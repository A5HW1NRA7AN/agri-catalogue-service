package com.catalogue.verg.croptype.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.catalogue.verg.core.dto.CustomResponse;
import com.catalogue.verg.core.dto.LifecycleRequest;
import com.catalogue.verg.core.elasticsearch.dto.SearchCriteria;
import org.springframework.web.multipart.MultipartFile;


public interface CroptypeService {

    CustomResponse createCroptype(JsonNode croptypeEntity);

    CustomResponse updateCroptype(String id, JsonNode croptypeEntity);

    // Lifecycle: create an incomplete DRAFT (relaxed validation)
    CustomResponse draftCroptype(JsonNode croptypeEntity);

    // Lifecycle: (re-)submit a DRAFT/REWORK record for approval -> PENDING (full validation)
    CustomResponse addCroptype(String id, JsonNode croptypeEntity);

    // Lifecycle: PENDING -> APPROVED | REJECTED | REWORK
    CustomResponse approveCroptype(LifecycleRequest request);

    // Lifecycle: APPROVED -> ACTIVE(published) | REJECTED | REWORK | PENDING
    CustomResponse reviewCroptype(LifecycleRequest request);

    // Toggle a live record between ACTIVE and INACTIVE (rejects any other status)
    CustomResponse toggleStatus(String id);

    CustomResponse searchCroptype(SearchCriteria searchCriteria);

    CustomResponse assignCroptype(JsonNode croptypeEntity, String token);

    CustomResponse read(String id);

    CustomResponse delete(String id);

    CustomResponse importData(MultipartFile file);

    // Drops the ES index and rebuilds it from the primary store (Postgres); skips DELETED records
    CustomResponse loadFromPrimaryCroptype();
}