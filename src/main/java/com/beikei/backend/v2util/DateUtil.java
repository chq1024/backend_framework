package com.beikei.backend.v2util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * 时间工具类
 * @author bk
 */
public class DateUtil {

    public static Long now() {
        return LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
    }
}
