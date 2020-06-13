package com.wx.app.game.utils.pay;


import com.baomidou.mybatisplus.extension.api.R;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;

/**
 * 微信支付回调
 */
public class PayNotifyUtils {

    public static void response(HttpServletResponse response, R r){
        String resXml;
        try {
            if (r== null){
                throw new RuntimeException("非法调用");
            }
            if (r.getCode() !=0){
                resXml = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA["+r.getMsg()+"]]></return_msg></xml> ";
            }else {
                resXml = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml> ";

            }
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
            out.write(resXml.getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
