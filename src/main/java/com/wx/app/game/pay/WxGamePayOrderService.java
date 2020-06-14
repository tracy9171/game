package com.wx.app.game.pay;

import com.baomidou.mybatisplus.extension.api.R;
import com.wx.app.game.pay.dto.PayParamsDto;

import javax.servlet.http.HttpServletRequest;

public interface WxGamePayOrderService {

    /**
     * 微信预支付
     * @return
     */
    R toPay(PayParamsDto dto, HttpServletRequest request);

    R selectOrderStatus(String orderNo);
}
