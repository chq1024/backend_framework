package com.beikei.backend.v2orm.entity;

import com.beikei.backend.v2core.core.V2RecordCommentEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 用户实体
 * @author bk
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class V2User extends V2RecordCommentEntity {

    private Long id;
    private String tenantId;
    private String username;
    private String password;
    private String slat;
    private String uname;
    private String unick;
    private String email;
    private String phone;
    private Boolean opened;
    private String remark;

    @JsonIgnore
    private Boolean valid;
}
