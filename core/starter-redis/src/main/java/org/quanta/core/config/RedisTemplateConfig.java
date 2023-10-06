package org.quanta.core.config;

import org.quanta.core.serializer.RedisKeySerializer;
import org.quanta.core.utils.RedisUtils;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Description: RedisTemplate配置类
 * Param:
 * return:
 * Author: wzf
 * Date: 2023/9/17
 */
@EnableCaching
@Configuration(proxyBeanMethods = false)
//@AutoConfigureBefore()
public class RedisTemplateConfig {
    /**
     * 注册redis序列化器
     */
    @Bean
    @ConditionalOnMissingBean(RedisSerializer.class)
    public RedisSerializer<Object> redisSerializer() {
        // 使用jackson序列化
        return new GenericJackson2JsonRedisSerializer();
    }

    /**
     * 注册redisTemplate bean
     */
    @Bean(name = "redisTemplate")
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory,
                                                       RedisSerializer<Object> redisSerializer) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // key 序列化
        RedisKeySerializer keySerializer = new RedisKeySerializer();
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setHashKeySerializer(keySerializer);
        // value 序列化
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setHashValueSerializer(redisSerializer);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    /**
     * 注册redis工具
     * */
    @Bean("RedisUtils")
    public RedisUtils redisUtils(RedisTemplate<String, Object> redisTemplate){
        return new RedisUtils(redisTemplate);
    }
}
