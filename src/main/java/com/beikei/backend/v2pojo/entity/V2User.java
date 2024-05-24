package com.beikei.backend.v2pojo.entity;

import com.beikei.backend.v2core.core.V2RecordCommentEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

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
    private String account;
    private String password;
    private String slat;
    private String uname;
    private String unick;
    private String email;
    private String phone;
    private Boolean opened;
    private String remark;
}
