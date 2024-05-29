package com.beikei.backend.v2module.security.orm;

import com.beikei.backend.v2core.core.V2CommentDomainHelper;
import com.beikei.backend.v2core.enums.CacheEnum;
import com.beikei.backend.v2core.enums.ResponseEnum;
import com.beikei.backend.v2module.security.cover.V2UserDetail;
import com.beikei.backend.v2pojo.entity.V2User;
import com.beikei.backend.v2pojo.mapper.UserMapper;
import com.beikei.backend.v2util.CacheUtil;
import com.beikei.backend.v2util.RedisUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户orm辅助类
 * @author bk
 */
@Component
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

    public Map<String,Object> cacheQuery(Long id, String username) {
        String cacheKey = CacheUtil.formatRedisKey(CacheEnum.USER,String.valueOf(id));
        String userComm = RedisUtil.getString(cacheKey);
        if (!StringUtils.hasText(userComm)) {
            V2User user = query(username);
            V2UserDetail userDetails = (V2UserDetail) userDetailsService.loadUserByUsername(username);
            String cache = user.getAccount() + "#" + user.getTenantId() + "#" + (user.getOpened()?1:0) + "#" + "";
            RedisUtil.setStringKeyValue(cacheKey,cache);
        }
        String[] cacheArr = userComm.split("#");
        Map<String,Object> res = new HashMap<>();
        V2User v2User = new V2User();
        v2User.setId(id);
        v2User.setAccount(cacheArr[0]);
        v2User.setTenantId(cacheArr[1]);
        v2User.setOpened("1".equals(cacheArr[2]));
        res.put()
    }
}
