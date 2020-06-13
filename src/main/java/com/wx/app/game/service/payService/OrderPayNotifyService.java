package com.wx.app.game.service.payService;


import com.baomidou.mybatisplus.extension.api.R;


/**
 * 游戏订单回调处理
 */
public interface OrderPayNotifyService {

    /**
     * 处理游戏支付回调订单
     * @param OrderNo
     * @return
     */
    R toDoWxOrderNotify(String OrderNo);
}
