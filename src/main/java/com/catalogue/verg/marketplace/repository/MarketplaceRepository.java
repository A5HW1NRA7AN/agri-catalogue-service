package com.catalogue.verg.marketplace.repository;

import com.catalogue.verg.marketplace.entity.MarketplaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketplaceRepository extends JpaRepository<MarketplaceEntity, String> {

}