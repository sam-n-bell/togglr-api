package com.heb.togglr.api.controllers;

import java.util.Collection;
import java.util.List;

import com.heb.togglr.api.entities.AppEntity;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resources;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.heb.togglr.api.entities.ConfigsEntity;
import com.heb.togglr.api.entities.FeatureEntity;
import com.heb.togglr.api.repositories.ConfigsRepository;
import com.heb.togglr.api.repositories.FeatureRepository;
import javassist.tools.web.BadHttpRequest;

@RepositoryRestController
public class FeatureEntitiesController {

    private FeatureRepository featureRepository;
    private ConfigsRepository configsRepository;

    public FeatureEntitiesController(FeatureRepository featureRepository, ConfigsRepository configsRepository){
        this.featureRepository = featureRepository;
        this.configsRepository = configsRepository;
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/featureEntities/{featureId}")
    @ResponseBody
    public void removeFeature(@PathVariable Integer featureId) throws BadHttpRequest {


        FeatureEntity featureEntity = this.featureRepository.findById(featureId).orElse(null);

        if(featureEntity == null){
            throw new BadHttpRequest(new Exception("Could not fine admin with id " + featureId));
        }

        Collection<ConfigsEntity> configs = featureEntity.getConfigsById();

        this.configsRepository.deleteAll(configs);

        featureEntity.getConfigsById().clear();
        this.featureRepository.delete(featureEntity);
    }

    @RequestMapping(method = RequestMethod.GET, value="/featureEntities/{featureId}/deleted")
    @ResponseBody
    public Resources getDeletedFeaturesForApplication(int featureId) {
        // get apps only that user should be able to see
        String userId = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getUsername();
        List<FeatureEntity> features = this.featureRepository.findByAppIdAndDeletedIsTrue(featureId);
        Resources<AppEntity> resources = new Resources(features);
        return resources;
    }

//    @RequestMapping(method = RequestMethod.PATCH, value="/appEntities/{appId}/recover")
//    @ResponseBody
//    public void recoverDeletedApplication(@PathVariable int appId) {
//        String userId = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getDetails()).getUsername();
//        AppEntity appEntity = applicationsRepository.findSoftDeletedById(appId);
//        if (appEntity != null) {
//            appEntity.setDeleted(false);
//            applicationsRepository.save(appEntity);
//        }
//    }
}
