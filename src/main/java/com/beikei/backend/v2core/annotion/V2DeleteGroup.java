package com.beikei.backend.v2core.annotion;

import java.lang.annotation.*;

/**
 * 删除时分组校验注解
 * @author bk
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface V2DeleteGroup {
}
