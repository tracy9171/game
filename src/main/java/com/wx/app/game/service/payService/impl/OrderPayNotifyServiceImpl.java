package com.wx.app.game.service.payService.impl;

import com.baomidou.mybatisplus.extension.api.R;
import com.wx.app.game.service.payService.OrderPayNotifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * 游戏订单回调处理
 */
@Service
@Slf4j
public class OrderPayNotifyServiceImpl implements OrderPayNotifyService {


    /**
     * 处理游戏支付回调订单
     * @param OrderNo
     * @return
     */
    @Override
    public R toDoWxOrderNotify(String OrderNo) {

        return null;
    }
}
