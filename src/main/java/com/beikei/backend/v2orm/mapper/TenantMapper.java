package com.beikei.backend.v2orm.mapper;

import com.beikei.backend.v2core.core.V2CommentMapper;
import com.beikei.backend.v2orm.entity.V2Tenant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TenantMapper extends V2CommentMapper {

    List<V2Tenant> load();

    V2Tenant one(@Param("tenantId") String tenantId);
}
