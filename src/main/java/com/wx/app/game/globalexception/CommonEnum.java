package com.wx.app.game.globalexception;

public enum CommonEnum implements BizServiceException{
    // 数据操作错误定义
    SUCCESS(0, "成功!"),
    BODY_NOT_MATCH(-1,"数据格式不符!"),
    SIGNATURE_NOT_MATCH(5,"请求的数字签名不匹配!"),
    NOT_FOUND(404, "未找到该资源!"),
    INTERNAL_SERVER_ERROR(-1, "未知服务异常!"),
    SERVER_BUSY(503,"服务器正忙，请稍后再试!");


    private int code;
    private String msg;

    CommonEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
