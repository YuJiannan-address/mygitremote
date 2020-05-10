package com.lagou.edu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

@Configuration
@PropertySource(value = {"classpath:redis.properties"})
public class RedisConfiguration {
//    redis.host=192.168.33.111
//    redis.port=6379
//    redis.connectionTimeout=5000
//    redis.password=
//    redis.database=0

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private Integer port;

    @Value("${redis.connectionTimeout}")
    private Integer connectTimeout;

    @Value("${redis.password}")
    private String password;

    @Value("${redis.database}")
    private Integer database;

    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setTestOnBorrow(true);
        poolConfig.setMaxTotal(200);
        poolConfig.setMaxIdle(50);
        poolConfig.setMinIdle(10);
        JedisPool jedisPool = new JedisPool(
                poolConfig,
                host,
                port,
                connectTimeout,
                "".equals(password) ? null : password);
        return jedisPool;
    }
}
