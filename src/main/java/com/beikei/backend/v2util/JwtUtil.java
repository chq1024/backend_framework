package com.beikei.backend.v2util;

import com.beikei.backend.v2core.config.SecurityProperties;
import com.beikei.backend.v2core.enums.CacheEnum;
import com.beikei.backend.v2core.enums.ResponseEnum;
import com.beikei.backend.v2core.exception.V2GameException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * jwt工具类
 *
 * @author bk
 */
@Component
@Import({SecurityProperties.class})
public class JwtUtil {

    private static String secret;

    @Value("${security.secret}")
    public static void setSecret(String secret) {
        JwtUtil.secret = secret;
    }

    /**
     * 解析token，使用当前的secret解析
     *
     * @param token 需要被解析的token
     * @return 解析后的body
     */
    public static Claims parseToken(String token, String keyword) {
        String key = StringUtils.hasText(keyword) ? keyword : secret;
        SecretKey secretKey = secretKey(key);
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }

    public static String genderAccessToken(Long uid, String username) {
        Map<String, Object> accessTokenMap = new HashMap<>();
        accessTokenMap.put("sub", uid);
        accessTokenMap.put("username", username);
        LocalDateTime now = DateUtil.localDate().plusSeconds(7200);
        Date expireDateTime = Date.from(now.toInstant(ZoneOffset.of("+8")));
        return Jwts.builder().addClaims(accessTokenMap).setExpiration(expireDateTime).signWith(secretKey(secret), SignatureAlgorithm.HS256).compact();
    }

    public static String genderRefreshToken(Long uid, String username,String keyword) {
        Map<String,Object> refreshTokenMap = new HashMap<>();
        refreshTokenMap.put("sub", uid);
        refreshTokenMap.put("username", username);
        return Jwts.builder().addClaims(refreshTokenMap).signWith(secretKey(keyword), SignatureAlgorithm.HS256).compact();
    }

    private static SecretKey secretKey(String keyword) {
        byte[] decode = keyword.getBytes();
        return new SecretKeySpec(decode, 0, decode.length, "HmacSHA256");
    }

}
