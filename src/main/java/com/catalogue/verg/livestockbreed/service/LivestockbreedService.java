package com.catalogue.verg.livestockbreed.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.catalogue.verg.core.dto.CustomResponse;
import com.catalogue.verg.core.dto.LifecycleRequest;
import com.catalogue.verg.core.elasticsearch.dto.SearchCriteria;
import org.springframework.web.multipart.MultipartFile;


public interface LivestockbreedService {

    CustomResponse createLivestockbreed(JsonNode livestockbreedEntity);

    CustomResponse updateLivestockbreed(String id, JsonNode livestockbreedEntity);

    // Lifecycle: create an incomplete DRAFT (relaxed validation)
    CustomResponse draftLivestockbreed(JsonNode livestockbreedEntity);

    // Lifecycle: (re-)submit a DRAFT/REWORK record for approval -> PENDING (full validation)
    CustomResponse addLivestockbreed(String id, JsonNode livestockbreedEntity);

    // Lifecycle: PENDING -> APPROVED | REJECTED | REWORK
    CustomResponse approveLivestockbreed(LifecycleRequest request);

    // Lifecycle: APPROVED -> ACTIVE(published) | REJECTED | REWORK | PENDING
    CustomResponse reviewLivestockbreed(LifecycleRequest request);

    // Toggle a live record between ACTIVE and INACTIVE (rejects any other status)
    CustomResponse toggleStatus(String id);

    CustomResponse searchLivestockbreed(SearchCriteria searchCriteria);

    CustomResponse assignLivestockbreed(JsonNode livestockbreedEntity, String token);

    CustomResponse read(String id);

    CustomResponse delete(String id);

    CustomResponse importData(MultipartFile file);

    // Drops the ES index and rebuilds it from the primary store (Postgres); skips DELETED records
    CustomResponse loadFromPrimaryLivestockbreed();
}