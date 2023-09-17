package org.quanta.core.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf
 * Date: 2023/9/17
 */
public class RedisUtils {
    // 基础操作模板
    private final RedisTemplate<String, Object> redisTemplate;
    // string类型
    private final ValueOperations<String, Object> valueOps;


    public RedisUtils(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOps = redisTemplate.opsForValue();
    }

    /**
     * 判断键是否存在
     */
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 删除键
     */
    public boolean del(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 删除多个键
     */
    public long del(String... key) {
        return redisTemplate.delete(Arrays.asList(key));
    }

    /**
     * 获取剩余时间
     * -1为未设置
     * -2为键不存在
     */
    public Long getExpire(String key) {
        return valueOps.getOperations().getExpire(key);
    }

    /**
     * 设置过期时间
     */
    public boolean setExpire(String key, long time, TimeUnit timeUnit) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 设置过期时间(s为单位)
     */
    public boolean setExpire(String key, long time) {
        return setExpire(key, time, TimeUnit.SECONDS);
    }

    /**
     * String类型操作
     * 获取值
     */
    public <T> T get(String key) {
        return (T) valueOps.get(key);
    }

    /**
     * String类型操作
     * 设置值
     */
    public void set(String key, Object value) {
        valueOps.set(key, value);
    }

    /**
     * String类型操作
     * 设置值并指定过期时间
     */
    public void set(String key, Object value, Long time, TimeUnit timeUnit) {
        valueOps.set(key, value, time, timeUnit);
    }

    /**
     * String类型操作
     * 设置值并指定过期时间(s为单位)
     */
    public void set(String key, Object value, Long time) {
        this.set(key, value, time, TimeUnit.SECONDS);
    }
}
