package com.beikei.backend.v2core.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.h7culture.manage.v2core.enums.ResponseEnum;
import com.h7culture.manage.v2util.SpringUtil;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.MDC;

import java.io.Serializable;

/**
 * @author bk
 */
@Getter
@Setter
public class V2CommentResponse<T> implements Serializable {

    private Integer code;
    private String message;
    private T data;

    // 用于前期检查，非生产环境环境中展示
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = CostTimeJsonSerializableFilter.class)
    private Long cost_time;

    public V2CommentResponse() {
    }

    public V2CommentResponse(Integer code, String message, T data, Long cost_time) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.cost_time = cost_time;
    }

    public static <T> V2CommentResponse<T> success(T data) {
        return success(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMessage(), data);
    }

    private static <T> V2CommentResponse<T> success(Integer code, String message, T data) {
        Long costTime = Long.valueOf(MDC.get("cost_time"));
        return new V2CommentResponse<>(code, message, data, costTime);
    }

    public static <T> V2CommentResponse<T> fail() {
        return fail(ResponseEnum.FAIL.getCode(), ResponseEnum.FAIL.getMessage());
    }

    public static <T> V2CommentResponse<T> fail(Integer code, String message) {
        Long costTime = Long.valueOf(MDC.get("cost_time"));
        return new V2CommentResponse<>(code, message, null, costTime);
    }

    static class CostTimeJsonSerializableFilter {

        @Override
        public boolean equals(Object obj) {
            return "pro".equals(SpringUtil.getEnv()) || "product".equals(SpringUtil.getEnv());
        }
    }
}
