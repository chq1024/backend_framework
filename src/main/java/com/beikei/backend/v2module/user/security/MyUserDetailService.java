package com.beikei.backend.v2module.user.security;

import com.beikei.backend.v2core.enums.CacheEnum;
import com.beikei.backend.v2module.user.cover.V2UserDetail;
import com.beikei.backend.v2orm.helper.UserCharacterRelationShipHelper;
import com.beikei.backend.v2orm.helper.UserHelper;
import com.beikei.backend.v2orm.helper.TenantHelper;
import com.beikei.backend.v2orm.entity.V2Tenant;
import com.beikei.backend.v2orm.entity.V2User;
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
        V2Tenant tenant = tenantHelper.queryByTenantId(user.getTenantId());
        List<GrantedAuthority> authorities = shipHelper.queryByUid(user.getId());
        user.setValid(tenant.getStatus() ==  1 && tenant.getExpireTime() > DateUtil.now());
        String cacheKey = CacheUtil.formatRedisKey(CacheEnum.USER, String.valueOf(user.getId()));
        String cacheValue = CacheUtil.formatUserCache(user, authorities);
        RedisUtil.setStringKeyValue(cacheKey,cacheValue);
        return new V2UserDetail(user,authorities);
    }
}
