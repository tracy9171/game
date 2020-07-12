package com.wx.app.game.commom;

import com.baomidou.mybatisplus.extension.api.R;
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

    public R checkToken(String token,String userId){
        String redisKey;
        try {
            redisKey = TokenUtil.decryptToken(token);
        } catch (Exception e) {
            log.info("token校验失败");
            return R.failed(new ErrorCode(1003,"非法请求"));
        }
        Object data = redisClient.get(redisKey);
        if (data== null){
            log.info("缓存中key不存在登陆失效");
            return R.failed(new ErrorCode(1001,"登陆失效"));
        }
        if (!data.equals(userId)){
            log.info("用户id校验失败");
            return R.failed(new ErrorCode(1002,"参数校验失败"));
        }
        return null;
    }
}
