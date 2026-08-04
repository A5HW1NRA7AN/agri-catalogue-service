package com.catalogue.verg.marketplace.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.catalogue.verg.core.dto.CustomResponse;
import com.catalogue.verg.core.dto.LifecycleRequest;
import com.catalogue.verg.core.elasticsearch.dto.SearchCriteria;
import org.springframework.web.multipart.MultipartFile;


public interface MarketplaceService {

    CustomResponse createMarketplace(JsonNode marketplaceEntity);

    CustomResponse updateMarketplace(String id, JsonNode marketplaceEntity);

    // Lifecycle: create an incomplete DRAFT (relaxed validation)
    CustomResponse draftMarketplace(JsonNode marketplaceEntity);

    // Lifecycle: (re-)submit a DRAFT/REWORK record for approval -> PENDING (full validation)
    CustomResponse addMarketplace(String id, JsonNode marketplaceEntity);

    // Lifecycle: PENDING -> APPROVED | REJECTED | REWORK
    CustomResponse approveMarketplace(LifecycleRequest request);

    // Lifecycle: APPROVED -> ACTIVE(published) | REJECTED | REWORK | PENDING
    CustomResponse reviewMarketplace(LifecycleRequest request);

    // Toggle a live record between ACTIVE and INACTIVE (rejects any other status)
    CustomResponse toggleStatus(String id);

    CustomResponse searchMarketplace(SearchCriteria searchCriteria);

    CustomResponse assignMarketplace(JsonNode marketplaceEntity, String token);

    CustomResponse read(String id);

    CustomResponse delete(String id);

    CustomResponse importData(MultipartFile file);

    // Drops the ES index and rebuilds it from the primary store (Postgres); skips DELETED records
    CustomResponse loadFromPrimaryMarketplace();
}