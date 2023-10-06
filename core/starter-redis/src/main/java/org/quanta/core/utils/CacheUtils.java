package org.quanta.core.utils;

import org.quanta.base.utils.SpringUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

/**
 * Description: 缓存工具
 * Param:
 * return:
 * Author: wzf
 * Date: 2023/9/18
 */
public class CacheUtils {
    private volatile static CacheManager cacheManager;

    /**
     * 双检锁单例模式
     */
    private static CacheManager getCacheManager() {
        if (cacheManager == null) {
            synchronized (CacheManager.class) {
                if (cacheManager == null) {
                    cacheManager = SpringUtils.getBean(CacheManager.class);
                }
            }
        }
        return cacheManager;
    }

    /**
     * 获取缓存对象
     */
    public static Cache getCache(String name) {
        return cacheManager.getCache(name);
    }

    /**
     *
     * */
    public static Object get(String name,String keyPrefix,String key){
        return null;
    }
}
