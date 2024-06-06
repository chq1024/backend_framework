package com.beikei.backend.v2orm.dto;

import com.beikei.backend.v2core.core.V2CommentDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

/**
 * @author bk
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class V2Person extends V2CommentDTO {

    private Long uid;
    private String username;
    private String uname;
    private String unick;
    private String email;
    private String remark;
    // 用于授权
    private List<String> roles;
}
