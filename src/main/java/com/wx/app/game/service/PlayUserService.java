package com.wx.app.game.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.app.game.Entity.PlayUserEntity;
import com.wx.app.game.dto.PlayUserDto;
import com.wx.app.game.dto.PlayUserPageParamsDto;


/**
 * @ClassName PlayUserService
 * @Description TODO
 * @Author Administrator
 * @Date 2020-03-24 21:08
 * @Version 1.0
 */
public interface PlayUserService extends IService<PlayUserEntity> {

    /**
     * 分页查询玩家列表
     * @param dto
     * @return
     */
    R getPlayUserList(PlayUserPageParamsDto dto);

    /**
     * 根据玩家ID查询玩家信息
     * @param dto
     * @return
     */
    R selectOneByUserId(PlayUserPageParamsDto dto);

    /**
     * 新增游戏玩家
     * @param dto
     * @return
     */
    R addPlayUserInfo(PlayUserDto  dto);

    /**
     * 编辑游戏玩家
     * @param dto
     * @return
     */
    R updatePlayUserInfo(PlayUserDto  dto);

    /**
     * 根据用户ID查询用户信息
     * @param userId
     * @return
     */
    PlayUserEntity getPlayUserByToDay(String userId);
}
