package com.ron.jedis;

import com.ron.jedis.util.JedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class JedisApplicationTests {
    @Autowired
    JedisUtil jedisUtil;

    @Test
    void test() {
//        jedisUtil.demoRedisHash();
    jedisUtil.demoSortedSet();
    }


}
