package com.catalogue.verg.livestockcategory.repository;

import com.catalogue.verg.livestockcategory.entity.LivestockcategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivestockcategoryRepository extends JpaRepository<LivestockcategoryEntity, String> {

}