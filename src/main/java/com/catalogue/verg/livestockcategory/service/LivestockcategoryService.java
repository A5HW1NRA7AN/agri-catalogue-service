package com.catalogue.verg.livestockcategory.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.catalogue.verg.core.dto.CustomResponse;
import com.catalogue.verg.core.dto.LifecycleRequest;
import com.catalogue.verg.core.elasticsearch.dto.SearchCriteria;
import org.springframework.web.multipart.MultipartFile;


public interface LivestockcategoryService {

    CustomResponse createLivestockcategory(JsonNode livestockcategoryEntity);

    CustomResponse updateLivestockcategory(String id, JsonNode livestockcategoryEntity);

    // Lifecycle: create an incomplete DRAFT (relaxed validation)
    CustomResponse draftLivestockcategory(JsonNode livestockcategoryEntity);

    // Lifecycle: (re-)submit a DRAFT/REWORK record for approval -> PENDING (full validation)
    CustomResponse addLivestockcategory(String id, JsonNode livestockcategoryEntity);

    // Lifecycle: PENDING -> APPROVED | REJECTED | REWORK
    CustomResponse approveLivestockcategory(LifecycleRequest request);

    // Lifecycle: APPROVED -> ACTIVE(published) | REJECTED | REWORK | PENDING
    CustomResponse reviewLivestockcategory(LifecycleRequest request);

    // Toggle a live record between ACTIVE and INACTIVE (rejects any other status)
    CustomResponse toggleStatus(String id);

    CustomResponse searchLivestockcategory(SearchCriteria searchCriteria);

    CustomResponse assignLivestockcategory(JsonNode livestockcategoryEntity, String token);

    CustomResponse read(String id);

    CustomResponse delete(String id);

    CustomResponse importData(MultipartFile file);

    // Drops the ES index and rebuilds it from the primary store (Postgres); skips DELETED records
    CustomResponse loadFromPrimaryLivestockcategory();
}