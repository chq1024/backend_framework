package com.beikei.backend.v2core.filter;

import com.beikei.backend.v2core.enums.ResponseEnum;
import com.beikei.backend.v2core.exception.V2GameException;
import org.slf4j.MDC;

import javax.servlet.*;
import java.io.IOException;

public class TenantFilter implements Filter {

    /**
     * 需要去加载当前所有租户ID,并将它们存入redis
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String tenantId = (String) request.getAttribute("TENANT-ID");
        //TODO 校验tenantId是否存在缓存

        boolean valid = false;
        if (!valid) {
            throw new V2GameException(ResponseEnum.TENANT_NOT_VALID);
        }
        // 将当前线程请求需要的tenant_id进行存储
        MDC.put("TENANT_ID",tenantId);
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
