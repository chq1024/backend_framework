package com.beikei.backend.v2module.security;

import com.beikei.backend.v2module.security.orm.UserHelper;
import com.beikei.backend.v2module.security.orm.V2UserDetail;
import com.beikei.backend.v2module.tenant.orm.TenantHelper;
import com.beikei.backend.v2pojo.entity.V2Tenant;
import com.beikei.backend.v2pojo.entity.V2User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户信息加载类，服务于SpringSecurity Auth
 * @author bk
 */
@Service
@Transactional("primaryTransactionManager")
public class V2UserDetailService implements UserDetailsService {

    private final UserHelper userHelper;
    private final TenantHelper tenantHelper;

    public V2UserDetailService(UserHelper userHelper, TenantHelper tenantHelper) {
        this.userHelper = userHelper;
        this.tenantHelper = tenantHelper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        V2User user = userHelper.selectByUserName(username);
        V2Tenant v2Tenant = tenantHelper.selectByTenantId(user.getTenantId());

        return new V2UserDetail();
    }
}
