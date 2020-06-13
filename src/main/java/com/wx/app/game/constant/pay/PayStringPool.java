package com.wx.app.game.constant.pay;

import java.util.UUID;

public interface PayStringPool {
    //获取微信openId链接
    String OPENID_URL = "https://api.weixin.qq.com/sns/jscode2session?";

    //伊原盒子微信小程序appid
    String MINI_APPID = "wxfdf21db7915067e8";

    //伊原盒子appSecret
    String MINI_APP_SECRET = "ba8755223b3d1d3582bb332376a968b1";

    //微信支付回调接口
    String NOTIFY_URL ="";

    String TRADE_TYPE = "JSAPI";
    String SIGN_TYPE = "MD5";

    String NONCE_STR= UUID.randomUUID().toString().replaceAll("-", "");


    /**-----------支付公众号信息------------------*/
    String MCH_ID = "1595100911";//商户号
    String APPID = "wxfdf21db7915067e8";//公众号appid
    String API_KEY ="9076AE8B039CB1D79B8567891BA24A08";//公众号密钥
}