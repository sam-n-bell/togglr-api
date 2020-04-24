package com.heb.togglr.api.validators;

import com.heb.togglr.api.entities.ConfigsEntity;
import com.heb.togglr.api.entities.KeysEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.security.Key;

@Component("beforeCreateKeysEntityValidator")
public class KeysEntityValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return KeysEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        KeysEntity keysEntity = (KeysEntity) target;

        if (checkKeyName(keysEntity.getKeyName())) {
            errors.rejectValue("keyName", "missing keyName property");
        }

        if (keysEntity.getAppId() == null) {
            errors.rejectValue("appId", "missing appId property");
        }
    }

    private boolean checkKeyName(String input) {
        String pattern = "^[-a-zA-Z0-9]+$"; //alphanumeric and hyphens regex if ever needed
        return (input == null || input.trim().length() == 0);
    }


}
