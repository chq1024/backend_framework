package com.beikei.backend.v2core.core;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 基础类，想拔插version，但java是单继承，在部分情况下可能会成为冗余字段
 * @author bk
 */
@Getter
@Setter
public class V2CommentEntity implements Serializable {
    public Long createTime;
    public Long updateTime;
    public Long version;
}