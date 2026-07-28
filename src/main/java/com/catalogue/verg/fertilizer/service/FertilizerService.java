package com.catalogue.verg.fertilizer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.catalogue.verg.core.dto.CustomResponse;
import com.catalogue.verg.core.dto.LifecycleRequest;
import com.catalogue.verg.core.elasticsearch.dto.SearchCriteria;
import org.springframework.web.multipart.MultipartFile;


public interface FertilizerService {

    CustomResponse createFertilizer(JsonNode fertilizerEntity);

    CustomResponse updateFertilizer(String id, JsonNode fertilizerEntity);

    // Lifecycle: create an incomplete DRAFT (relaxed validation)
    CustomResponse draftFertilizer(JsonNode fertilizerEntity);

    // Lifecycle: (re-)submit a DRAFT/REWORK record for approval -> PENDING (full validation)
    CustomResponse addFertilizer(String id, JsonNode fertilizerEntity);

    // Lifecycle: PENDING -> APPROVED | REJECTED | REWORK
    CustomResponse approveFertilizer(LifecycleRequest request);

    // Lifecycle: APPROVED -> ACTIVE(published) | REJECTED | REWORK | PENDING
    CustomResponse reviewFertilizer(LifecycleRequest request);

    // Toggle a live record between ACTIVE and INACTIVE (rejects any other status)
    CustomResponse toggleStatus(String id);

    CustomResponse searchFertilizer(SearchCriteria searchCriteria);

    CustomResponse assignFertilizer(JsonNode fertilizerEntity, String token);

    CustomResponse read(String id);

    CustomResponse delete(String id);

    CustomResponse importData(MultipartFile file);
}