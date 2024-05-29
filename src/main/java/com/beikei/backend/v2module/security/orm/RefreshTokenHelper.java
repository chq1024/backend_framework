package com.beikei.backend.v2module.security.orm;

import cn.hutool.crypto.digest.MD5;
import com.beikei.backend.v2core.config.SecurityProperties;
import com.beikei.backend.v2core.core.V2CommentDomainHelper;
import com.beikei.backend.v2core.enums.ResponseEnum;
import com.beikei.backend.v2core.exception.V2GameException;
import com.beikei.backend.v2pojo.entity.V2RefreshToken;
import com.beikei.backend.v2pojo.mapper.RefreshTokenMapper;
import com.beikei.backend.v2util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author bk
 */
@Component
@Slf4j
public class RefreshTokenHelper extends V2CommentDomainHelper {

    private final RefreshTokenMapper refreshTokenMapper;
    private final SecurityProperties securityProperties;

    public RefreshTokenHelper(RefreshTokenMapper refreshTokenMapper,  SecurityProperties securityProperties) {
        super(refreshTokenMapper);
        this.refreshTokenMapper = refreshTokenMapper;
        this.securityProperties = securityProperties;
    }

    public V2RefreshToken queryAndCheck(Long uid,boolean refresh) {
        V2RefreshToken one = refreshTokenMapper.one(uid);
        if (one == null) {
            throw new V2GameException(ResponseEnum.DB_NOT_FOUND_DATA);
        }
        SecurityProperties.RefreshTokenProperties refreshTokenProperties = securityProperties.getRefreshToken();
        if (refreshTokenProperties.getLimit() >= one.getUsed() || one.getRefreshTime() + refreshTokenProperties.getExpire() >= DateUtil.now()) {
            if (!refresh) {
                log.error("{}的refreshToken已失效!",uid);
                throw new V2GameException(ResponseEnum.AUTHENTICATION_PARAM_ERROR);
            }
            return refresh(one);
        }
        return one;
    }

    public V2RefreshToken refresh(V2RefreshToken before) {
        String keyword = UUID.randomUUID().toString().replace("-","");
        keyword = MD5.create().digestHex(keyword, StandardCharsets.UTF_8);
        before.setKeyword(keyword);
        before.setUsed(0);
        before.setRefreshTime(DateUtil.now());
        refreshTokenMapper.update(before.getId(), before);
        return before;
    }
}
