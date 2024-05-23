package com.beikei.backend.v2pojo.entity;

import com.beikei.backend.v2core.core.V2RecordCommentEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 租户实体
 * @author bk
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class V2Tenant extends V2RecordCommentEntity {

    private Long id;
    private String tenantId;
    private Boolean status;
    // 如果续费模式可以使用
    private Long expireTime;
    private String remark;
}
