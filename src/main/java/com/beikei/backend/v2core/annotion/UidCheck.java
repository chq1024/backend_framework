package com.beikei.backend.v2core.annotion;

import com.h7culture.manage.v2core.validator.VersionCheckValidator;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * uid合法性检测
 * 必填，且需要与token进行比较是否是同一个用户
 * @author bk
 */
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.ANNOTATION_TYPE,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = VersionCheckValidator.class)
@Documented
public @interface UidCheck {

    boolean check() default true;
}
