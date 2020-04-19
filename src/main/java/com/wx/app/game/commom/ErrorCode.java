package com.wx.app.game.commom;

import com.baomidou.mybatisplus.extension.api.IErrorCode;
import lombok.Data;

/**
 * @ClassName RR
 * @Description TODO
 * @Author Administrator
 * @Date 2020-03-22 0:54
 * @Version 1.0
 */
@Data
public class ErrorCode implements IErrorCode {
    private long code;
    private String msg;

    public ErrorCode(String msg) {
        this.msg = msg;
        this.code = -1;
    }
    public ErrorCode(long code,String msg) {
        this.msg = msg;
        this.code = code;
    }
}
