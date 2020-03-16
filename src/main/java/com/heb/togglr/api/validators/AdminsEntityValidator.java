package com.heb.togglr.api.validators;

import com.heb.togglr.api.entities.AdminsEntity;
import com.heb.togglr.api.entities.KeysEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("beforeCreateAdminsEntityValidator")
public class AdminsEntityValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return AdminsEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AdminsEntity adminsEntity = (AdminsEntity) target;

        if (checkAdminId(adminsEntity.getId())) {
            errors.rejectValue("keyname", "keyname.invalid");
        }

        if (adminsEntity.getAppId() == null) {
            errors.rejectValue("appId", "appdId.required");
        }


    }

    /**
     * Checks that an admin name is alphanumeric and
     * that it does not contain any special characters besides
     * hyphens
     * @param input
     * @return
     */
    private boolean checkAdminId(String input) {
        String pattern = "^[-a-zA-Z0-9]+$"; //alphanumeric and hyphens
        return (input.trim().length() == 0 || !input.matches(pattern));
    }


}
