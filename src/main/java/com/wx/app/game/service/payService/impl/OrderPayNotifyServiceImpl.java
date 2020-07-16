package com.wx.app.game.service.payService.impl;

import com.baomidou.mybatisplus.extension.api.R;
import com.wx.app.game.Entity.WxGameOrderEntity;
import com.wx.app.game.dto.pay.CpParamsDto;
import com.wx.app.game.service.GameOrderService;
import com.wx.app.game.service.payService.OrderPayNotifyService;
import com.wx.app.game.utils.MD5Utils;
import com.wx.app.game.utils.RedisLocks;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;


/**
 * 游戏订单回调处理
 */
@Service
@Slf4j
public class OrderPayNotifyServiceImpl implements OrderPayNotifyService {

    @Autowired
    private GameOrderService gameOrderServiceImpl;

    /**
     * 处理游戏支付回调订单
     * @param orderNo
     * @return
     */
    @Override
    public R toDoWxOrderNotify(String orderNo) {
        log.info("处理游戏订单={}",orderNo);
        if (StringUtils.isEmpty(orderNo)){
            return R.failed("订单号为空");
        }
        RedisLocks lock = new RedisLocks(30);
        String key ="GAME:ORDER:NOTITY:NOTITY_LOCK_" + orderNo;
        if (!lock.lock(key)){
            return R.failed("对调太频繁");
        }
        try {
            WxGameOrderEntity orderEntity = gameOrderServiceImpl.getGameOrderByOrderNo(orderNo);
            if (orderEntity == null){
                return R.failed("无效订单号");
            }
            //1未支付、2支付成功、3支付失败
            if (orderEntity.getOrderStatus() !=1){
                log.info("订单已处理");
                return R.ok("订单已处理");
            }
            orderEntity.setOrderStatus(2);
            orderEntity.setUpdatedDate(LocalDateTime.now());
            boolean b = gameOrderServiceImpl.updateById(orderEntity);
            if (b){
                //todo------------------------------
                //通知CP支付成功
            }
            return R.ok("订单处理功能");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.releaseLock(key);
        }
        return R.failed("订单处理失败");
    }



}
