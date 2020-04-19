package com.wx.app.game.globalexception;

public class BizException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    protected int code;
    /**
     * 错误信息
     */
    protected String msg;

    public BizException() {
        super();
    }

    public BizException(BizServiceException e) {
        super(Integer.toString(e.getCode()));
        this.code = e.getCode();
        this.msg = e.getMsg();
    }

    public BizException(BizServiceException e, Throwable cause) {
        super(e.getMsg(), cause);
        this.code = e.getCode();
        this.msg = e.getMsg();
    }

    public BizException(String errorMsg) {
        super(errorMsg);
        this.msg = errorMsg;
    }

    public BizException(int errorCode, String errorMsg) {
        super(Integer.toString(errorCode));
        this.code = errorCode;
        this.msg = errorMsg;
    }

    public BizException(int errorCode, String errorMsg, Throwable cause) {
        super(Integer.toString(errorCode), cause);
        this.code = errorCode;
        this.msg = errorMsg;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int errorCode) {
        this.code = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String errorMsg) {
        this.msg = errorMsg;
    }

    public String getMessage() {
        return msg;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
