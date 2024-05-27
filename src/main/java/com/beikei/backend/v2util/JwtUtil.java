package com.beikei.backend.v2util;

import com.beikei.backend.v2module.security.cover.V2Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * jwt工具类
 * @author bk
 */
@Component
public class JwtUtil {

    public static Claims parseToken(String token) {

        return null;
    }

    public static V2Token genderToken(Long uid, String username, String roles) {
        V2Token v2Token = new V2Token();
        v2Token.setAccessToken(genderAccessToken0(uid,username,roles));
        return v2Token;
    }

    public static boolean validToken(Claims claims) {

        return false;
    }

    private static String genderAccessToken0(Long uid,String username,String roles) {
        Map<String,Object> accessTokenMap = new HashMap<>();
        accessTokenMap.put("sub",uid);
        accessTokenMap.put("username",username);
        accessTokenMap.put("roles",roles);
        return "";
    }

    private static String genderRefreshToken0(String username,Integer times) {

        return "";
    }

}
