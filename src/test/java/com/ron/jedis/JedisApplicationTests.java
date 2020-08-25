package com.ron.jedis;

import com.ron.jedis.util.JedisTest;
import com.ron.jedis.util.JedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

import java.util.Map;

@SpringBootTest
class JedisApplicationTests {
    @Autowired
    JedisUtil jedisUtil;

    @Autowired
    JedisTest jedisTest;

    @Test
    void test() {
//        jedisUtil.demoRedisHash();
//        jedisUtil.demoSortedSet();
//        jedisUtil.saveCountUnion();
//        jedisUtil.countByTag_Id();
        jedisUtil.demoRedisList();
    }

//    @Test
//    void test1() {
//
//        Jedis jedis = new Jedis("localhost", 6379);
//
//        jedisTest.testString(jedis);
//        jedisTest.testHash(jedis);
//        jedisTest.testList(jedis);
//        jedisTest.testSet(jedis);
//        jedisTest.testZSet(jedis);
//
//        jedisTest.testCommands(jedis);
//    }


}
