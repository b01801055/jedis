package com.ron.jedis.util;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JedisUtil {
    Jedis jedis = new Jedis("127.0.0.1", 6379);


    public void saveUidTag_IdType(){
        String UID = null;
        String TAG_ID = null;
        String TYPE = null;
        jedis.hset(UID, TAG_ID, TYPE);

        Map<String, String> map = new HashMap<>();
        map.put("TAG_ID1", "TYPE1");
        map.put("TAG_ID2", "TYPE3");
        jedis.hmset(UID, map);
    }

    public Map<String, String> findTag_IdTypeByUid(String UID) {
        return jedis.hgetAll(UID);
    }

    public void saveSTag_IdUid() {
        String TAG_ID = null;
        String STAG_ID = "S" + TAG_ID;
        String UID = null;
        jedis.sadd(STAG_ID, UID);
    }

    public void saveTag_IdTimeStampUid() {
        String TAG_ID = null;
        Double TIMESTAMP = null;
        String UID = null;
        Map<String, Double> map = new HashMap<>();
        map.put(UID,TIMESTAMP);
        jedis.zadd(TAG_ID, map);
    }

    public double findTag_IdTimeStampByUid(String UID) {
        saveTag_IdTimeStampUid();
        String TAG_ID = null;
        return jedis.zscore(TAG_ID,UID);
    }

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
