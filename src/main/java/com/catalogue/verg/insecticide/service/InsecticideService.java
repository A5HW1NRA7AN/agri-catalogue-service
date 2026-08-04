package com.catalogue.verg.insecticide.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.catalogue.verg.core.dto.CustomResponse;
import com.catalogue.verg.core.dto.LifecycleRequest;
import com.catalogue.verg.core.elasticsearch.dto.SearchCriteria;
import org.springframework.web.multipart.MultipartFile;


public interface InsecticideService {

    CustomResponse createInsecticide(JsonNode insecticideEntity);

    CustomResponse updateInsecticide(String id, JsonNode insecticideEntity);

    // Lifecycle: create an incomplete DRAFT (relaxed validation)
    CustomResponse draftInsecticide(JsonNode insecticideEntity);

    // Lifecycle: (re-)submit a DRAFT/REWORK record for approval -> PENDING (full validation)
    CustomResponse addInsecticide(String id, JsonNode insecticideEntity);

    // Lifecycle: PENDING -> APPROVED | REJECTED | REWORK
    CustomResponse approveInsecticide(LifecycleRequest request);

    // Lifecycle: APPROVED -> ACTIVE(published) | REJECTED | REWORK | PENDING
    CustomResponse reviewInsecticide(LifecycleRequest request);

    // Toggle a live record between ACTIVE and INACTIVE (rejects any other status)
    CustomResponse toggleStatus(String id);

    CustomResponse searchInsecticide(SearchCriteria searchCriteria);

    CustomResponse assignInsecticide(JsonNode insecticideEntity, String token);

    CustomResponse read(String id);

    CustomResponse delete(String id);

    CustomResponse importData(MultipartFile file);

    // Drops the ES index and rebuilds it from the primary store (Postgres); skips DELETED records
    CustomResponse loadFromPrimaryInsecticide();
}