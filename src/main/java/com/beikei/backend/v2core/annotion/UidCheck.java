package com.beikei.backend.v2core.annotion;


import com.beikei.backend.v2core.validator.UidCheckValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * uid合法性检测
 * 必填，且需要与token进行比较是否是同一个用户
 * @author bk
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UidCheckValidator.class)
@Documented
@Repeatable(UidCheck.List.class)
public @interface UidCheck {

    boolean check() default true;

    String message() default "用户ID异常!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.FIELD})
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        UidCheck[] value();
    }
}
