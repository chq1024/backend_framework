package com.beikei.backend.v2core.validator;

import com.beikei.backend.v2core.enums.CacheEnum;
import com.beikei.backend.v2core.enums.ResponseEnum;
import com.beikei.backend.v2core.exception.V2GameException;
import com.beikei.backend.v2module.user.cover.V2UserDetail;
import com.beikei.backend.v2module.user.service.PermissionService;
import com.beikei.backend.v2util.CacheUtil;
import com.beikei.backend.v2util.RedisUtil;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 用于spring_security SpdEL表达式
 * @author bk
 */
@Component
public class PermissionValidator implements PermissionEvaluator {

    private final PermissionService permissionService;
    PermissionValidator(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetSign, Object permission) {
        V2UserDetail principal = (V2UserDetail) authentication.getPrincipal();
        List<GrantedAuthority> authorities = principal.getAuthorities();
        String source = String.valueOf(targetSign);
        if (!"ROLE".equalsIgnoreCase(source) && !"PERMISSION".equalsIgnoreCase(source)) {
            throw new V2GameException(ResponseEnum.AUTHORIZATION_CHECK_ERROR);
        }
        List<Object> permissions = new ArrayList<>();
        if (permission instanceof String) {
            permissions.add(permission);
        }
        if (permission instanceof Collection) {
            permissions.addAll((Collection<?>) permission);
        }
        if ("ROLE".equalsIgnoreCase(source)) {
            GrantedAuthority grantedAuthority = authorities.stream().filter(r -> {
                return r.getAuthority().equals(permission) && r.getAuthority().equals("ROLE_" + permission);
            }).findAny().orElse(null);
            return grantedAuthority != null;
        }
        if ("PERMISSION".equalsIgnoreCase(source)) {
            for (GrantedAuthority authority : principal.getAuthorities()) {
                String character = authority.getAuthority().replace("ROLE_", "");
                String cacheKey = CacheUtil.formatRedisKey(CacheEnum.ROLE,character);
                List<String> permissions = RedisUtil.getList(cacheKey, () -> {
                    List<String> characterPermissions = permissionService.permission(character);
                    // 加入缓存
                    RedisUtil.leftPushIfPresent(cacheKey, Collections.singletonList(characterPermissions));
                    return Collections.singletonList(characterPermissions);
                });
                return permissions.contains(String.valueOf(permission));
            }
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
