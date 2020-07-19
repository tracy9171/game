package com.wx.app.game.constant;

/**
 * @ClassName StringPools
 * @Description TODO
 * @Author Administrator
 * @Date 2020-03-20 23:07
 * @Version 1.0
 */
public interface StringPools {

    /**随机数*/
    String NONCE="nonce";
    /**时间戳*/
    String TIMESTAMP="timestamp";

    String SKIP_RECORD_KEY="SKIP_RECORD_DATA_KEY_TO_TAY";
    String SUCCESS ="SUCCESS";
    String LOGIN_KEY="login:user";
    Integer EXP = 60*60*24*200;
    String TOKEN="token";
    /**
     * CP支付回调密钥、支付加签密钥
     */
    String APP_KEY = "f8b26d1d98759f8625ed367200e6aacb";

}
