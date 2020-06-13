package com.wx.app.game.pay.paynotify;




import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 微信支付回调
 */
public interface WxNotifyService {

    void weixin_notify(HttpServletRequest request, HttpServletResponse response);
}
