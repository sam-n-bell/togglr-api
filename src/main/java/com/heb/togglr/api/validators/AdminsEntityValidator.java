package com.heb.togglr.api.validators;

import com.heb.togglr.api.entities.AdminsEntity;
import com.heb.togglr.api.entities.AppEntity;
import com.heb.togglr.api.entities.KeysEntity;
import com.heb.togglr.api.repositories.AdminRepository;
import com.heb.togglr.api.repositories.ApplicationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

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

        if (checkAdminId(adminsEntity.getId())) {
            errors.rejectValue("id", "missing a valid id");
        }

        if (isAppIdValid(adminsEntity.getAppId())) {
            errors.rejectValue("appId", "missing a valid appId");
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

    /**
     * Checks that an app Id is valid
     * @param id
     * @return
     */
    private boolean isAppIdValid(Integer id) {
        if (id == null || id < 1) {
            return false;
        }

        Optional<AppEntity> appEntity = applicationsRepository.findById(id);
        if (!appEntity.isPresent()) {
            return false;
        }

        return true;
    }


}
