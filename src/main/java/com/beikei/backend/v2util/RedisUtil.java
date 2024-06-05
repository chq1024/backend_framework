package com.beikei.backend.v2util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 主要用于简化Redis操作，可以替换客户端使用
 * @author bk
 */
@SuppressWarnings("unchecked")
@Slf4j
public class RedisUtil {

    private static final RedisTemplate<String,Object> redisTemplate;

    static {
        redisTemplate = SpringUtil.getBean("redisTemplate",RedisTemplate.class);
    }

    public static boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public static String getString(String key) {
        Object cache = getKey(key);
        return (String) cache;
    }

    public static List<String> getList(String key) {
        List<Object> cache = getListKey(key);
        return cache.stream().map(String::valueOf).collect(Collectors.toList());
    }

    private static Object getKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    private static List<Object> getListKey(String key) {
        return redisTemplate.opsForList().range(key,0,-1);
    }

    public static void setStringKeyValue(String key,Object value) {
         redisTemplate.opsForValue().set(key,value);
    }

    public static void setStringKeyValue(String key,Object value,long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key,value,timeout,unit);
    }



    public static boolean lock(String key,long timeout, TimeUnit unit) {
        boolean lock = Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, 1, timeout, unit));
        if (lock) {
            log.info("【redisTemplate lock】加锁:{} 成功!",key);
        }
        return lock;
    }

    public static void unlock(String key) {
        Object cache = redisTemplate.opsForValue().get(key);
        if (cache != null && Integer.valueOf(1) == cache) {
            boolean deleted = Boolean.TRUE.equals(redisTemplate.delete(key));
            if (deleted) {
                log.info("【redisTemplate lock】释放锁:{} 成功!",key);
            }
        }
    }
}
