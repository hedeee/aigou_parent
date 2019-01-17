package org.hedee.aigou.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
* 工具类
 * 创建连接池
 * 获取连接对象
 * 释放连接
* */
public enum RedisJedisPoolUtil {
    INSTANCE;
    private static JedisPool pool=null;
    static{
        //        连接池配置
        JedisPoolConfig config = new JedisPoolConfig();
//        设置连接池配置的参数,设置最小连接数
        config.setMaxIdle(2);
//        设置最大连接数
        config.setMaxTotal(10);
//        设置最大连接时间，是jedis与连接池的连接时间
        config.setMaxWaitMillis(10000);
//        设置检验连接是否畅通
        config.setTestOnBorrow(true);
//        创建连接池,这里的timeout是连接池与Redis数据库的连接时间
        pool = new JedisPool(config, "127.0.0.1", 6379,3000, "root");
    }
//    获得连接
    public  Jedis getJedis(){
        return pool.getResource();
    }

//    释放连接
    public void close(Jedis jedis){
        if (jedis != null) {
            jedis.close();
        }
    }

    /**
     * 设置字符值
     *
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        Jedis jedis = getJedis();
        jedis.set(key, value);
        close(jedis);
    }


    /**
     * 设置字符值
     *
     * @param key
     */
    public String get(String key) {
        Jedis jedis = getJedis();
        try {
            return jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(jedis);
        }

        return null;

    }
}
