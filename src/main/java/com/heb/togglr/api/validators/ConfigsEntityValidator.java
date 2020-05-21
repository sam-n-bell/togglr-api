package com.heb.togglr.api.validators;

import com.heb.togglr.api.entities.*;
import com.heb.togglr.api.repositories.ApplicationsRepository;
import com.heb.togglr.api.repositories.FeatureRepository;
import com.heb.togglr.api.repositories.KeysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("beforeCreateConfigsEntityValidator")
public class ConfigsEntityValidator implements Validator {

    @Autowired
    ApplicationsRepository applicationsRepository;

    @Autowired
    FeatureRepository featureRepository;

    @Autowired
    KeysRepository keysRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return ConfigsEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ConfigsEntity configsEntity = (ConfigsEntity) target;


        if (!isAppIdValid(configsEntity.getAppId())) {
            errors.rejectValue("appId", "appId (App ID) is not valid");
        }

        if (configsEntity.getKeyName() == null || configsEntity.getKeyName().trim().length() == 0) {
            errors.rejectValue("keyName", "Missing valid keyName (key name) property");
        } else if (!doesKeyExistForApp(configsEntity.getAppId(), configsEntity.getKeyName())) {
            errors.rejectValue("keyName", "Could not find specified key for given app");
        }

        if (configsEntity.getConfigValue() == null) {
            errors.rejectValue("configValue", "Missing valid configValue (config value) property");
        } else if (badConfigValue(configsEntity.getConfigValue())) {
            errors.rejectValue("configValue", "Letters, numbers, and hyphens only for config value");
        }

        if (!isFeatureIdValid(configsEntity.getFeatureId())) {
            errors.rejectValue("featureId", "featureId (Feature ID) is not valid");
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

        AppEntity appEntity = applicationsRepository.findById(id).orElse(null);
        if (appEntity == null) {
            return false;
        }

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

        FeatureEntity featureEntity = featureRepository.findById(id).orElse(null);
        if (featureEntity == null) {
            return false;
        }

        return true;
    }

    private boolean doesKeyExistForApp(Integer appId, String keyName) {
        KeysEntityPK pk = new KeysEntityPK();
        pk.setAppId(appId);
        pk.setKeyName(keyName);
        KeysEntity keysEntity = this.keysRepository.findById(pk).orElse(null);
        if(keysEntity == null){
            return false;
        }
        return true;
    }

    /**
     * Checks that a config name is alphanumeric and
     * that it does not contain any special characters besides
     * hyphens
     * @param input
     * @return
     */
    private boolean badConfigValue(String input) {
        String pattern = "^[-a-zA-Z0-9]+$"; //alphanumeric and hyphens
        return (input.trim().length() == 0 || !input.matches(pattern));
    }


}
