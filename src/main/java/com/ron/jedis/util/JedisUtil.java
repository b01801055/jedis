package com.ron.jedis.util;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.*;

@Service
public class JedisUtil {
    Jedis jedis = new Jedis("127.0.0.1", 6379);


    public void demoRedisHash() {
        String UID = "U1c96a75ff9f519db7f6fa94b961d61ac";
        jedis.hset(UID, "1", "A");
        jedis.hset(UID, "2", "M");
        Map<String, String> map = jedis.hgetAll(UID);
        System.out.println("資料型別"+jedis.type(UID));
        System.out.println("新增一筆hash");
        System.out.println(map);
        Map<String, String> value = new HashMap<>();
        value.put("1", "M");
        value.put("3", "A");
        value.put("5", "A");
        jedis.hmset(UID, value);
        map = jedis.hgetAll(UID);
        System.out.println("一次新增多組hash");
        System.out.println(map);

        jedis.hset(UID, "2", "A");
        map = jedis.hgetAll(UID);
        System.out.println("修改hash");
        System.out.println(map);

        jedis.hdel(UID, "2");
        map = jedis.hgetAll(UID);
        System.out.println("刪除filed='2'的資料");
        System.out.println(map);
        jedis.del(UID);//刪除key為UID的全部資料
    }



    public void saveSTag_IdUid() {
        String TAG_ID = "1";
        String STAG_ID = "S" + TAG_ID;
        String UID = "U1c96a75ff9f519db7f6fa94b961d61ac";
        jedis.sadd(STAG_ID, UID);
    }

    public void demoSortedSet() {
        Map<String, Double> map = new HashMap<>();
        map.put("U1c96a75ff9f519db7f6fa94b961d61ac", 1596167926.0);
        map.put("U6f2afe88ff72ed1226f597e3d24f8160", 1596422074.0);
        map.put("Ub0558ab326282ceaab9a6a228a74b4f4", 1696422074.0);
        jedis.zadd("1", map);
        double rmap = jedis.zscore("1", "U1c96a75ff9f519db7f6fa94b961d61ac");
        System.out.println(rmap);
        Set<String> all = jedis.zrangeByScore("1", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        System.out.println(all);
        jedis.zrem("1", "U6f2afe88ff72ed1226f597e3d24f8160");
        all = jedis.zrangeByScore("1", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        System.out.println(all);
        System.out.println(jedis.zcount("1", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));


    }



    public Set<String> findUidByTag_Id(String TAG_ID, double min, double max) {
        return jedis.zrangeByScore(TAG_ID, min, max);
    }

    public void countByTag_Id() {
//        Long len = jedis.append("name", "aaa");
//        System.out.println(len);
//        System.out.println(jedis.get("name"));

        Long num = jedis.hset("hash1", "username", "caopengfei");
        System.out.println(num);
        String hget = jedis.hget("hash1", "username");
        System.out.println(hget);

        Set<String> hash1 = jedis.hkeys("hash1");
        System.out.println(hash1);

        List<String> listhash1 = jedis.hvals("hash1");
        System.out.println(listhash1);

    }

    public void saveCountUnion() {

        jedis.zadd("1", 1596167926, "U1c96a75ff9f519db7f6fa94b961d61ac");
        jedis.zadd("1", 1596167927, "U6f2afe88ff72ed1226f597e3d24f8160");
        jedis.zadd("2", 1596167928, "U6f2afe88ff72ed1226f597e3d24f8160");
        jedis.zadd("3", 1596167929, "U1c96a75ff9f519db7f6fa94b961d61ac");
        jedis.zadd("3", 1596167930, "U6f2afe88ff72ed1226f597e3d24f8160");
        jedis.zadd("3", 1596167931, "Ub0558ab326282ceaab9a6a228a74b4f4");

        jedis.zrange("1", 0, -1);

        jedis.zrange("2", 0, -1);

        jedis.zrange("3", 0, -1);

        Set<String> TMP1 = null;
        jedis.zunionstore("TMP1","1","2","3");
        TMP1 = jedis.zrangeByScore("TMP1", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        System.out.println(TMP1);

//        jedis.zinterstore("TMP1", "1", "2", "3");
//        TMP1 = jedis.zrangeByScore("TMP1", "-INF", "+INF");
//        System.out.println(TMP1);
    }

    public void saveCountInter(String TAG_ID) {
        Map<String, Double> map = new HashMap<>();
        String UID = null;
        jedis.zadd(UID, map);
        jedis.zadd(UID, map);
        jedis.zadd(UID, map);
        long TAG_COUNT = jedis.llen(UID);
        jedis.zinterstore(TAG_ID, String.valueOf(TAG_COUNT), UID, UID, UID);
    }

    public void demoRedisList() {
        jedis.flushDB();
        System.out.println("===========新增一個list===========");
        jedis.lpush("collections", "ArrayList", "Vector", "Stack", "HashMap",         "WeakHashMap", "LinkedHashMap");
        jedis.lpush("collections", "HashSet");
        jedis.lpush("collections", "TreeSet");
        jedis.lpush("collections", "TreeMap");
        System.out.println("collections的內容："+jedis.lrange("collections", 0, -1));//-1    代表倒數第一個元素，-2代表倒數第二個元素
        System.out.println("collections區間0-3的元素："+jedis.lrange("collections",0,3));
        System.out.println("===============================");

        System.out.println("刪除指定元素個數："+jedis.lrem("collections", 2, "HashMap"));
        System.out.println("collections的內容："+jedis.lrange("collections", 0, -1));
        System.out.println("刪除下表0-3區間之外的元素："+jedis.ltrim("collections", 0, 3));
        System.out.println("collections的內容："+jedis.lrange("collections", 0, -1));
        System.out.println("collections列表出棧（左端）："+jedis.lpop("collections"));
        System.out.println("collections的內容："+jedis.lrange("collections", 0, -1));
        System.out.println("collections新增元素，從列表右端，與lpush相對應："+jedis.rpush("collections", "EnumMap"));
        System.out.println("collections的內容："+jedis.lrange("collections", 0, -1));
        System.out.println("collections列表出棧（右端）："+jedis.rpop("collections"));
        System.out.println("collections的內容："+jedis.lrange("collections", 0, -1));
        System.out.println("修改collections指定下標1的內容："+jedis.lset("collections", 1, "LinkedArrayList"));
        System.out.println("collections的內容："+jedis.lrange("collections", 0, -1));
    }
}
