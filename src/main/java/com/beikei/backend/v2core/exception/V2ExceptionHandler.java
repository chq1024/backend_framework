package com.beikei.backend.v2core.exception;

import com.beikei.backend.v2core.core.V2CommentResponse;
import com.beikei.backend.v2core.enums.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * 异常处理类
 * @author bk
 */
@RestController
@ControllerAdvice
@Slf4j
public class V2ExceptionHandler {

    @ExceptionHandler(V2GameException.class)
    public <T> V2CommentResponse<T> handlerV2GameException(V2GameException exception) {
        log.error("服务端运行时异常,{}", exception.getMessage());
        return V2CommentResponse.fail(exception.getCode(),exception.getMessage());
    }

    /**
     * 保底异常处理
     * @param exception 出现的异常
     */
    @ExceptionHandler(Exception.class)
    public <T> V2CommentResponse<T> handlerException(Exception exception) {
        log.error("服务端未分类异常,{}", exception.getMessage());
        return V2CommentResponse.fail(ResponseEnum.FAIL.getCode(), exception.getMessage());
    }

}
