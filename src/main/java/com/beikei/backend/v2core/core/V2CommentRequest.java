package com.beikei.backend.v2core.core;

import com.beikei.backend.v2core.annotion.VersionCheck;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 请求必须包含字段
 * @author bk
 */
@Getter
@Setter
public class V2CommentRequest implements Serializable {
    @VersionCheck
    public Long version;
}
