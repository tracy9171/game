package com.wx.app.game.commom.cpnotifypay;


import com.wx.app.game.Entity.WxGameOrderEntity;
import com.wx.app.game.constant.StringPools;
import com.wx.app.game.constant.pay.PayStringPool;
import com.wx.app.game.dto.pay.CpParamsDto;
import com.wx.app.game.dto.pay.CpResultDto;
import com.wx.app.game.service.GameOrderService;
import com.wx.app.game.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;


/**
 * Cp支付回调
 */
@Slf4j
@Service
public class CpNotifyPayServiceImpl implements CpNotifyPayService{

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private GameOrderService gameOrderServiceImpl;
    /**
     * 通知CP支付订单成功
     * @param orderEntity
     * @return
     */
    @Override
    public void cpNotify(WxGameOrderEntity orderEntity) {

        try {
            CpParamsDto cpParamsDto = new CpParamsDto();
            cpParamsDto.setCpOrderNo(orderEntity.getCpOrderNo());
            cpParamsDto.setOrderNo(orderEntity.getOrderNo());
            cpParamsDto.setPayment(orderEntity.getPayMoney().toString());
            cpParamsDto.setGameId(orderEntity.getGameId());
            cpParamsDto.setOrderStatus(0);
            cpParamsDto.setUserId(orderEntity.getUserId());
            StringBuffer sign = new StringBuffer();
            sign.append(orderEntity.getCpOrderNo())
                    .append(orderEntity.getGameId())
                    .append(orderEntity.getOrderStatus())
                    .append(orderEntity.getUserId())
                    .append(orderEntity.getPayDate().toInstant(ZoneOffset.of("+8")).toEpochMilli())
                    .append(StringPools.APP_KEY);
            String md5 = MD5Utils.getMD5(sign.toString());
            cpParamsDto.setSign(md5);
            log.info("cpNotify_通知CP入参={}",cpParamsDto);
            CpResultDto result = restTemplate.postForObject(PayStringPool.CP_NOTIFY_RRL, null, CpResultDto.class, cpParamsDto);
            log.info("result={}",result);
            //更新订单通知CP状态
            notifyCp(result,orderEntity.getId());
        } catch (RestClientException e) {
            e.printStackTrace();
        }
    }


    private void notifyCp(CpResultDto cpResultDto,Integer id){
            if (cpResultDto !=null && cpResultDto.getCode() == 0){
                //更新订单通知CP成功
                WxGameOrderEntity orderEntity = new WxGameOrderEntity();
                orderEntity.setId(id);
                orderEntity.setNotifyStatus(1);
                orderEntity.setUpdatedDate(LocalDateTime.now());
                gameOrderServiceImpl.updateById(orderEntity);
            }
    }
}
