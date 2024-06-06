package com.beikei.backend.v2orm.helper;

import com.beikei.backend.v2core.core.V2CommentDomainHelper;
import com.beikei.backend.v2core.enums.CacheEnum;
import com.beikei.backend.v2core.enums.ResponseEnum;
import com.beikei.backend.v2core.exception.V2GameException;
import com.beikei.backend.v2module.user.cover.V2UserDetail;
import com.beikei.backend.v2orm.entity.V2User;
import com.beikei.backend.v2orm.mapper.UserMapper;
import com.beikei.backend.v2util.CacheUtil;
import com.beikei.backend.v2util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 用户orm辅助类
 *
 * @author bk
 */
@Component
@Slf4j
public class UserHelper extends V2CommentDomainHelper {

    private final UserMapper userMapper;

    private final UserDetailsService userDetailsService;

    public UserHelper(UserMapper userMapper, @Qualifier("myUserDetailService") UserDetailsService userDetailsService) {
        super(userMapper);
        this.userMapper = userMapper;
        this.userDetailsService = userDetailsService;
    }

    public V2User query(String username) {
        V2User v2User = userMapper.one(username);
        if (v2User == null) {
            throw new UsernameNotFoundException(ResponseEnum.USER_NOT_MATCH.getMessage());
        }
        return v2User;
    }

    @SuppressWarnings("unchecked")
    public V2UserDetail cacheQuery(Long uid, String username) {
        String cacheKey = CacheUtil.formatRedisKey(CacheEnum.USER, String.valueOf(uid));
        String userComm = RedisUtil.getString(cacheKey);
        if (!StringUtils.hasText(userComm)) {
            try {
                V2UserDetail v2UserDetail = (V2UserDetail) userDetailsService.loadUserByUsername(username);
                Long quid = v2UserDetail.getV2User().getId();
                // 这里有漏洞，可以通过账号查到其他玩家信息,谨慎
                if (!uid.equals(quid)) {
                    throw new V2GameException(ResponseEnum.ILLEGAL_ERROR);
                }
            } catch (UsernameNotFoundException e) {
                log.error("{}用户不存在",username);
                return null;
            }
        }
        Object[] parseUserCache = CacheUtil.parseUserCache(uid, userComm);
        V2User user  = (V2User) parseUserCache[0];
        List<GrantedAuthority> characters = (List<GrantedAuthority>) parseUserCache[1];
        return new V2UserDetail(user,characters);
    }

    // 暂不使用延迟双删
    public int update(Long uid,V2User user) {
        int update = userMapper.update(uid, user);
        if (update > 0) {
            String cacheKey = CacheUtil.formatRedisKey(CacheEnum.USER, String.valueOf(uid));
            RedisUtil.delete(cacheKey);
        }
        return update;
    }

    public void create(V2User user) {
        // 对初始密码加密

//        user.setPassword();
//        userMapper.create(v2User);
    }
}
