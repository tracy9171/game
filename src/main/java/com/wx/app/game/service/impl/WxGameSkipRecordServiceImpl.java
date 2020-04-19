package com.wx.app.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.app.game.Entity.WxGameDataCountEntity;
import com.wx.app.game.Entity.WxGameSkipLoginRecordEntity;
import com.wx.app.game.Entity.WxGameSkipRecordEntity;
import com.wx.app.game.commom.ErrorCode;
import com.wx.app.game.constant.StringPools;
import com.wx.app.game.dto.WxGameLoginRecordDto;
import com.wx.app.game.dto.WxGameSkipRecordDto;
import com.wx.app.game.mapper.WxGameSkipRecordMapper;
import com.wx.app.game.service.WxGameDataCountService;
import com.wx.app.game.service.WxGameSkipRecordService;
import com.wx.app.game.utils.CheckPramsUtils;
import com.wx.app.game.utils.RedisLocks;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;

/**
 * @ClassName WxGameSkipRecordServiceImpl
 * @Description 用户跳转记录
 * @Author Administrator
 * @Date 2020-03-29 13:30
 * @Version 1.0
 */
@Service
@Slf4j
public class WxGameSkipRecordServiceImpl extends ServiceImpl<WxGameSkipRecordMapper, WxGameSkipRecordEntity> implements WxGameSkipRecordService {

    @Autowired
    private WxGameDataCountService wxGameDataCountServiceImpl;

    /**
     * 新增跳转记录
     * @param dto
     * @return
     */
    @Override
    public R addWxGameSkip(WxGameSkipRecordDto dto) {
        log.info("addWxGameSkip_dto={}",dto);
        if (CheckPramsUtils.isEmpty(dto.getGameAccount(),dto.getUserId()) || dto.getGameId() == null|| dto.getSystemType() == null){
            log.info("addWxGameSkip缺少必要参数");
            return R.failed(new ErrorCode(1,"缺少必要参数"));
        }
        RedisLocks lock = new RedisLocks();
        StringBuilder key = new StringBuilder();
    key.append(StringPools.SKIP_RECORD_KEY).append(dto.getGameAccount()).append(dto.getGameId()).append(dto.getUserId());
        if (!lock.lock(key.toString())){
            return R.failed(new ErrorCode("操作太快"));
        }
        try {
            doSkip(dto);
            return R.ok("");
        } catch (Exception e) {
            log.error("addWxGameSkip_error={}",e.getMessage(),e);
        }finally {
            lock.releaseLock(key.toString());
        }
        return R.failed(new ErrorCode("未知异常"));
    }

    /**
     * 记录跳转
     * @param dto
     * @return
     */
    private void doSkip(WxGameSkipRecordDto dto){
        WxGameSkipRecordEntity entity = getOne(new QueryWrapper<WxGameSkipRecordEntity>()
                .eq("user_id", dto.getUserId())
                .eq("game_account", dto.getGameAccount())
                .eq("game_id", dto.getGameId())
                .eq("system_type", dto.getSystemType())
                .ge("created_date", LocalDate.now()));
        if (entity !=null){
            log.info("已记录跳转");
        }else {
            entity = new WxGameSkipRecordEntity();
            BeanUtils.copyProperties(dto,entity);
            save(entity);
            doRecord(dto);
        }
    }

    private void doRecord(WxGameSkipRecordDto dto){
        WxGameDataCountEntity countEntity = new WxGameDataCountEntity();
        if (dto.getSystemType() == 1){
            //安卓跳转
            countEntity.setASkipCount(1);
        }else {
            countEntity.setISkipCount(1);
        }
        wxGameDataCountServiceImpl.updateDataById(countEntity);
    }
}
