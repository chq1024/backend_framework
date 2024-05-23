package com.beikei.backend.v2util;

import com.beikei.backend.v2core.config.SystemKeyword;
import com.beikei.backend.v2core.enums.CacheEnum;

/**
 * 用于缓存的工具类
 * @author bk
 */
public class CacheUtil {

    public static String formatRedisKey(CacheEnum belong,String key) {
        return belong.getBelong() + SystemKeyword.REDIS_SPLIT_CHAR + key;
    }
}
