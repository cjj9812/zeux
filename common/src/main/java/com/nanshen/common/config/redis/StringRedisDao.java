package com.nanshen.common.config.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;


public class StringRedisDao {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    private static final Logger logger= LoggerFactory.getLogger(StringRedisDao.class);


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
     * 批量插入
     * @param map
     */
    public void multiSet(final Map map){
        ValueOperations valueOperations=redisTemplate.opsForValue();
        valueOperations.multiSet(map);
    }

    /**
     * 批量插入(如果存在则不插入，不存在则插入)
     * @param map
     */
    public void multiSetIfAbsent(final Map map){
        ValueOperations valueOperations=redisTemplate.opsForValue();
        valueOperations.multiSetIfAbsent(map);
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
        redisTemplate.delete(key);
    }


    /**
     * 根据key获取值
     * @param key
     * @return
     */
    public Object get(final String key){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }





}
