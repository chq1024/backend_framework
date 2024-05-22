package com.beikei.backend.v2core.validator;

import com.h7culture.manage.v2core.annotion.VersionCheck;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 用于校验前端请求体中是否包含version，根据versionCheck中check值判断
 * @author bk
 */
public class VersionCheckValidator implements ConstraintValidator<VersionCheck,Long> {

    private boolean check = true;

    @Override
    public void initialize(VersionCheck constraintAnnotation) {
         check = constraintAnnotation.check();
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return !check || value != null;
    }
}
