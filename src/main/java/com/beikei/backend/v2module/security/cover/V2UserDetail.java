package com.beikei.backend.v2module.security.cover;

import com.beikei.backend.v2pojo.entity.V2Tenant;
import com.beikei.backend.v2pojo.entity.V2User;
import com.beikei.backend.v2util.DateUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 *
 *
 * @author bk
 */
public class V2UserDetail implements UserDetails {

    private final V2User v2User;

    private final V2Tenant v2Tenant;

    private final List<GrantedAuthority> characters;

    public V2UserDetail(V2User v2User, List<GrantedAuthority> characters,V2Tenant v2Tenant) {
        this.v2User = v2User;
        this.v2Tenant = v2Tenant;
        this.characters = characters;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Optional.ofNullable(characters).orElse(new ArrayList<>());
    }

    @Override
    public String getPassword() {
        return v2User.getPassword();
    }

    @Override
    public String getUsername() {
        return v2User.getAccount();
    }

    /**
     * 账号是否失效
     * 当前判定账号是否失效的依据是当前租户是否处于正常开通状态
     * @return true/false
     */
    @Override
    public boolean isAccountNonExpired() {
        return v2Tenant.getStatus() ==  1 && v2Tenant.getExpireTime() > DateUtil.now();
    }

    /**
     * 用户账号是否被锁定
     * 当前判断账号是否被锁定根据user的opened字段判断
     * 用户在非法操作或离职时被设置为false
     * @return true/false
     */
    @Override
    public boolean isAccountNonLocked() {
        return v2User.getOpened();
    }

    /**
     * 用户凭证是否过期
     * @return true/false
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 账户是否启用
     * 当前判定与账号是否被锁定相同
     * @return true/false
     */
    @Override
    public boolean isEnabled() {
        return v2User.getOpened();
    }
}
