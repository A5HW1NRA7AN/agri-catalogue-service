package com.catalogue.verg.locationobject.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.catalogue.verg.core.dto.CustomResponse;
import com.catalogue.verg.core.dto.LifecycleRequest;
import com.catalogue.verg.core.elasticsearch.dto.SearchCriteria;
import org.springframework.web.multipart.MultipartFile;


public interface LocationObjectService {

    CustomResponse createLocationObject(JsonNode locationObjectEntity);

    CustomResponse updateLocationObject(String id, JsonNode locationObjectEntity);

    // Lifecycle: create an incomplete DRAFT (relaxed validation)
    CustomResponse draftLocationObject(JsonNode locationObjectEntity);

    // Lifecycle: (re-)submit a DRAFT/REWORK record for approval -> PENDING (full validation)
    CustomResponse addLocationObject(String id, JsonNode locationObjectEntity);

    // Lifecycle: PENDING -> APPROVED | REJECTED | REWORK
    CustomResponse approveLocationObject(LifecycleRequest request);

    // Lifecycle: APPROVED -> ACTIVE(published) | REJECTED | REWORK | PENDING
    CustomResponse reviewLocationObject(LifecycleRequest request);

    // Toggle a live record between ACTIVE and INACTIVE (rejects any other status)
    CustomResponse toggleStatus(String id);

    CustomResponse searchLocationObject(SearchCriteria searchCriteria);

    CustomResponse assignLocationObject(JsonNode locationObjectEntity, String token);

    CustomResponse read(String id);

    CustomResponse delete(String id);

    CustomResponse importData(MultipartFile file);
}