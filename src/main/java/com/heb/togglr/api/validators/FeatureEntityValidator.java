package com.heb.togglr.api.validators;

import com.heb.togglr.api.entities.FeatureEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("beforeCreateFeatureEntityValidator")
public class FeatureEntityValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
    return FeatureEntity.class.equals(clazz);
}

    @Override
    public void validate(Object target, Errors errors) {
        FeatureEntity featureEntity = (FeatureEntity) target;

        /**
         * Check that descr is present/valid
         */
        if (featureEntity.getDescr() == null || featureEntity.getDescr().trim().length() == 0) {
            errors.rejectValue("descr", "Missing valid descr (description) property");
        }

        /**
         * Check that appId is present/valid
         */
        if (featureEntity.getAppId() == null || featureEntity.getAppId() < 1) {
            errors.rejectValue("appId", "Missing valid appId (App ID) property");
        }

        /**
         * Check that negation is present
         */
        if (featureEntity.getNegation() == null) {
            errors.rejectValue("negation", "Missing negation property");
        }

        /**
         * Check that active is present
         */
        if (featureEntity.getActive() == null) {
            errors.rejectValue("active", "Missing active property");
        }
    }
}
