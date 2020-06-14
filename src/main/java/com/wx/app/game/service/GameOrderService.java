package com.wx.app.game.service;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.app.game.Entity.WxGameOrderEntity;
import com.wx.app.game.dto.WxGameOrderDto;

public interface GameOrderService extends IService<WxGameOrderEntity> {


    /**
     * 游戏订单列表
     * @param dto
     * @return
     */
    R getGameOrderList(WxGameOrderDto dto);

    /**
     * 新增游戏订单
     * @param dto
     * @return
     */
    R addGameOrder(WxGameOrderDto dto);

    /**
     * 根据订单号和cp订单号修改订单状态
     * @param dto
     * @return
     */
    R updateOrderStatusByOrderNo(WxGameOrderDto dto);

    /**
     * 根据订单号查询游戏订单
     * @return
     */
    WxGameOrderEntity getGameOrderByOrderNo(String orderNo);

    /**
     * 根据订单号查询游戏订单
     * @return
     */
    WxGameOrderEntity getGameOrderByCpOrderNo(String cpOrderNo);
}
