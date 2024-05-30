package com.beikei.backend.v2module.security.orm;

import com.beikei.backend.v2core.core.V2CommentDomainHelper;
import com.beikei.backend.v2core.enums.CacheEnum;
import com.beikei.backend.v2core.enums.ResponseEnum;
import com.beikei.backend.v2core.exception.V2GameException;
import com.beikei.backend.v2module.security.cover.V2UserDetail;
import com.beikei.backend.v2pojo.entity.V2User;
import com.beikei.backend.v2pojo.mapper.UserMapper;
import com.beikei.backend.v2util.CacheUtil;
import com.beikei.backend.v2util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public V2UserDetail cacheQuery(Long id, String username) {
        String cacheKey = CacheUtil.formatRedisKey(CacheEnum.USER, String.valueOf(id));
        String userComm = RedisUtil.getString(cacheKey);
        if (!StringUtils.hasText(userComm)) {
            try {
                return (V2UserDetail) userDetailsService.loadUserByUsername(username);
            } catch (UsernameNotFoundException e) {
                log.error("{}用户不存在",username);
                return null;
            }
        }
        // account#1#1#rA|rB
        String[] cacheArr = userComm.split("#");
        V2User user = new V2User();
        user.setId(id);
        user.setAccount(userComm);
        user.setOpened("1".equals(cacheArr[1]));
        user.setValid("1".equals(cacheArr[2]));
        String[] roles = cacheArr[3].split("\\|");
        List<GrantedAuthority> characters  = Arrays.stream(roles).map(SimpleGrantedAuthority::new).collect(Collectors.toList());;
        return new V2UserDetail(user,characters);
    }
}
