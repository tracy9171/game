package com.wx.app.game.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class RedisLocks {
    private static Logger logger = LoggerFactory.getLogger(RedisLocks.class);

    /**
     * 每个锁有唯一的uuid
     */
    private String uuid = UUID.randomUUID().toString();

    /**
     * 未拿到锁休眠时间
     */
    private long sleepTime = 0;

    /**
     * 锁放在redis中的失效时间
     */
    private int redisExpireTime = 10;

    /**
     * 等待超时是否抛出异常
     */
    private boolean timeOutThrowsException = false;

    /**
     * 异常是否抛出异常，exceptionThrowException 为true,timeOutThrowsException才有效
     */
    private boolean exceptionThrowException = false;

    public RedisLocks(){
    }

    public RedisLocks(int redisExpireTime){
        this(false,false,redisExpireTime,0);
    }

    public RedisLocks(int redisExpireTime,long sleepTime){
        this(false,false,redisExpireTime,sleepTime);
    }

    public RedisLocks(boolean timeOutThrowsException,int redisExpireTime,long sleepTime){
        this(true,true,redisExpireTime,sleepTime);
    }

    public RedisLocks(boolean timeOutThrowsException,boolean exceptionThrowException,int redisExpireTime,long sleepTime){
        this.timeOutThrowsException = timeOutThrowsException;
        this.exceptionThrowException = exceptionThrowException;
        this.redisExpireTime = redisExpireTime;
        this.sleepTime = sleepTime;
    }

    public RedisLocks(long sleepTime, boolean timeOutThrowsException){
        this.sleepTime = sleepTime;
        this.timeOutThrowsException = timeOutThrowsException;
    }

    /**
     * 获取不到锁直接返回失败，不等待
     */
    public boolean lock(String key){
        return lock(key,0);
    }

    /**
     * expireTime: 期望等待锁时间，单位秒
     */
    public boolean lock(String key,int expiresTime){
        try {
            if (!StringUtils.hasText(key)){
                return false;
            }
            long time = System.currentTimeMillis() + expiresTime * 1000;
            do {
                String oldUuid = (String) RedisStaticUtil.get(key);
                boolean flag = false;
                if (!StringUtils.hasText(oldUuid)){
                    flag = RedisStaticUtil.setStrNx(key,uuid);
                    if (flag){
                        RedisStaticUtil.expire(key,redisExpireTime);
                        logger.info("key:{}正常获取锁",key);
                        return true;
                    }
                }else if (uuid.equals(oldUuid)){
                    logger.info("key:{} 重人获得锁",key);
                    return true;
                }
                Thread.sleep(sleepTime);
                logger.info("key:{}等待获取锁。。。",key);
            }while (time > System.currentTimeMillis());
            if (timeOutThrowsException){
                throw new TimeoutException("key:" + key + "等待锁超时，抛出异常。。");
            }else {
                logger.info("key:{} 等待锁超时，不再等待锁",key);
            }
        } catch (Exception e) {
            if (exceptionThrowException){
                if (timeOutThrowsException){
                    if (e instanceof TimeoutException){
                        throw new RuntimeException(e);
                    }
                }
                throw new RuntimeException(e);
            }else {
                logger.info("key:{}等待锁时异常，不再等待锁。",key,e);
            }
        }
        return false;
    }

    public boolean releaseLock(String key){
        try {
            String oldUuid = (String) RedisStaticUtil.get(key);
            if (uuid.equals(oldUuid)){
                boolean del = RedisStaticUtil.del(key);
                if (del){
                    logger.info("key:{}释放了锁",key);
                    return true;
                }
            }else {
                logger.info("key:{}释放锁失败",key);
                return false;
            }
        } catch (Exception e) {
            logger.info("key:{}释放锁异常",key,e);
        }
        logger.info("key:{}异常释放锁失败",key);
        return false;
    }
}
