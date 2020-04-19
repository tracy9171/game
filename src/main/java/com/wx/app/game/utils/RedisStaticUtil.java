package com.wx.app.game.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;


/**
 *
 */
@Component
@Slf4j
public class RedisStaticUtil {

    private static RedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate){
        RedisStaticUtil.redisTemplate = redisTemplate;
    }

    public static boolean setStrNx(String key, String value){
        Boolean ff = (Boolean)redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer keySerializer = redisTemplate.getKeySerializer();
                byte[] rawKey = keySerializer.serialize(key);
                RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
                byte[] rawValue = valueSerializer.serialize(value);
                Boolean flag = connection.setNX(rawKey, rawValue);
                return flag;
            }
        });
        return ff;
    }

    public static Object get(String key){
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    //delete 应该有返回boolean类型
    public static boolean del(String key){
        if (!StringUtils.isEmpty(key)){
             redisTemplate.delete(key);
             return true;
        }
        return false;
    }

    public static boolean expire(String key,long time){
        try {
            if (time>0){
                redisTemplate.expire(key,time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
