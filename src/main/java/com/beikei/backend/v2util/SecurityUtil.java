package com.beikei.backend.v2util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.beikei.backend.v2core.enums.CacheEnum;
import com.beikei.backend.v2core.enums.ResponseEnum;
import com.beikei.backend.v2core.exception.V2GameException;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * 提供部分加密解密方法
 * @author bk
 */
public class SecurityUtil {

    public static String passwordDecode(String password) {
        RSA rsa = currRsa();
        byte[] decode = Base64.decode(StrUtil.bytes(password, StandardCharsets.UTF_8));
        byte[] decrypt = rsa.decrypt(decode, KeyType.PrivateKey);
        return new String(decrypt,StandardCharsets.UTF_8);
    }

    public static RSA currRsa() {
        String lockKey = CacheUtil.formatRedisKey(CacheEnum.SECURITY, "lock");
        String priKey = CacheUtil.formatRedisKey(CacheEnum.SECURITY, "priKey");
        String pubKey = CacheUtil.formatRedisKey(CacheEnum.SECURITY, "pubKey");
        boolean lock = RedisUtil.lock(lockKey, 5, TimeUnit.SECONDS);
        if (lock) {
            try {
                boolean hasKey = RedisUtil.hasKey(priKey);
                if (hasKey) {
                    String priKeyValue = RedisUtil.getString(priKey);
                    String pubKeyValue = RedisUtil.getString(pubKey);
                    return new RSA(pubKeyValue, priKeyValue);
                } else {
                    return refreshRsa();
                }
            } finally {
                RedisUtil.unlock(lockKey);
            }
        }
        throw new V2GameException(ResponseEnum.UNKNOWN_ERROR);
    }


    private static RSA refreshRsa() {
        RSA rsa = new RSA();
        String priKey = CacheUtil.formatRedisKey(CacheEnum.SECURITY, "priKey");
        String pubKey = CacheUtil.formatRedisKey(CacheEnum.SECURITY, "pubKey");
        String privateKeyBase64 = rsa.getPrivateKeyBase64();
        String publicKeyBase64 = rsa.getPublicKeyBase64();
        RedisUtil.setStringKeyValue(priKey, privateKeyBase64, 7, TimeUnit.DAYS);
        RedisUtil.setStringKeyValue(pubKey, publicKeyBase64, 7, TimeUnit.DAYS);
        return rsa;
    }
}
