package com.wx.app.game.controller.pay;


import com.wx.app.game.pay.paynotify.WxNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 微信回调接口
 */
@RequestMapping("/wx/pay")
@RestController
public class WxPayNotifyController {

    @Autowired
    private WxNotifyService wxNotifyServiceImpl;

    @RequestMapping("/notify")
    public void wxNotify(HttpServletRequest request, HttpServletResponse response){
        wxNotifyServiceImpl.weixin_notify(request,response);
    }
}
