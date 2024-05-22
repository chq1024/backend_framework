package com.beikei.backend.v2core.annotion;


import com.beikei.backend.v2core.validator.VersionCheckValidator;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * version机端检测
 * @author bk
 */
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.ANNOTATION_TYPE,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = VersionCheckValidator.class)
@Documented
public @interface VersionCheck {

    boolean check() default true;
}
