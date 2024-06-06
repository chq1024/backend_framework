package com.beikei.backend.v2core.enums;

import lombok.Getter;

/**
 * 用于cache前缀
 * @author bk
 */
@Getter
public enum CacheEnum {

    TENANT("Tenant"),

    SECURITY("Security"),

    USER("User"),

    ROLE("Role"),
    ;

    private final String belong;
    CacheEnum(String belong) {
        this.belong = belong;
    }
}
