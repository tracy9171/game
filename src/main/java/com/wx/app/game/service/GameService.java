package com.wx.app.game.service;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.app.game.Entity.GameEntity;
import com.wx.app.game.dto.GamePageParamsDto;
import com.wx.app.game.dto.GameParamsDto;
import com.wx.app.game.dto.IdDto;

import javax.servlet.http.HttpServletRequest;


/**
 * @ClassName GameService
 * @Description 游戏管理
 * @Author Administrator
 * @Date 2020-03-22 0:14
 * @Version 1.0
 */
public interface GameService extends IService<GameEntity> {


    /**
     * 获取单条游戏记录
     * @param dto
     * @return
     */
    R getOneGame(IdDto dto);

    /**
     * 分页查询游戏列表
     * @param dto
     * @return
     */
    R getGameList(GamePageParamsDto dto);

    /**
     * 根据父游戏ID查询关联游戏
     * @param dto
     * @return
     */
    R selectChildrenGame(GamePageParamsDto dto);

    /**
     * 查询所有游戏列表
     * @return
     */
    R allList();
}
