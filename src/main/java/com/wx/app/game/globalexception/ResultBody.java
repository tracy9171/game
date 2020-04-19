package com.wx.app.game.globalexception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @ClassName ResponseData
 * @Description TODO
 * @Author Administrator
 * @Date 2020-03-21 13:17
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultBody implements Serializable {
    private int code;
    private String msg;

    private Object data;
    public ResultBody(BizServiceException e) {
        this.msg = e.getMsg();
        this.code = e.getCode();
    }

    /**
     * 成功
     *
     * @return
     */
    public static ResultBody success() {
        return success(null);
    }

    /**
     * 成功
     * @param data
     * @return
     */
    public static ResultBody success(Object data) {
        ResultBody rb = new ResultBody();
        rb.setCode(CommonEnum.SUCCESS.getCode());
        rb.setMsg(CommonEnum.SUCCESS.getMsg());
        rb.setData(data);
        return rb;
    }

    /**
     * 失败
     */
    public static ResultBody error(BizServiceException errorInfo) {
        ResultBody rb = new ResultBody();
        rb.setCode(errorInfo.getCode());
        rb.setMsg(errorInfo.getMsg());
        rb.setData(null);
        return rb;
    }

    /**
     * 失败
     */
    public static ResultBody error(int code, String message) {
        ResultBody rb = new ResultBody();
        rb.setCode(code);
        rb.setMsg(message);
        rb.setData(null);
        return rb;
    }

    /**
     * 失败
     */
    public static ResultBody error( String message) {
        ResultBody rb = new ResultBody();
        rb.setCode(-1);
        rb.setMsg(message);
        rb.setData(null);
        return rb;
    }
}
