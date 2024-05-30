package com.beikei.backend.v2module.security;

import com.beikei.backend.v2core.enums.CacheEnum;
import com.beikei.backend.v2module.security.cover.V2UserDetail;
import com.beikei.backend.v2module.security.orm.UserCharacterRelationShipHelper;
import com.beikei.backend.v2module.security.orm.UserHelper;
import com.beikei.backend.v2module.tenant.orm.TenantHelper;
import com.beikei.backend.v2pojo.entity.V2Tenant;
import com.beikei.backend.v2pojo.entity.V2User;
import com.beikei.backend.v2util.CacheUtil;
import com.beikei.backend.v2util.DateUtil;
import com.beikei.backend.v2util.RedisUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户信息加载类，服务于SpringSecurity Auth
 * @author bk
 */
@Service("myUserDetailService")
@Transactional("primaryTransactionManager")
public class MyUserDetailService implements UserDetailsService {

    private final UserHelper userHelper;
    private final TenantHelper tenantHelper;
    private final UserCharacterRelationShipHelper shipHelper;

    public MyUserDetailService(UserHelper userHelper, TenantHelper tenantHelper, UserCharacterRelationShipHelper shipHelper) {
        this.userHelper = userHelper;
        this.tenantHelper = tenantHelper;
        this.shipHelper = shipHelper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        V2User user = userHelper.query(username);
        V2Tenant tenant = tenantHelper.selectByTenantId(user.getTenantId());
        List<GrantedAuthority> authorities = shipHelper.query(user.getId());
        user.setValid(tenant.getStatus() ==  1 && tenant.getExpireTime() > DateUtil.now());
        // 存入redis
        String cacheKey = CacheUtil.formatRedisKey(CacheEnum.USER,String.valueOf(user.getId()));
        String characters = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining("|"));
        RedisUtil.setStringKeyValue(cacheKey,user.getAccount() + "#" + (user.getOpened()?1:0) + "#" + (user.getValid()?1:0) + "#" + characters);
        return new V2UserDetail(user,authorities);
    }
}
