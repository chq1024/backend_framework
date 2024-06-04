package com.beikei.backend.v2orm.entity;

import com.beikei.backend.v2core.core.V2CommentEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * refreshToken,用于无感accessToken刷新
 * 其中keyword用来保存refreshToken中的claims中的关键字
 * @author bk
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class V2RefreshToken extends V2CommentEntity {

    private Long id;
    private String keyword;
    private Integer used;
    private Long refreshTime;

}
