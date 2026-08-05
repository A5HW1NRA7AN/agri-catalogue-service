package com.catalogue.verg.locationobject.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.catalogue.verg.core.dto.CustomResponse;
import com.catalogue.verg.core.dto.LifecycleRequest;
import com.catalogue.verg.core.elasticsearch.dto.SearchCriteria;
import org.springframework.web.multipart.MultipartFile;


public interface LocationobjectService {

    CustomResponse createLocationobject(JsonNode locationobjectEntity);

    CustomResponse updateLocationobject(String id, JsonNode locationobjectEntity);

    // Lifecycle: create an incomplete DRAFT (relaxed validation)
    CustomResponse draftLocationobject(JsonNode locationobjectEntity);

    // Lifecycle: (re-)submit a DRAFT/REWORK record for approval -> PENDING (full validation)
    CustomResponse addLocationobject(String id, JsonNode locationobjectEntity);

    // Lifecycle: PENDING -> APPROVED | REJECTED | REWORK
    CustomResponse approveLocationobject(LifecycleRequest request);

    // Lifecycle: APPROVED -> ACTIVE(published) | REJECTED | REWORK | PENDING
    CustomResponse reviewLocationobject(LifecycleRequest request);

    // Toggle a live record between ACTIVE and INACTIVE (rejects any other status)
    CustomResponse toggleStatus(String id);

    CustomResponse searchLocationobject(SearchCriteria searchCriteria);

    CustomResponse assignLocationobject(JsonNode locationobjectEntity, String token);

    CustomResponse read(String id);

    CustomResponse delete(String id);

    CustomResponse importData(MultipartFile file);

    // Drops the ES index and rebuilds it from the primary store (Postgres); skips DELETED records
    CustomResponse loadFromPrimaryLocationobject();
}