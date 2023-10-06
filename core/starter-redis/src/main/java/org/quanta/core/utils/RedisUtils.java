package org.quanta.core.utils;

import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Description: 原始redis操作封装
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
    // hash类型
    private final HashOperations<String, Object, Object> hashOps;
    // zset操作
    private final ZSetOperations<String, Object> zSetOps;
    // set操作
    private final SetOperations<String, Object> setOps;
    // list操作
    private final ListOperations<String, Object> listOps;


    public RedisUtils(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOps = redisTemplate.opsForValue();
        this.hashOps = redisTemplate.opsForHash();
        this.zSetOps = redisTemplate.opsForZSet();
        this.setOps = redisTemplate.opsForSet();
        this.listOps = redisTemplate.opsForList();
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
     * 获取多个值
     */
    public <T> List<T> mget(String... keys) {
        return mget(Arrays.asList(keys));
    }

    /**
     * String类型操作
     * 获取多个值
     */
    public <T> List<T> mget(Collection<String> keys) {
        return (List<T>) valueOps.multiGet(keys);
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

    /**
     * incr自增操作
     * delta 步长
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("delta must greater than zero");
        }
        return valueOps.increment(key, delta);
    }

    /**
     * incr自增
     * 默认步长为1
     */
    public long incr(String key) {
        return this.incr(key, 1);
    }

    /**
     * decr自减操作
     * delta 步长
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("delta must greater than zero");
        }
        return valueOps.decrement(key, delta);
    }

    /**
     * decr自减操作
     * 默认步长1
     */
    public long decr(String key) {
        return this.decr(key, 1);
    }

    /**
     * hash类型
     * 获取hash值
     */
    public <T> T hget(String key, String item) {
        return (T) hashOps.get(key, item);
    }

    /**
     * hash类型
     * 获取一个hash的所有key
     */
    public Map<Object, Object> hmget(String key) {
        return hashOps.entries(key);
    }

    /**
     * hash类型
     * 设置一个hash类型 带时长 默认秒(可能出现问题，后续考虑使用lua脚本解决)
     */
    public void hset(String key, String item, Object value, long time) {
        this.hset(key, item, value, time, TimeUnit.SECONDS);
    }

    /**
     * hash类型
     * 设置一个hash类型(可能出现问题，后续考虑使用lua脚本解决)
     */
    public void hset(String key, String item, Object value, long time, TimeUnit timeUnit) {
        hashOps.put(key, item, value);
        if (time > 0) {
            setExpire(key, time, timeUnit);
        }
    }

    /**
     * hash类型
     * 批量设置
     */
    public void hmset(String key, Map<Object, Object> value) {
        hashOps.putAll(key, value);
    }

    /**
     * hash类型
     * 批量设置 并指定时间 默认秒
     */
    public void hmset(String key, Map<Object, Object> value, long time) {
        this.hmset(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * hash类型
     * 批量设置 并指定时间
     */
    public void hmset(String key, Map<Object, Object> value, long time, TimeUnit timeUnit) {
        hashOps.putAll(key, value);
        if (time > 0) {
            setExpire(key, time, timeUnit);
        }
    }

    /**
     * hash类型
     * 删除hash key
     */
    public void hdel(String key, Object... item) {
        hashOps.delete(key, item);
    }

    /**
     * hash类型
     * 判断是否存在hash key
     */
    public boolean hHasKey(String key, String item) {
        return hashOps.hasKey(key, item);
    }

    /**
     * hash类型
     * 自增hash key
     */
    public double hincr(String key, String item, double delta) {
        return hashOps.increment(key, item, delta);
    }

    /**
     * hash类型
     * 自减hash key
     */
    public double hdecr(String key, String item, double delta) {
        return hashOps.increment(key, item, -delta);
    }

    /**
     * zset类型
     * 在 zset中插入一条数据
     *
     * @param key   键
     * @param value 要插入的值
     * @param score 设置分数
     */

    public void zAdd(String key, Object value, long score) {
        zSetOps.add(key, value, score);
    }

    /**
     * zset类型
     * 在zset中让分数自增
     */
    public void zIncr(String key, Object value) {
        zSetOps.incrementScore(key, value, 1);
    }

    /**
     * zset类型
     * 在zset中让分数自增
     */
    public void zIncr(String key, Object value, long score) {
        zSetOps.incrementScore(key, value, score);
    }

    /**
     * 得到排名在 rank1，rank2 之间的值
     *
     * @param key   键
     * @param rank1 起始排位
     * @param rank2 终止排位
     * @return 范围内所有值
     */
    public Set<Object> zRange(String key, long rank1, long rank2) {
        return zSetOps.range(key, rank1, rank2);
    }

    /**
     * 得到分数在 score1，score2 之间的值
     *
     * @param key    键
     * @param score1 起始分数
     * @param score2 终止分数
     * @return 范围内所有值
     */
    public Set<Object> zRangeByScore(String key, long score1, long score2) {
        return zSetOps.rangeByScore(key, score1, score2);
    }

    /**
     * 根据value删除，并返回删除个数
     *
     * @param key   键
     * @param value 要删除的值，可传入多个
     * @return 删除个数
     */
    public Long zRemove(String key, Object... value) {
        return zSetOps.remove(key, value);
    }

    /**
     * 获取所有zset数据
     *
     * @param key Redis键
     */
    public Set<ZSetOperations.TypedTuple<Object>> zGetSetAll(final String key) {
        return zSetOps.rangeWithScores(key, 0, -1);
    }


    /**
     * 设置分布式锁
     *
     * @param key    键，可以用用户主键
     * @param value  值，可以传requestId，可以保证锁不会被其他请求释放，增加可靠性
     * @param expire 锁的时间(秒)
     * @return 设置成功为 true
     */
    public Boolean setNx(String key, Object value, long expire) {
        return valueOps.setIfAbsent(key, value, expire, TimeUnit.SECONDS);
    }

    /**
     * 设置分布式锁，有等待时间
     *
     * @param key     键，可以用用户主键
     * @param value   值，可以传requestId，可以保证锁不会被其他请求释放，增加可靠性
     * @param expire  锁的时间(秒)
     * @param timeout 在timeout时间内仍未获取到锁，则获取失败
     * @return 设置成功为 true
     */
    public Boolean setNx(String key, Object value, long expire, long timeout) {
        long start = System.currentTimeMillis();
        //在一定时间内获取锁，超时则返回错误
        while (true) {
            // 获取到锁，并设置过期时间返回true
            if (Boolean.TRUE.equals(valueOps.setIfAbsent(key, value, expire, TimeUnit.SECONDS))) {
                return true;
            }
            //否则循环等待，在timeout时间内仍未获取到锁，则获取失败
            if (System.currentTimeMillis() - start > timeout) {
                return false;
            }
        }
    }

    /**
     * 释放分布式锁
     *
     * @param key   锁
     * @param value 值，可以传requestId，可以保证锁不会被其他请求释放，增加可靠性
     * @return 成功返回true, 失败返回false
     */
    public boolean releaseNx(String key, Object value) {
        Object currentValue = valueOps.get(key);
        if (String.valueOf(currentValue) != null && value.equals(currentValue)) {
            return Boolean.TRUE.equals(valueOps.getOperations().delete(key));
        }
        return false;
    }


    /**
     * list操作
     * 向列表中放入键
     */
    public long lPush(String key, Object value) {
        return listOps.leftPush(key, value);
    }

    /**
     * list操作
     * 批量从列表左侧放入值
     */
    public long lPush(String key, Object... value) {
        return listOps.leftPushAll(key, value);
    }

    /**
     * list操作
     * 从列表左侧弹出值
     */
    public <T> T lPop(String key) {
        return (T) listOps.leftPop(key);
    }

    /**
     * list操作
     * 从列表右侧放入值
     */
    public long rPush(String key, Object value) {
        return listOps.leftPush(key, value);
    }

    /**
     * list操作
     * 从列表右侧批量放入值
     */
    public long rPush(String key, Object... value) {
        return listOps.leftPushAll(key, value);
    }

    /**
     * list操作
     * 从列表右侧弹出值
     */
    public <T> T rPop(String key) {
        return (T) listOps.leftPop(key);
    }

    /**
     * set操作
     * 放入元素
     */
    public void sadd(String key, Object... value) {
        setOps.add(key, value);
    }

    /**
     * set操作
     * 移除并返回集合中的一个随机元素。
     */
    public <T> T sPop(String key) {
        return (T) setOps.pop(key);
    }

    /**
     * set操作
     * 获取set中的所有元素
     */
    public <T> Set<T> sMember(String key) {
        return (Set<T>) setOps.members(key);
    }


}
