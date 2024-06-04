package com.beikei.backend.v2core.filter;

import com.beikei.backend.v2core.config.SystemKeyword;
import com.beikei.backend.v2core.enums.CacheEnum;
import com.beikei.backend.v2core.enums.ResponseEnum;
import com.beikei.backend.v2core.exception.V2GameException;
import com.beikei.backend.v2module.tenant.service.TenantService;
import com.beikei.backend.v2orm.entity.V2Tenant;
import com.beikei.backend.v2util.CacheUtil;
import com.beikei.backend.v2util.RedisUtil;
import com.beikei.backend.v2util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TenantFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tenantId = (String) Optional.ofNullable(request.getAttribute(SystemKeyword.X_TENANT_ID)).orElse("");
        if (!StringUtils.hasText(tenantId)) {
            throw new V2GameException(ResponseEnum.TENANT_PARAM_ERROR);
        }
        String cacheKey = CacheUtil.formatRedisKey(CacheEnum.TENANT, "active");
        String tenantIds = RedisUtil.getString(cacheKey);
        if (!StringUtils.hasText(tenantIds)) {
            loadingTenants();
            tenantIds = RedisUtil.getString(cacheKey);
        }
        List<String> tenantList = Arrays.asList(StringUtils.commaDelimitedListToStringArray(tenantIds));
        boolean valid = tenantList.contains(tenantId);
        if (!valid) {
            throw new V2GameException(ResponseEnum.TENANT_NOT_VALID);
        }
        // 将当前线程Tenant存储
        MDC.put(SystemKeyword.X_TENANT_ID, tenantId);
        filterChain.doFilter(request, response);
    }

    private void loadingTenants() {
        String lockKey = CacheUtil.formatRedisKey(CacheEnum.TENANT, "loading:lock");
        boolean lock = RedisUtil.lock(lockKey, 10, TimeUnit.SECONDS);
        if (lock) {
            try {
                TenantService tenantService = SpringUtil.getBean(TenantService.class);
                String tenantIdsString = StringUtils.arrayToDelimitedString(tenantService.tenants().stream().map(V2Tenant::getTenantId).toArray(), ",");
                String cacheKey = CacheUtil.formatRedisKey(CacheEnum.TENANT, "active");
                RedisUtil.setStringKeyValue(cacheKey,tenantIdsString);
            } catch (Exception e) {
                log.error("TenantFilter::LoadingTenants::出现错误{}",e.getMessage());
                throw new V2GameException(ResponseEnum.FAIL);
            } finally {
                RedisUtil.unlock(lockKey);
            }
        }
    }
}
