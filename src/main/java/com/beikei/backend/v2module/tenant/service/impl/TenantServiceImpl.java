package com.beikei.backend.v2module.tenant.service.impl;

import com.beikei.backend.v2module.tenant.service.TenantService;
import com.beikei.backend.v2pojo.entity.V2Tenant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author bk
 */
@Service
@Transactional
public class TenantServiceImpl implements TenantService {

    public TenantServiceImpl() {

    }

    @Override
    public List<V2Tenant> tenants() {
        return null;
    }
}
