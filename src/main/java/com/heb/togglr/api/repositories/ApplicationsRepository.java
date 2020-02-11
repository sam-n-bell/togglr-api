package com.heb.togglr.api.repositories;

import com.heb.togglr.api.entities.AppEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ApplicationsRepository extends CrudRepository<AppEntity, Integer> {

    public List<AppEntity> findAllByAdminsById_Id(String id);


    public AppEntity findById(@RequestParam int id, Sort sort);

    // Got Sort parameter idea from: https://stackoverflow.com/questions/53330135/spring-data-rest-findby-not-sorting
    // Postman url: {{server}}/togglr-api/appEntities/search/findById?id=1&projection=includeSubObjects&sort=featuresById.descr,asc
    // Tried a couple of different sort entity name formats: featuresById_descr, FeatureEntity.descr, FeatureEntity_descr, etc


}
