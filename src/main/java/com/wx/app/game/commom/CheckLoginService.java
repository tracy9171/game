package com.wx.app.game.commom;

import com.baomidou.mybatisplus.extension.api.R;
import com.wx.app.game.constant.StringPools;
import com.wx.app.game.pay.dto.PayParamsDto;
import com.wx.app.game.utils.MD5Utils;
import com.wx.app.game.utils.RedisClient;
import com.wx.app.game.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CheckLoginService {
    @Autowired
    private RedisClient redisClient;

    public R checkToken(String token){
        String userIdKey;
        try {
            userIdKey = TokenUtil.decryptToken(token);
        } catch (Exception e) {
            log.info("token校验失败");
            return R.failed(new ErrorCode(1003,"非法请求"));
        }
        Object data = redisClient.get(StringPools.LOGIN_KEY + userIdKey);
        if (data== null){
            log.info("缓存中key不存在登陆失效");
            return R.failed(new ErrorCode(1001,"登陆超时"));
        }
        if (!data.equals(userIdKey)){
            log.info("用户id校验失败");
            return R.failed(new ErrorCode(1002,"参数校验失败"));
        }
        return null;
    }

    /**
     * 预下单验签
     * @return
     */
    public R payVail(PayParamsDto dto){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(dto.getPayment())
        .append(dto.getGameId())
        .append(dto.getRoleId())
        .append(dto.getSystems())
        .append(dto.getUserId())
        .append(dto.getCpOrderNO())
        .append(StringPools.APP_KEY);
        String sign = MD5Utils.getMD5(stringBuffer.toString());
        if (!sign.equals(dto.getSign())){
            log.info("预下单解签失败");
            return R.failed("验签失败");
        }
        return null;
    }
}
