package com.heb.togglr.api.validators;

import com.heb.togglr.api.entities.ConfigsEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("beforeCreateConfigsEntityValidator")
public class ConfigsEntityValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ConfigsEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ConfigsEntity configsEntity = (ConfigsEntity) target;

        if (configsEntity.getKeyName() == null || configsEntity.getKeyName().trim().length() == 0) {
            errors.rejectValue("keyName", "keyName.invalid");
        }

        if (checkConfigValue(configsEntity.getConfigValue())) {
            errors.rejectValue("configValue", "configValue.invalid");
        }

        if (idIsInvalid(configsEntity.getAppId())) {
            errors.rejectValue("appId", "appId.invalid");
        }

        if (idIsInvalid(configsEntity.getFeatureId())) {
            errors.rejectValue("featureId", "featureId.invalid");
        }

    }

    /**
     * Make sure that an ID (integer) exists and is valid
     * @param id
     * @return
     */
    private boolean idIsInvalid(Integer id) {
        return (id == null || id < 1);
    }

    /**
     * Checks that a config name is alphanumeric and
     * that it does not contain any special characters besides
     * hyphens
     * @param input
     * @return
     */
    private boolean checkConfigValue(String input) {
        String pattern = "^[-a-zA-Z0-9]+$"; //alphanumeric and hyphens
        return (input == null || input.trim().length() == 0 || !input.matches(pattern));
    }


}
