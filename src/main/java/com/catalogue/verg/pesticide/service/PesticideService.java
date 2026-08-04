package com.catalogue.verg.pesticide.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.catalogue.verg.core.dto.CustomResponse;
import com.catalogue.verg.core.dto.LifecycleRequest;
import com.catalogue.verg.core.elasticsearch.dto.SearchCriteria;
import org.springframework.web.multipart.MultipartFile;


public interface PesticideService {

    CustomResponse createPesticide(JsonNode pesticideEntity);

    CustomResponse updatePesticide(String id, JsonNode pesticideEntity);

    // Lifecycle: create an incomplete DRAFT (relaxed validation)
    CustomResponse draftPesticide(JsonNode pesticideEntity);

    // Lifecycle: (re-)submit a DRAFT/REWORK record for approval -> PENDING (full validation)
    CustomResponse addPesticide(String id, JsonNode pesticideEntity);

    // Lifecycle: PENDING -> APPROVED | REJECTED | REWORK
    CustomResponse approvePesticide(LifecycleRequest request);

    // Lifecycle: APPROVED -> ACTIVE(published) | REJECTED | REWORK | PENDING
    CustomResponse reviewPesticide(LifecycleRequest request);

    // Toggle a live record between ACTIVE and INACTIVE (rejects any other status)
    CustomResponse toggleStatus(String id);

    CustomResponse searchPesticide(SearchCriteria searchCriteria);

    CustomResponse assignPesticide(JsonNode pesticideEntity, String token);

    CustomResponse read(String id);

    CustomResponse delete(String id);

    CustomResponse importData(MultipartFile file);

    // Drops the ES index and rebuilds it from the primary store (Postgres); skips DELETED records
    CustomResponse loadFromPrimaryPesticide();
}