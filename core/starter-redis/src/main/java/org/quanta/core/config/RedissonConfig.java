package org.quanta.core.config;

import jodd.util.StringUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf
 * Date: 2023/9/17
 */
@Configuration
public class RedissonConfig {
    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.password:}")
    private String password;

    @Value("${spring.redis.port}")
    private Integer port;

    @Bean
    public RedissonClient getRedisson() {
        Config config = new Config();
        config.useSingleServer().
                setAddress("redis://" + redisHost + ":" + port).
                setPassword(StringUtil.isBlank(password) ? null : password);
        config.setCodec(new JsonJacksonCodec());
        return Redisson.create(config);
    }

    /**
     * 注入redisson实现的CacheManager
     */
    @Bean
    CacheManager cacheManager(RedissonClient RedissonClient) {
        Map<String, CacheConfig> config = new HashMap<>();
        // create "user_cache" spring cache with ttl = 24 minutes and maxIdleTime = 12 minutes
        config.put("user_cache", new CacheConfig(24 * 60 * 1000, 12 * 60 * 1000));
        return new RedissonSpringCacheManager(RedissonClient, config);
    }
}
