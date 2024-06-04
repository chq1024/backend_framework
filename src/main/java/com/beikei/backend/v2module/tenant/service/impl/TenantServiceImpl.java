package com.beikei.backend.v2module.tenant.service.impl;

import com.beikei.backend.v2orm.helper.TenantHelper;
import com.beikei.backend.v2module.tenant.service.TenantService;
import com.beikei.backend.v2orm.entity.V2Tenant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Tenant业务层
 * @author bk
 */
@Service
@Transactional("primaryTransactionManager")
public class TenantServiceImpl implements TenantService {

    private final TenantHelper tenantHelper;

    public TenantServiceImpl(TenantHelper tenantHelper) {
        this.tenantHelper = tenantHelper;
    }

    @Override
    public List<V2Tenant> tenants() {
        return tenantHelper.activeTenants();
    }

}
