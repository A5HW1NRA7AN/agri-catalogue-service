package com.catalogue.verg.cropvariety.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.catalogue.verg.core.dto.CustomResponse;
import com.catalogue.verg.core.dto.LifecycleRequest;
import com.catalogue.verg.core.elasticsearch.dto.SearchCriteria;
import org.springframework.web.multipart.MultipartFile;


public interface CropvarietyService {

    CustomResponse createCropvariety(JsonNode cropvarietyEntity);

    CustomResponse updateCropvariety(String id, JsonNode cropvarietyEntity);

    // Lifecycle: create an incomplete DRAFT (relaxed validation)
    CustomResponse draftCropvariety(JsonNode cropvarietyEntity);

    // Lifecycle: (re-)submit a DRAFT/REWORK record for approval -> PENDING (full validation)
    CustomResponse addCropvariety(String id, JsonNode cropvarietyEntity);

    // Lifecycle: PENDING -> APPROVED | REJECTED | REWORK
    CustomResponse approveCropvariety(LifecycleRequest request);

    // Lifecycle: APPROVED -> ACTIVE(published) | REJECTED | REWORK | PENDING
    CustomResponse reviewCropvariety(LifecycleRequest request);

    // Toggle a live record between ACTIVE and INACTIVE (rejects any other status)
    CustomResponse toggleStatus(String id);

    CustomResponse searchCropvariety(SearchCriteria searchCriteria);

    CustomResponse assignCropvariety(JsonNode cropvarietyEntity, String token);

    CustomResponse read(String id);

    CustomResponse delete(String id);

    CustomResponse importData(MultipartFile file);

    // Drops the ES index and rebuilds it from the primary store (Postgres); skips DELETED records
    CustomResponse loadFromPrimaryCropvariety();
}