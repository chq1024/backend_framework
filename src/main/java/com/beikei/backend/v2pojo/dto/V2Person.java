package com.beikei.backend.v2pojo.dto;

import com.beikei.backend.v2core.core.V2CommentDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author bk
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class V2Person extends V2CommentDTO {

    private Long uid;
    private Long cid;
    private String account;
    private String uname;
    private String unick;
    private String email;
    private String remark;
    private String roles;
}
