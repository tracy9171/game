package com.wx.app.game.commom.cpnotifypay;

import com.wx.app.game.Entity.WxGameOrderEntity;

/**
 * 通知cp支付回调
 */
public interface CpNotifyPayService {
    String cpNotify(WxGameOrderEntity orderEntity);
}
