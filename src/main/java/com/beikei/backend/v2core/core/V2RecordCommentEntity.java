package com.beikei.backend.v2core.core;

import lombok.Getter;
import lombok.Setter;

/**
 * 可记录实体创建和最近一次修改情况
 * @author bk
 */
@Getter
@Setter
public class V2RecordCommentEntity extends V2CommentEntity {
    public Long createUid;
    public Long updateUid;
}
