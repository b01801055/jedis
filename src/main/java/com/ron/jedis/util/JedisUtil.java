package com.ron.jedis.util;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class JedisUtil {
    Jedis jedis = new Jedis("127.0.0.1", 6379);


    public void demoRedisHash(){
        String UID = "U1c96a75ff9f519db7f6fa94b961d61ac";
        jedis.hset(UID, "1", "A");
        jedis.hset(UID, "2", "M");
        Map<String, String> map = jedis.hgetAll(UID);
        System.out.println(map);

        Map<String, String> value = new HashMap<>();
        value.put("1", "M");
        value.put("3", "A");
        value.put("5", "A");
        jedis.hmset(UID, value);
        map = jedis.hgetAll(UID);
        System.out.println(map);

        jedis.hset(UID, "2", "A");
        map = jedis.hgetAll(UID);
        System.out.println(map);

        jedis.hdel(UID, "2");
        map = jedis.hgetAll(UID);
        System.out.println(map);
        jedis.del(UID);
    }

//    public Map<String, String> findTag_IdTypeByUid(String UID) {
//        return jedis.hgetAll(UID);
//    }

    public void saveSTag_IdUid() {
        String TAG_ID = "1";
        String STAG_ID = "S" + TAG_ID;
        String UID = "U1c96a75ff9f519db7f6fa94b961d61ac";
        jedis.sadd(STAG_ID, UID);
    }

    public void demoSortedSet() {
        Map<String, Double> map = new HashMap<>();
        map.put("U1c96a75ff9f519db7f6fa94b961d61ac",1596167926.0);
        map.put("U6f2afe88ff72ed1226f597e3d24f8160",1596422074.0);
        map.put("Ub0558ab326282ceaab9a6a228a74b4f4",1696422074.0);
        jedis.zadd("1", map);
        double rmap = jedis.zscore("1","U1c96a75ff9f519db7f6fa94b961d61ac");
        System.out.println(rmap);
        Set<String> all = jedis.zrangeByScore("1", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        System.out.println(all);
        jedis.zrem("1", "U6f2afe88ff72ed1226f597e3d24f8160");
        all = jedis.zrangeByScore("1", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        System.out.println(all);
        System.out.println(jedis.zcount("1", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));
        map.put("U6f2afe88ff72ed1226f597e3d24f8160",1596422074.0);
        map.put("U6f2afe88ff72ed1226f597e3d24f8160",1596167928.0);
        map.put("U1c96a75ff9f519db7f6fa94b961d61ac",1596167929.0);
        map.put("U6f2afe88ff72ed1226f597e3d24f8160", 1596167930.0);
        map.put("Ub0558ab326282ceaab9a6a228a74b4f4", 1596167931.0);

        jedis.zadd("1",map);
        jedis.zadd("2", map);
        jedis.zadd("3", map);
        Set<String> TMP1=null;
        jedis.zunionstore("TMP1",map);
    }

//    public double findTag_IdTimeStampByUid(String UID) {
//        saveTag_IdTimeStampUid();
//        String TAG_ID = null;
//        return jedis.zscore(TAG_ID,UID);
//    }

    public Set<String> findUidByTag_Id(String TAG_ID,double min,double max) {
        return jedis.zrangeByScore(TAG_ID,min,max);
    }

    public long countByTag_Id(String TAG_ID,double min,double max) {
        return jedis.zcount(TAG_ID,min,max);
    }

    public void saveCountUnion(String TAG_ID) {
        Map<String,Double> map = new HashMap<>();
        String UID = null;
        jedis.zadd(UID,map);
        jedis.zadd(UID,map);
        jedis.zadd(UID,map);
        long TAG_COUNT = jedis.llen(UID);
        jedis.zunionstore(TAG_ID, String.valueOf(TAG_COUNT),UID,UID,UID);
    }

    public void saveCountInter(String TAG_ID) {
        Map<String,Double> map = new HashMap<>();
        String UID = null;
        jedis.zadd(UID,map);
        jedis.zadd(UID,map);
        jedis.zadd(UID,map);
        long TAG_COUNT = jedis.llen(UID);
        jedis.zinterstore(TAG_ID, String.valueOf(TAG_COUNT),UID,UID,UID);
    }
}
