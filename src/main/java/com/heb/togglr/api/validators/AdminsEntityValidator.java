package com.heb.togglr.api.validators;

import com.heb.togglr.api.entities.AdminsEntity;
import com.heb.togglr.api.entities.AppEntity;
import com.heb.togglr.api.repositories.ApplicationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("beforeCreateAdminsEntityValidator")
public class AdminsEntityValidator implements Validator {

    @Autowired
    ApplicationsRepository applicationsRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return AdminsEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AdminsEntity adminsEntity = (AdminsEntity) target;

        if (adminsEntity.getId() == null) {
            errors.rejectValue("id", "Missing a valid id (Admin ID)");
        }

        if (!isAppIdValid(adminsEntity.getAppId())) {
            errors.rejectValue("appId", "Missing a valid appId (App ID)");
        } else if (adminsEntity.getAppId() < 1) {
            errors.rejectValue("appId", "Not a valid appId (App ID)");
        }
    }


    private boolean isAppIdValid(Integer id) {

        AppEntity appEntity = this.applicationsRepository.findById(id).orElse(null);
        if (appEntity == null) {
            return false;
        }

        return true;
    }


}
