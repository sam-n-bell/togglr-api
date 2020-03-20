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
            errors.rejectValue("keyname", "missing or contains invalid keyName property");
        }

        if (keysEntity.getAppId() == null) {
            errors.rejectValue("appId", "missing appId property");
        }


    }

    /**
     * Checks that a key name is alphanumeric and
     * that it does not contain any special characters besides
     * hyphens
     * @param input
     * @return
     */
    private boolean checkKeyName(String input) {
        String pattern = "^[-a-zA-Z0-9]+$"; //alphanumeric and hyphens
        return (input.trim().length() == 0 || !input.matches(pattern));
    }


}
