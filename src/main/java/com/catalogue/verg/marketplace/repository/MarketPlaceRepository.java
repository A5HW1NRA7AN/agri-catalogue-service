package com.catalogue.verg.marketplace.repository;

import com.catalogue.verg.marketplace.entity.MarketPlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketPlaceRepository extends JpaRepository<MarketPlaceEntity, String> {

}