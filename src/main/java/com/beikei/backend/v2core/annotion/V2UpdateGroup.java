package com.beikei.backend.v2core.annotion;

import java.lang.annotation.*;

/**
 * 更新时分组校验注解
 * @author bk
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface V2UpdateGroup {
}
