package com.nanshen.common.config.redis

import com.nanshen.web.WebApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(classes = WebApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StringRedisDaoTest extends Specification {

    @Autowired
    StringRedisDao stringRedisDao;

    def "test set"() {
        expect:
        stringRedisDao.set(key,value);
        where:
        key | value
        "key1" | "插入值"
    }
}
