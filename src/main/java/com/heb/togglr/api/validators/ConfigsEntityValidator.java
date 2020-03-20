package com.heb.togglr.api.validators;

import com.heb.togglr.api.entities.AppEntity;
import com.heb.togglr.api.entities.ConfigsEntity;
import com.heb.togglr.api.entities.FeatureEntity;
import com.heb.togglr.api.repositories.ApplicationsRepository;
import com.heb.togglr.api.repositories.ConfigsRepository;
import com.heb.togglr.api.repositories.FeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component("beforeCreateConfigsEntityValidator")
public class ConfigsEntityValidator implements Validator {

    @Autowired
    ApplicationsRepository applicationsRepository;

    @Autowired
    FeatureRepository featureRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return ConfigsEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ConfigsEntity configsEntity = (ConfigsEntity) target;

        if (configsEntity.getKeyName() == null || configsEntity.getKeyName().trim().length() == 0) {
            errors.rejectValue("keyName", "missing valid keyName property");
        }

        if (configsEntity.getConfigValue() == null) {
            errors.rejectValue("configValue", "missing valid configValue property");
        } else if (checkConfigValue(configsEntity.getConfigValue())) {
            errors.rejectValue("configValue", "configValue is not valid");
        }

        if (isAppIdValid(configsEntity.getAppId())) {
            errors.rejectValue("appId", "appId is not valid");
        }

        if (isFeatureIdValid(configsEntity.getFeatureId())) {
            errors.rejectValue("featureId", "featureId is not valid");
        }

    }

    /**
     * Make sure that an App ID (integer) exists and is valid
     * @param id
     * @return
     */
    private boolean isAppIdValid(Integer id) {

        if (id == null || id < 1) {
            return false;
        }

        Optional<AppEntity> appEntity = applicationsRepository.findById(id);
        if (!appEntity.isPresent()) {return false;}

        return true;
    }

    /**
     * Make sure that a feature ID (integer) exists and is valid
     * @param id
     * @return
     */
    private boolean isFeatureIdValid(Integer id) {
        if (id == null || id < 1) {
            return false;
        }

        Optional<FeatureEntity> featureEntity = featureRepository.findById(id);
        if (!featureEntity.isPresent()) {return false;}

        return true;
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
        return (input.trim().length() == 0 || !input.matches(pattern));
    }


}
