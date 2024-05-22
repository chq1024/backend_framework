package com.beikei.backend.v2pojo.entity;

import com.h7culture.manage.v2core.core.V2RecordCommentEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 角色表
 * @author bk
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class V2Character extends V2RecordCommentEntity {

    private Long id;
    private String cname;
    private String remark;
}
