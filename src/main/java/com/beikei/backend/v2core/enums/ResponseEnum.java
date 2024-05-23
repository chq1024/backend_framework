package com.beikei.backend.v2core.enums;

import lombok.Getter;

/**
 * response枚举类
 * @author bk
 */
@Getter
public enum ResponseEnum {

    SUCCESS(200,"success"),
    FAIL(500,"fail"),
    TENANT_NOT_VALID(10001,"租户不合法");
    ;

    private final Integer code;
    private final String message;

    ResponseEnum(Integer code,String message) {
        this.code = code;
        this.message = message;
    }

    public String formatMessage(Object[] args) {
        return String.format(this.getMessage(),args);
    }
}
