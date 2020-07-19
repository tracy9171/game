package com.wx.app.game.task;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wx.app.game.Entity.WxGameOrderEntity;
import com.wx.app.game.commom.cpnotifypay.CpNotifyPayService;
import com.wx.app.game.service.GameOrderService;
import com.wx.app.game.utils.RedisLocks;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 定时推送消息给Cp 支付回调
 */
@Slf4j
@Component
public class CpNotifyTask {

    @Autowired
    private CpNotifyPayService cpNotifyPayServiceImpl;
    @Autowired
    private GameOrderService gameOrderServiceImpl;

    @Scheduled(cron = "0 0/5 * * * ?")
    public void notifyTask(){
        RedisLocks lock = new RedisLocks();
        String key = "task:lock:CpNotifyTask";
        if (!lock.lock(key)){
            return;
        }
        try {
            List<WxGameOrderEntity> list = gameOrderServiceImpl.list(new QueryWrapper<WxGameOrderEntity>().eq("order_status", 2).eq("notify_status", 0));
            if (CollectionUtils.isEmpty(list)){
                return;
            }
            for (WxGameOrderEntity entity:list) {
                //通知CP支付成功
                cpNotifyPayServiceImpl.cpNotify(entity);
            }
        } finally {
            lock.releaseLock(key);
        }
    }
}
