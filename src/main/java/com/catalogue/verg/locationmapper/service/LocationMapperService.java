package com.catalogue.verg.locationmapper.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.catalogue.verg.core.dto.CustomResponse;
import com.catalogue.verg.core.dto.LifecycleRequest;
import com.catalogue.verg.core.elasticsearch.dto.SearchCriteria;
import org.springframework.web.multipart.MultipartFile;


public interface LocationMapperService {

    CustomResponse createLocationMapper(JsonNode locationMapperEntity);

    CustomResponse updateLocationMapper(String id, JsonNode locationMapperEntity);

    // Lifecycle: create an incomplete DRAFT (relaxed validation)
    CustomResponse draftLocationMapper(JsonNode locationMapperEntity);

    // Lifecycle: (re-)submit a DRAFT/REWORK record for approval -> PENDING (full validation)
    CustomResponse addLocationMapper(String id, JsonNode locationMapperEntity);

    // Lifecycle: PENDING -> APPROVED | REJECTED | REWORK
    CustomResponse approveLocationMapper(LifecycleRequest request);

    // Lifecycle: APPROVED -> ACTIVE(published) | REJECTED | REWORK | PENDING
    CustomResponse reviewLocationMapper(LifecycleRequest request);

    // Toggle a live record between ACTIVE and INACTIVE (rejects any other status)
    CustomResponse toggleStatus(String id);

    CustomResponse searchLocationMapper(SearchCriteria searchCriteria);

    CustomResponse assignLocationMapper(JsonNode locationMapperEntity, String token);

    CustomResponse read(String id);

    CustomResponse delete(String id);

    CustomResponse importData(MultipartFile file);
}