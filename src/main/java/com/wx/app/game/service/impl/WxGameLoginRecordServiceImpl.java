package com.wx.app.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.app.game.Entity.WxGameDataCountEntity;
import com.wx.app.game.Entity.WxGameLoginRecordEntity;
import com.wx.app.game.Entity.WxGameSkipLoginRecordEntity;
import com.wx.app.game.commom.ErrorCode;
import com.wx.app.game.dto.WxGameLoginRecordDto;
import com.wx.app.game.mapper.WxGameLoginRecordMapper;
import com.wx.app.game.service.WxGameDataCountService;
import com.wx.app.game.service.WxGameLoginRecordService;
import com.wx.app.game.service.WxGameSkipLoginRecordService;
import com.wx.app.game.utils.CheckPramsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName WxGameLoginRecordServiceImpl
 * @Description 用户登陆记录
 * @Author Administrator
 * @Date 2020-03-29 13:27
 * @Version 1.0
 */
@Service
@Slf4j
public class WxGameLoginRecordServiceImpl extends ServiceImpl<WxGameLoginRecordMapper, WxGameLoginRecordEntity> implements WxGameLoginRecordService {

     @Autowired
     private WxGameDataCountService wxGameDataCountServiceImpl;
    /**
     * 保存登陆记录
     * @param dto
     * @return
     */
    @Override
    public R saveWxGameLoginRecord(WxGameLoginRecordDto dto) {
        log.info("saveWxGameLoginRecord_dto={}",dto);
        if (CheckPramsUtils.isEmpty(dto.getGameAccount(),dto.getUserId())
                || dto.getGameId() ==null
                || dto.getType() == null
                || dto.getSystemType() == null){
            log.info("saveWxGameLoginRecord缺少必要参数");
            return R.failed(new ErrorCode(1,"缺少必要参数"));
        }

        try {
            doLogin(dto);
            return R.ok("");
        } catch (Exception e) {
            log.error("saveWxGameLoginRecord_error={}",e.getMessage(),e);
        }
        return R.failed(new ErrorCode("保存失败"));
    }

    /**
     * 记录登陆
     * @param dto
     * @return
     */
    private void doLogin(WxGameLoginRecordDto dto){
        WxGameLoginRecordEntity one = getOne(new QueryWrapper<WxGameLoginRecordEntity>()
                .eq("user_id", dto.getUserId())
                .eq("game_account", dto.getGameAccount())
                .eq("game_id", dto.getGameId())
                .eq(!StringUtils.isEmpty(dto.getRoleId()),"role_id", dto.getRoleId())
                .eq("system_type", dto.getSystemType())
                .ge("created_date", LocalDate.now())
                .orderByDesc("created_date"));
        if (one !=null){
            one.setUpdatedDate(LocalDateTime.now());
            updateById(one);
            log.info("已记录登陆，更新登陆时间");
        }else {
            WxGameLoginRecordEntity entity = new WxGameLoginRecordEntity();
            BeanUtils.copyProperties(dto,entity);
            save(entity);
            doRecord(dto);
        }
    }

    /**
     * 计入统计表
     * @param dto
     */
    private void doRecord(WxGameLoginRecordDto dto){
        WxGameDataCountEntity countEntity = new WxGameDataCountEntity();
        if (dto.getType() == 1){//平台跳转过来的进入游戏
            if (dto.getSystemType() == 1){
                //安卓跳转
                countEntity.setASkipLoginCount(1);
                countEntity.setALoginRecord(1);
            }else {
                countEntity.setISkipLoginCount(1);
                countEntity.setILoginRecord(1);
            }
        }else {//游戏直接登陆
            if (dto.getSystemType() == 1){
                //安卓跳转
                countEntity.setALoginRecord(1);
            }else {
                countEntity.setILoginRecord(1);
            }
        }
        wxGameDataCountServiceImpl.updateDataById(countEntity);
    }
}
