package com.anchuk.citylist.repository;

import com.anchuk.citylist.model.entity.CityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CityRepository extends JpaRepository<CityEntity, Long> {

    Page<CityEntity> findByNameContainsIgnoreCase(String name, Pageable pageable);

}
