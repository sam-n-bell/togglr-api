package com.heb.togglr.api.repositories;

import com.heb.togglr.api.entities.AppEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ApplicationsRepository extends CrudRepository<AppEntity, Integer> {

    public List<AppEntity> findAllByAdminsById_Id(String id);

    public AppEntity findById(@RequestParam int id, Sort sort);

}
