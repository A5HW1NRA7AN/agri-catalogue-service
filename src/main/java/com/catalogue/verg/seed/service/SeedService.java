package com.catalogue.verg.seed.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.catalogue.verg.core.dto.CustomResponse;
import com.catalogue.verg.core.dto.LifecycleRequest;
import com.catalogue.verg.core.elasticsearch.dto.SearchCriteria;
import org.springframework.web.multipart.MultipartFile;


public interface SeedService {

    CustomResponse createSeed(JsonNode seedEntity);

    CustomResponse updateSeed(String id, JsonNode seedEntity);

    // Lifecycle: create an incomplete DRAFT (relaxed validation)
    CustomResponse draftSeed(JsonNode seedEntity);

    // Lifecycle: (re-)submit a DRAFT/REWORK record for approval -> PENDING (full validation)
    CustomResponse addSeed(String id, JsonNode seedEntity);

    // Lifecycle: PENDING -> APPROVED | REJECTED | REWORK
    CustomResponse approveSeed(LifecycleRequest request);

    // Lifecycle: APPROVED -> ACTIVE(published) | REJECTED | REWORK | PENDING
    CustomResponse reviewSeed(LifecycleRequest request);

    // Toggle a live record between ACTIVE and INACTIVE (rejects any other status)
    CustomResponse toggleStatus(String id);

    CustomResponse searchSeed(SearchCriteria searchCriteria);

    CustomResponse assignSeed(JsonNode seedEntity, String token);

    CustomResponse read(String id);

    CustomResponse delete(String id);

    CustomResponse importData(MultipartFile file);

    // Drops the ES index and rebuilds it from the primary store (Postgres); skips DELETED records
    CustomResponse loadFromPrimarySeed();
}