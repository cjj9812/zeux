package com.nanshen.common.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {




    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        StringRedisSerializer stringRedisSerializer=new StringRedisSerializer();
        FastJsonRedisSerializer fastJsonRedisSerializer=new FastJsonRedisSerializer(Object.class);
        template.setConnectionFactory(redisConnectionFactory);
        //设置序列化key使用字符串序列化
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        //设置序列化value使用fastjson序列化
        template.setHashValueSerializer(fastJsonRedisSerializer);
        template.setValueSerializer(fastJsonRedisSerializer);
        return template;
    }

}
