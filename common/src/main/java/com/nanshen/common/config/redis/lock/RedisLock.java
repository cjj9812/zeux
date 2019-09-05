package com.nanshen.common.config.redis.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCommands;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Component
public class RedisLock {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;



    private static final String OK="OK";
    private static final String NX="NX";
    private static final String PX="PX";
    //LUR脚本
    private static final String RELEASE_LOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    public Boolean tryLock(String lockKey, String clientId, long timeout, TimeUnit unit) {

        return stringRedisTemplate.execute((RedisCallback<Boolean>) connection -> {
            JedisCommands commands = (JedisCommands)connection.getNativeConnection();
            String result = commands.set(lockKey, clientId, NX, PX, unit.toMillis(timeout));
            return OK.equals(result);
        });
    }


    public Boolean releaseLock(String lockKey, String clientId) {
        return stringRedisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
            Jedis jedis = (Jedis) redisConnection.getNativeConnection();
            Object result = jedis.eval(RELEASE_LOCK_SCRIPT, Collections.singletonList(lockKey),
                    Collections.singletonList(clientId));
            if (OK.equals(result)) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        });
    }

}
