package com.catalogue.verg.locationmapper.repository;

import com.catalogue.verg.locationmapper.entity.LocationMapperEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationMapperRepository extends JpaRepository<LocationMapperEntity, String> {

}