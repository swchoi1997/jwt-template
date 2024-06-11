package org.hacsick.jwttemplate.global.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class RedisConfigTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    void testREdis() {
        redisTemplate.opsForValue().set("testKey", "testValue", 200000);
        String value = redisTemplate.opsForValue().get("testKey");
        System.out.println("Retrieved value: " + value);
    }

}