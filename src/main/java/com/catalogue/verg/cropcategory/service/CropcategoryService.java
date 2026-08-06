package com.catalogue.verg.cropcategory.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.catalogue.verg.core.dto.CustomResponse;
import com.catalogue.verg.core.dto.LifecycleRequest;
import com.catalogue.verg.core.elasticsearch.dto.SearchCriteria;
import org.springframework.web.multipart.MultipartFile;


public interface CropcategoryService {

    CustomResponse createCropcategory(JsonNode cropcategoryEntity);

    CustomResponse updateCropcategory(String id, JsonNode cropcategoryEntity);

    // Lifecycle: create an incomplete DRAFT (relaxed validation)
    CustomResponse draftCropcategory(JsonNode cropcategoryEntity);

    // Lifecycle: (re-)submit a DRAFT/REWORK record for approval -> PENDING (full validation)
    CustomResponse addCropcategory(String id, JsonNode cropcategoryEntity);

    // Lifecycle: PENDING -> APPROVED | REJECTED | REWORK
    CustomResponse approveCropcategory(LifecycleRequest request);

    // Lifecycle: APPROVED -> ACTIVE(published) | REJECTED | REWORK | PENDING
    CustomResponse reviewCropcategory(LifecycleRequest request);

    // Toggle a live record between ACTIVE and INACTIVE (rejects any other status)
    CustomResponse toggleStatus(String id);

    CustomResponse searchCropcategory(SearchCriteria searchCriteria);

    CustomResponse assignCropcategory(JsonNode cropcategoryEntity, String token);

    CustomResponse read(String id);

    CustomResponse delete(String id);

    CustomResponse importData(MultipartFile file);

    // Drops the ES index and rebuilds it from the primary store (Postgres); skips DELETED records
    CustomResponse loadFromPrimaryCropcategory();
}