package com.wx.app.game.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.app.game.Entity.BoxEntity;
import com.wx.app.game.dto.BoxDto;
import com.wx.app.game.dto.BoxPageParamsDto;
import com.wx.app.game.dto.IdDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName BoxService
 * @Description 盒子
 * @Author Administrator
 * @Date 2020-03-23 0:09
 * @Version 1.0
 */
public interface BoxService extends IService<BoxEntity> {


    /**
     * 获取单条盒子信息
     * @param dto
     * @return
     */
    R getOne(IdDto dto);


    /**
     * 分页查询
     * @param dto
     * @return
     */
    R getBoxList(BoxPageParamsDto dto);

    /**
     * 不分页查询列表
     * @return
     */
    R getBoxAllList();
}
