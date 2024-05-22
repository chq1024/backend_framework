package com.beikei.backend.v2core.exception;

import com.h7culture.manage.v2core.enums.ResponseEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.Arrays;

/**
 * 异常处理
 * @author bk
 */
@Getter
@Setter
public class V2GameException extends RuntimeException {

    private Integer code;
    private String message;

    public V2GameException(ResponseEnum response) {
        this(response, (Object) null);
    }

    public V2GameException(ResponseEnum response, @Nullable Object...args) {
        super("RunTimeError#code#"+ response.getCode() + "#message#" + response.getMessage() + "#args#" + Arrays.toString(args));
        this.code = response.getCode();
        this.message= String.format(response.getMessage(), new Object[]{args});
    }
}
