package com.heb.togglr.api.validators;

import com.heb.togglr.api.entities.KeysEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("beforeCreateKeysEntityValidator")
public class KeysEntityValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return KeysEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        KeysEntity keysEntity = (KeysEntity) target;

        if (keysEntity.getKeyName() == null || keysEntity.getKeyName().trim().length() == 0) {
            errors.rejectValue("keyName", "Missing keyName (key name) property");
        }

        if (keysEntity.getAppId() == null || keysEntity.getAppId() < 1) {
            errors.rejectValue("appId", "Missing valid appId (App ID) property");
        }
    }


}
