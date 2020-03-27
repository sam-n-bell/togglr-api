package com.heb.togglr.api.repositories;

import com.heb.togglr.api.entities.AppEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ApplicationsRepository extends CrudRepository<AppEntity, Integer> {

    public List<AppEntity> findAllByAdminsById_Id(String id);

    @Query(nativeQuery = true, value = "SELECT ap.* " +
            "FROM togglr.admins ad " +
            "JOIN togglr.app ap ON ap.id = ad.app_id " +
            "WHERE ad.id = ?1 and ap.deleted = 1;")
    public List<AppEntity> findAllDeletedByAdminsById_Id(String id);

    @Query(nativeQuery = true, value = "SELECT * FROM togglr.app WHERE id = ?1")
    public AppEntity findSoftDeletedById(int id);

    public AppEntity findById(@RequestParam int id, Sort sort);

}
