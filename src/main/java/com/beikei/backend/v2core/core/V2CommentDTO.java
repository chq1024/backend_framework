package com.beikei.backend.v2core.core;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author bk
 */
@Getter
@Setter
public class V2CommentDTO implements Serializable {

    public Long version;
    public Long createTime;
    public Long updateTime;
}
