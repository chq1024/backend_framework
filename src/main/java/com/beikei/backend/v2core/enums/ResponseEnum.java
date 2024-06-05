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
    UNKNOWN_ERROR(99999,"服务器繁忙，请稍后重试"),
    DB_NOT_FOUND_DATA(99998,"查询失败"),

    TENANT_NOT_VALID(10001,"租户不合法"),
    TENANT_PARAM_ERROR(10002,"缺少关键参数"),
    TENANT_NOT_EXITS(10003,"租户不合法"),

    USER_NOT_MATCH(20001,"账号名或密码错误"),


    AUTHENTICATION_PARAM_ERROR(30001,"认证参数异常"),
    AUTHENTICATION_TOKEN_ERROR(30002,"认证异常"),
    AUTHENTICATION_REFRESH_TOKEN_ERROR(30003,"认证异常"),


    AUTHORIZATION_CHECK_ERROR(40001,"授权验证异常"),
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
