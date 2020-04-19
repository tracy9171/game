package com.wx.app.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.app.game.Entity.PlayUserEntity;
import com.wx.app.game.Entity.WxGameDataCountEntity;
import com.wx.app.game.commom.ErrorCode;
import com.wx.app.game.dto.PlayUserDto;
import com.wx.app.game.dto.PlayUserPageParamsDto;
import com.wx.app.game.dto.WxGameLoginRecordDto;
import com.wx.app.game.mapper.PlayUserMapper;
import com.wx.app.game.service.PlayUserService;
import com.wx.app.game.service.WxGameDataCountService;
import com.wx.app.game.utils.CheckPramsUtils;
import com.wx.app.game.utils.InitPageUtils;
import com.wx.app.game.utils.RedisLocks;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * @ClassName PlayUserServiceImpl
 * @Description 玩家信息
 * @Author Administrator
 * @Date 2020-03-24 21:09
 * @Version 1.0
 */
@Slf4j
@Service
public class PlayUserServiceImpl extends ServiceImpl<PlayUserMapper, PlayUserEntity> implements PlayUserService {

    @Autowired
    private WxGameDataCountService wxGameDataCountServiceImpl;

    /**
     * 分页查询玩家列表
     * @param dto
     * @return
     */
    @Override
    public R getPlayUserList(PlayUserPageParamsDto dto) {
        log.info("getPlayUserList_dto={}",dto);
        try {
            Page page = InitPageUtils.getPage(dto.getLimit(), dto.getPage());
            IPage data = page(page, new QueryWrapper<>());
            return R.ok(data);
        } catch (Exception e) {
            log.error("getPlayUserList_error={}",e.getMessage(),e);
        }
        return R.failed(new ErrorCode("查询异常"));
    }

    /**
     * 根据玩家ID查询玩家信息
     * @param dto
     * @return
     */
    @Override
    public R selectOneByUserId(PlayUserPageParamsDto dto) {
        log.info("selectOneByUserId_dto={}",dto);
        try {
            if (StringUtils.isEmpty(dto.getUserId())){
                log.info("userId不能为空");
                return R.failed(new ErrorCode("缺少必要参数"));
            }
            return R.ok(getPlayUser(dto.getUserId()));
        } catch (Exception e) {
            log.error("selectOneByUserId_error={}",e.getMessage(),e);
        }
        return R.failed(new ErrorCode("未知异常"));
    }

    /**
     * 新增游戏玩家
     * @param dto
     * @return
     */
    @Override
    public R addPlayUserInfo(PlayUserDto dto) {
        log.info("addPlayUserInfo_dto={}",dto);
        if (CheckPramsUtils.isEmpty(dto.getUserId(),dto.getGameAccount()) || dto.getSystemType() != null ){
            log.info("缺少必要参数");
            return R.failed(new ErrorCode(1,"缺少必要参数"));
        }
        RedisLocks lock = new RedisLocks();
        String key ="ADD_PLAY_USER_INFO_LOCK_KEY" + dto.getUserId() ;
        if (!lock.lock(key)){
            log.info("频繁请求");
            return R.failed(new ErrorCode(2,"操作频繁"));
        }

        try {
            PlayUserEntity playUser = getPlayUser(dto.getUserId());
            if (playUser !=null){
                log.info("该玩家ID已存在");
                return R.failed(new ErrorCode(3,"该玩家ID已存在"));
            }
            PlayUserEntity entity = new PlayUserEntity();
            BeanUtils.copyProperties(dto,entity);
            save(entity);
            doRecord(dto);
            return R.ok("");
        } catch (BeansException e) {
            log.error("addPlayUserInfo_error={}",e.getMessage(),e);
        }finally {
            lock.releaseLock(key);
        }
        return R.failed(new ErrorCode("新增失败"));
    }

    /**
     * 统计创用户数
     * @param dto
     */
    private void doRecord(PlayUserDto dto){
        WxGameDataCountEntity countEntity = new WxGameDataCountEntity();
        if (dto.getSystemType() == 1){//安卓
            countEntity.setAUserCount(1);
        }else {
            countEntity.setIUserCount(1);
        }
        wxGameDataCountServiceImpl.updateDataById(countEntity);
    }

    /**
     * 编辑游戏玩家
     * @param dto
     * @return
     */
    @Override
    public R updatePlayUserInfo(PlayUserDto dto) {
        log.info("updatePlayUserInfo_dto={}",dto);
        if (CheckPramsUtils.isEmpty(dto.getUserId())){
            log.info("编辑缺少必要参数");
            return R.failed(new ErrorCode(1,"缺少必要参数"));
        }
        RedisLocks lock = new RedisLocks();
        String key ="UPDATE_PLAY_USER_INFO_LOCK_KEY" + dto.getUserId() ;
        if (!lock.lock(key)){
            log.info("同一订单频繁请求，不正常");
            return R.failed(new ErrorCode(2,"操作频繁"));
        }
        try {
            PlayUserEntity playUser = getPlayUser(dto.getUserId());
            if (playUser ==null){
                log.info("该玩家ID不存在");
                return R.failed(new ErrorCode(3,"该玩家ID不存在"));
            }
            PlayUserEntity entity = new PlayUserEntity();
            BeanUtils.copyProperties(dto,entity);
            entity.setCreatedDate(null);
            entity.setUpdatedDate(LocalDateTime.now());
            entity.setId(playUser.getId());
            updateById(entity);
            return R.ok("");
        } catch (BeansException e) {
            log.error("addPlayUserInfo_error={}",e.getMessage(),e);
        }finally {
            lock.releaseLock(key);
        }
        return R.failed(new ErrorCode("编辑失败"));
    }


    private PlayUserEntity getPlayUser(String userId){
        return getOne(new QueryWrapper<PlayUserEntity>().eq("user_id", userId));
    }

    /**
     * 查询是否今天注册的用户
     * @param userId
     * @return
     */
    public PlayUserEntity getPlayUserByToDay(String userId){
        return getOne(new QueryWrapper<PlayUserEntity>().eq("user_id", userId).ge("created_date", LocalDate.now()));
    }
}
