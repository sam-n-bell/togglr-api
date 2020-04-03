package com.heb.togglr.api.repositories;

import com.heb.togglr.api.entities.AppEntity;
import com.heb.togglr.api.entities.FeatureEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FeatureRepository extends CrudRepository<FeatureEntity, Integer> {

    List<FeatureEntity> findByAppIdAndActiveIsTrue(int appId);

    @Query(value = "SELECT * FROM togglr.feature " +
            "WHERE app_id = ?1 " +
            "and deleted = 1", nativeQuery = true)
    public List<FeatureEntity> findByAppIdAndDeletedIsTrue(int appId);

    @Query(nativeQuery = true, value = "SELECT * FROM togglr.feature WHERE id = ?1 and deleted = 1")
    public FeatureEntity findByIdAndDeletedIsTrue(int id);

    List<FeatureEntity> findByAppId(int appId, Sort sort);
}
