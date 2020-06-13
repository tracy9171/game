package com.wx.app.game.pay.paynotify;


import com.baomidou.mybatisplus.extension.api.R;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信支付回调
 */
public interface WxNotifyService {

    R weixin_notify(HttpServletRequest request);
}
