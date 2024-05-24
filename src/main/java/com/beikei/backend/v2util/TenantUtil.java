package com.beikei.backend.v2util;

import com.beikei.backend.v2core.config.SystemKeyword;
import org.slf4j.MDC;

/**
 * 租户工具类
 * @author bk
 */
public class TenantUtil {

    public static String currTenantId() {
        return MDC.get(SystemKeyword.X_TENANT_ID);
    }
}
