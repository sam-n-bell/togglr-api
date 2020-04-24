package com.heb.togglr.api.validators;

import com.heb.togglr.api.entities.AdminsEntity;
import com.heb.togglr.api.entities.AppEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("beforeCreateAppEntityValidator")
public class AppEntityValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return AppEntity.class.equals(clazz);
    }

    /**
     * Makes sure that the name and descr entity variables have non-null and non-space characters
     * @param target
     * @param errors
     */
    @Override
    public void validate(Object target, Errors errors) {
        AppEntity appEntity = (AppEntity) target;

        if (appEntity.getName() == null || appEntity.getName().trim().length() == 0) {
            errors.rejectValue("name", "missing a valid name property");
        }

        if (appEntity.getDescr() == null || appEntity.getDescr().trim().length() == 0) {
            errors.rejectValue("descr", "missing a valid descr property");
        }

    }

}
