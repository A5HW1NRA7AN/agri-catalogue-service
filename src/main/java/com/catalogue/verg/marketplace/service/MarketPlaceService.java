package com.catalogue.verg.marketplace.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.catalogue.verg.core.dto.CustomResponse;
import com.catalogue.verg.core.dto.LifecycleRequest;
import com.catalogue.verg.core.elasticsearch.dto.SearchCriteria;
import org.springframework.web.multipart.MultipartFile;


public interface MarketPlaceService {

    CustomResponse createMarketPlace(JsonNode marketPlaceEntity);

    CustomResponse updateMarketPlace(String id, JsonNode marketPlaceEntity);

    // Lifecycle: create an incomplete DRAFT (relaxed validation)
    CustomResponse draftMarketPlace(JsonNode marketPlaceEntity);

    // Lifecycle: (re-)submit a DRAFT/REWORK record for approval -> PENDING (full validation)
    CustomResponse addMarketPlace(String id, JsonNode marketPlaceEntity);

    // Lifecycle: PENDING -> APPROVED | REJECTED | REWORK
    CustomResponse approveMarketPlace(LifecycleRequest request);

    // Lifecycle: APPROVED -> ACTIVE(published) | REJECTED | REWORK | PENDING
    CustomResponse reviewMarketPlace(LifecycleRequest request);

    // Toggle a live record between ACTIVE and INACTIVE (rejects any other status)
    CustomResponse toggleStatus(String id);

    CustomResponse searchMarketPlace(SearchCriteria searchCriteria);

    CustomResponse assignMarketPlace(JsonNode marketPlaceEntity, String token);

    CustomResponse read(String id);

    CustomResponse delete(String id);

    CustomResponse importData(MultipartFile file);
}