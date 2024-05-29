package com.beikei.backend.v2util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * 时间工具类
 * @author bk
 */
public class DateUtil {

    public static Long now() {
        return LocalDateTime.now().toEpochSecond(zoneOffset());
    }

    public static LocalDateTime localDate() {
        return LocalDateTime.now(zoneId());
    }

    public static LocalDateTime date2LocalDateTime(Date date) {
        return date.toInstant().atZone(zoneId()).toLocalDateTime();
    }

    private static ZoneId zoneId() {
        return ZoneId.of("GMT+8");
    }

    private static ZoneOffset zoneOffset() {
        return ZoneOffset.of("+8");
    }
}
