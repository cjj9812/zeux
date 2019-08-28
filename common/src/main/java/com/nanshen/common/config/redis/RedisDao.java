package com.nanshen.common.config.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisDao {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    private static final Logger logger= LoggerFactory.getLogger(RedisDao.class);


    /**
     * 存储对象(单个)
     * @param key
     * @param value
     */
    public void set(final String key,Object value){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key,value);
    }

    /**
     * 存储对象并设置过期时间（单个）
     * @param key
     * @param value
     * @param expireTime
     * @param timeUnit
     */
    public void setAndTime(final String key,Object value,long expireTime,TimeUnit timeUnit){
        set(key,value);
        redisTemplate.expire(key, expireTime,timeUnit);
    }

    /**
     * 删除key对应值（单个）
     * @param key
     */
    public void remove(final String key){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        redisTemplate.delete(key);
    }

}
