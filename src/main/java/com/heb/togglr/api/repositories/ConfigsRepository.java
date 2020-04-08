package com.heb.togglr.api.repositories;

import com.heb.togglr.api.entities.KeysEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.heb.togglr.api.entities.ConfigsEntity;
import com.heb.togglr.api.entities.ConfigsEntityPK;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ConfigsRepository extends CrudRepository<ConfigsEntity, ConfigsEntityPK> {

    List<ConfigsEntity> findByAppIdAndFeatureId(@RequestParam("appId") int appId, @RequestParam("featureId") int featureId);

    @Query(nativeQuery = true, value = "SELECT * FROM togglr.configs " +
                                        "WHERE app_id = ?1 AND feature_id = ?2 " +
                                        "AND deleted = 1")
    List<ConfigsEntity> findByAppIdAndFeatureIdAndDeletedIsTrue(int appId, int featureId);

    @Query(nativeQuery = true, value = "SELECT * FROM togglr.configs " +
            "WHERE app_id = ?1 AND feature_id = ?2 and key_name = ?3 " +
            "AND deleted = 1")
    List<ConfigsEntity> findByAppIdAndFeatureIdAndKeyNameAndDeletedIsTrue(int appId, int featureId, String keyName);

    @Query(nativeQuery = true, value = "SELECT * FROM togglr.configs " +
            "WHERE app_id = ?1 AND key_name = ?2 and feature_id = ?3 " +
            "AND config_value = ?4 and deleted = 1")
    ConfigsEntity findByAppIdAndKeyNameAndFeatureIdAndConfigValueAndDeletedIsTrue(int appId, String keyName, int featureId, String configValue);

}
