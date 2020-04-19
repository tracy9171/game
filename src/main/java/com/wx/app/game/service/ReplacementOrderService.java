package com.wx.app.game.service;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.app.game.Entity.WxReplacementOrderEntity;
import com.wx.app.game.dto.WxGameOrderDto;
import com.wx.app.game.dto.WxReplacementOrderDto;

public interface ReplacementOrderService extends IService<WxReplacementOrderEntity> {

    /**
     * 补单列表
     * @param dto
     * @return
     */
    R getRepOrderList(WxReplacementOrderDto dto);

    /**
     * 新增补单
     * @param dto
     * @return
     */
    R addRepOrder(WxReplacementOrderDto dto);

    /**
     * 根据订单号和cp订单号修改订单状态
     * @param dto
     * @return
     */
    R updateRepOrderStatusByOrderNo(WxReplacementOrderDto dto);

    /**
     * 根据订单号查询补单列表
     * @param dto
     * @return
     */
    R getRepOrderByOrderNo(WxReplacementOrderDto dto);
}
