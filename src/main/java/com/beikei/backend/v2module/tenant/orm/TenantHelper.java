package com.beikei.backend.v2module.tenant.orm;

import com.beikei.backend.v2core.core.V2CommentDomainHelper;
import com.beikei.backend.v2core.enums.ResponseEnum;
import com.beikei.backend.v2core.exception.V2GameException;
import com.beikei.backend.v2pojo.mapper.TenantMapper;
import com.beikei.backend.v2pojo.entity.V2Tenant;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Tenant抽象层
 * helper层作用见V2CommentDomainHelper中的说明
 * @author bk
 */
@Component
public class TenantHelper extends V2CommentDomainHelper {

    private final TenantMapper tenantMapper;

    public TenantHelper(TenantMapper tenantMapper) {
        super(tenantMapper);
        this.tenantMapper = tenantMapper;
    }

    public List<V2Tenant> activeTenants() {
        return tenantMapper.load();
    }

    public V2Tenant selectByTenantId(String tenantId) {
        V2Tenant tenant = tenantMapper.one(tenantId);
        if (tenant == null) {
            throw new V2GameException(ResponseEnum.TENANT_NOT_EXITS);
        }
        return tenant;
    }
}
