package com.beikei.backend.v2core.validator;

import com.beikei.backend.v2core.annotion.UidCheck;
import com.beikei.backend.v2core.annotion.VersionCheck;
import com.beikei.backend.v2core.config.SystemKeyword;
import com.beikei.backend.v2module.user.cover.V2UserDetail;
import org.slf4j.MDC;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 用于校验前端请求体中是否包含version，根据versionCheck中check值判断
 * @author bk
 */
public class UidCheckValidator implements ConstraintValidator<UidCheck,Long> {

    private boolean check = true;

    @Override
    public void initialize(UidCheck constraintAnnotation) {
         check = constraintAnnotation.check();
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (!check) {
            return true;
        }
        return value != null;
    }
}
