package com.wx.app.game.controller.pay;


import com.baomidou.mybatisplus.extension.api.R;
import com.wx.app.game.pay.WxGamePayOrderService;
import com.wx.app.game.pay.dto.PayParamsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 微信预下单接口
 */
@RequestMapping("/wx/game/topay")
@RestController
public class WxPayController {
    @Autowired
    WxGamePayOrderService wxGamePayOrderServiceimpl;

    /**
     * 微信预下单
     * @param dto
     * @param request
     * @return
     */
    @PostMapping("/toOrder")
    public R wxNotify(@Valid PayParamsDto dto, HttpServletRequest request){
        return wxGamePayOrderServiceimpl.toPay(dto,request);
    }

    /**
     * 查询订单支付状态
     * @param orderNo
     * @return
     */
    @PostMapping("/paySuccess")
    public R paySuccess(String orderNo){
        return wxGamePayOrderServiceimpl.selectOrderStatus(orderNo);
    }
}
