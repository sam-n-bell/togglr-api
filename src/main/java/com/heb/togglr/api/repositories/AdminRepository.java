package com.heb.togglr.api.repositories;

import com.heb.togglr.api.entities.AdminsEntity;
import com.heb.togglr.api.entities.AdminsEntityPK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<AdminsEntity, AdminsEntityPK> {

    @Query(value = "SELECT * from togglr.admins WHERE app_id = ?1 and id = ?2", nativeQuery = true)
    public AdminsEntity findDeletedAdminByAppIdAndId(int appId, String id);

}
