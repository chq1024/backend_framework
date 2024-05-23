package com.beikei.backend.v2module.tenant.service;

import com.beikei.backend.v2pojo.entity.V2Tenant;

import java.util.List;

/**
 * 租户服务
 * @author bk
 */
public interface TenantService {

    List<V2Tenant> tenants();
}
