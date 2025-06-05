package com.xgaslan.data.utils.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordValid, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Custom logic (ör: uzunluk ve özel karakter)
        return value != null && value.length() >= 8 && value.matches(".*\\d.*");
    }
}