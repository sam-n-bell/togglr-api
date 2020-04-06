package com.heb.togglr.api.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.heb.togglr.api.entities.KeysEntity;
import com.heb.togglr.api.entities.KeysEntityPK;

import java.util.List;

public interface KeysRepository extends CrudRepository<KeysEntity, KeysEntityPK> {

    List<KeysEntity> findByAppId(int appId, Sort sort);

    @Query(nativeQuery = true, value = "SELECT * FROM togglr.key_names " +
            " WHERE app_id = ?1 and key_name = ?2 and deleted = 1")
    KeysEntity findByAppIdAndKeyNameAndDeletedIsTrue(int appId, String keyName);

    @Query(nativeQuery = true, value = "SELECT * FROM togglr.key_names " +
            " WHERE app_id = ?1 and deleted = 1")
    List<KeysEntity> findByAppIdAndDeletedIsTrue(int appId);

}
