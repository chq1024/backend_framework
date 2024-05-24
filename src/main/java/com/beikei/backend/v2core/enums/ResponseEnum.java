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

    TENANT_NOT_VALID(10001,"租户不合法"),
    TENANT_PARAM_ERROR(10002,"缺少关键参数"),
    TENANT_NOT_EXITS(10003,"租户不合法"),

    USER_NOT_MATCH(20001,"账号名或密码错误")
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
