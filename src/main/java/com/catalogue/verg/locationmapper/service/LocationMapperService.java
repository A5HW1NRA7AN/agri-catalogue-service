package com.catalogue.verg.locationmapper.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.catalogue.verg.core.dto.CustomResponse;
import com.catalogue.verg.core.dto.LifecycleRequest;
import com.catalogue.verg.core.elasticsearch.dto.SearchCriteria;
import org.springframework.web.multipart.MultipartFile;


public interface LocationmapperService {

    CustomResponse createLocationmapper(JsonNode locationmapperEntity);

    CustomResponse updateLocationmapper(String id, JsonNode locationmapperEntity);

    // Lifecycle: create an incomplete DRAFT (relaxed validation)
    CustomResponse draftLocationmapper(JsonNode locationmapperEntity);

    // Lifecycle: (re-)submit a DRAFT/REWORK record for approval -> PENDING (full validation)
    CustomResponse addLocationmapper(String id, JsonNode locationmapperEntity);

    // Lifecycle: PENDING -> APPROVED | REJECTED | REWORK
    CustomResponse approveLocationmapper(LifecycleRequest request);

    // Lifecycle: APPROVED -> ACTIVE(published) | REJECTED | REWORK | PENDING
    CustomResponse reviewLocationmapper(LifecycleRequest request);

    // Toggle a live record between ACTIVE and INACTIVE (rejects any other status)
    CustomResponse toggleStatus(String id);

    CustomResponse searchLocationmapper(SearchCriteria searchCriteria);

    CustomResponse assignLocationmapper(JsonNode locationmapperEntity, String token);

    CustomResponse read(String id);

    CustomResponse delete(String id);

    CustomResponse importData(MultipartFile file);

    // Drops the ES index and rebuilds it from the primary store (Postgres); skips DELETED records
    CustomResponse loadFromPrimaryLocationmapper();
}