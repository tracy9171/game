package com.wx.app.game.controller.pay;


import com.baomidou.mybatisplus.extension.api.R;
import com.wx.app.game.pay.WxGamePayOrderService;
import com.wx.app.game.pay.dto.PayParamsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 微信预下单接口
 */
@RequestMapping("/wx/game/topay")
@RestController
public class WxPayController {
    @Autowired
    WxGamePayOrderService wxGamePayOrderServiceimpl;

    @RequestMapping("toOrder")
    public R wxNotify(@Valid PayParamsDto dto, HttpServletRequest request){
        return wxGamePayOrderServiceimpl.toPay(dto,request);
    }
}
