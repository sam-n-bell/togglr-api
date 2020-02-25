package com.heb.togglr.api.repositories;

import com.heb.togglr.api.entities.KeysEntity;
import org.springframework.data.repository.CrudRepository;

import com.heb.togglr.api.entities.ConfigsEntity;
import com.heb.togglr.api.entities.ConfigsEntityPK;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ConfigsRepository extends CrudRepository<ConfigsEntity, ConfigsEntityPK> {

    List<ConfigsEntity> findByAppIdAndFeatureId(@RequestParam("appId") int appId, @RequestParam("featureId") int featureId);

}
