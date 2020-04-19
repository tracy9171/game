package com.wx.app.game.aspect;

import com.wx.app.game.commom.HttpRequestUtils;
import com.wx.app.game.constant.StringPools;
import com.wx.app.game.globalexception.BizException;
import com.wx.app.game.utils.CheckPramsUtils;
import com.wx.app.game.utils.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
@ConditionalOnProperty(value = "sso.needLogin.enable",havingValue = "true",matchIfMissing = true)
public class NeedLoginAspect {

    @Autowired
    private HttpRequestUtils httpRequestUtils;

    @Autowired
    private RedisClient redisClient;

    @Before("@annotation(NeedCheck)&&@annotation(needCheck)")
    public void before(JoinPoint joinpoint, NeedCheck needCheck){
        needCheck(needCheck);
    }

    private void needCheck(NeedCheck needCheck){
        HttpServletRequest request = httpRequestUtils.getRequest();
        try {
            String nonce = request.getHeader(StringPools.NONCE);
            String timestamp = request.getHeader(StringPools.TIMESTAMP);
            if (!needCheck.needCheck()){
                log.info("不校验--------------->>>>>>>>");
                return;
            }
            log.info("开始校验请求合法性-------------->>>>>>>>>");
            log.info("NeedLogin不校验--------------->>>>>>>>Aspect_nonce={},timestamp={}",nonce,timestamp);
            if (CheckPramsUtils.isEmpty(nonce,timestamp)){
                log.info("needLogin_校验参数为空");
                throw new BizException(-1,"非法请求");
            }
            long nowDate = System.currentTimeMillis();
            long paramDate = Long.parseLong(timestamp);
            if ((nowDate - paramDate)/1000>60*10 || paramDate > nowDate){
                log.info("同一请求大于10分钟前访问过,或大于当前时间，判断为非法请求");
                throw new BizException(-1,"非法请求");
            }
            String redis_nonce = (String)redisClient.get(nonce + timestamp);
            if (!StringUtils.isEmpty(redis_nonce)){
                log.info("已经访问过，判断为非法请求");
                throw new BizException(-1,"非法请求");
            }
            redisClient.set(nonce + timestamp,nonce + timestamp,60*10);
            log.info("请求成功");
        } catch (Exception e) {
            log.info("校验异常error={}",e.getMessage(),e);
            throw new BizException(-1,"非法请求");
        }
    }
}
