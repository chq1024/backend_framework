package com.beikei.backend.v2core.filter;

import com.beikei.backend.v2core.config.SystemKeyword;
import com.beikei.backend.v2core.enums.CacheEnum;
import com.beikei.backend.v2core.enums.ResponseEnum;
import com.beikei.backend.v2core.exception.V2GameException;
import com.beikei.backend.v2module.tenant.service.TenantService;
import com.beikei.backend.v2pojo.entity.V2Tenant;
import com.beikei.backend.v2util.CacheUtil;
import com.beikei.backend.v2util.RedisUtil;
import com.beikei.backend.v2util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
public class TenantFilter implements Filter {

    /**
     * 需要去加载当前所有租户ID,并将它们存入redis
     */
    @Override
    public void init(FilterConfig filterConfig) {
        loadingTenants();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
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

    @Override
    public void destroy() {
        Filter.super.destroy();
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
