package com.wx.app.game.commom.cpnotifypay;

import com.wx.app.game.Entity.WxGameOrderEntity;
import com.wx.app.game.dto.pay.CpParamsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CpNotifyPayServiceImpl implements CpNotifyPayService{
    @Override
    public String cpNotify(WxGameOrderEntity orderEntity) {

            CpParamsDto cpParamsDto = new CpParamsDto();
            cpParamsDto.setCpOrderNo(orderEntity.getCpOrderNo());
            cpParamsDto.setGameId(orderEntity.getGameId());
            cpParamsDto.setOrderStatus(0);
            cpParamsDto.setUserId(orderEntity.getUserId());
            StringBuffer sign = new StringBuffer();
            //sign.append(orderEntity.getCpOrderNo()).
                    //MD5Utils.getMD5()

        return null;
    }
}
