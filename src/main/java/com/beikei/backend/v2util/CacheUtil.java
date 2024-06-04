package com.beikei.backend.v2util;

import com.beikei.backend.v2core.config.SystemKeyword;
import com.beikei.backend.v2core.enums.CacheEnum;
import com.beikei.backend.v2orm.entity.V2User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用于缓存的工具类
 * @author bk
 */
public class CacheUtil {

    public static String formatRedisKey(CacheEnum belong,String key) {
        return belong.getBelong() + SystemKeyword.REDIS_SPLIT_CHAR + key;
    }

    public static String formatUserCache(V2User user, List<GrantedAuthority> authorities) {
        String characters = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining("|"));
        return user.getAccount() + "#" + (user.getOpened()?1:0) + "#" + (user.getValid()?1:0) + "#" + characters;
    }

    public static Object[] parseUserCache(Long uid,String userCacheKey) {
        String[] cacheArr = userCacheKey.split("#");
        V2User user = new V2User();
        user.setId(uid);
        user.setAccount(cacheArr[0]);
        user.setOpened("1".equals(cacheArr[1]));
        user.setValid("1".equals(cacheArr[2]));
        String[] roles = cacheArr[3].split("\\|");
        List<GrantedAuthority> characters  = Arrays.stream(roles).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        Object[] res = new Object[2];
        res[0] = user;
        res[1] = characters;
        return res;
    }
}
