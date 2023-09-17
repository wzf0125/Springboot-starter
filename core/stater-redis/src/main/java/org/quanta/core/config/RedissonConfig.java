package org.quanta.core.config;

import jodd.util.StringUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
