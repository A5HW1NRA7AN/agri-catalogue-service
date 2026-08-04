package com.catalogue.verg.extensionequipment.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.catalogue.verg.core.dto.CustomResponse;
import com.catalogue.verg.core.dto.LifecycleRequest;
import com.catalogue.verg.core.elasticsearch.dto.SearchCriteria;
import org.springframework.web.multipart.MultipartFile;


public interface ExtensionequipmentService {

    CustomResponse createExtensionequipment(JsonNode extensionequipmentEntity);

    CustomResponse updateExtensionequipment(String id, JsonNode extensionequipmentEntity);

    // Lifecycle: create an incomplete DRAFT (relaxed validation)
    CustomResponse draftExtensionequipment(JsonNode extensionequipmentEntity);

    // Lifecycle: (re-)submit a DRAFT/REWORK record for approval -> PENDING (full validation)
    CustomResponse addExtensionequipment(String id, JsonNode extensionequipmentEntity);

    // Lifecycle: PENDING -> APPROVED | REJECTED | REWORK
    CustomResponse approveExtensionequipment(LifecycleRequest request);

    // Lifecycle: APPROVED -> ACTIVE(published) | REJECTED | REWORK | PENDING
    CustomResponse reviewExtensionequipment(LifecycleRequest request);

    // Toggle a live record between ACTIVE and INACTIVE (rejects any other status)
    CustomResponse toggleStatus(String id);

    CustomResponse searchExtensionequipment(SearchCriteria searchCriteria);

    CustomResponse assignExtensionequipment(JsonNode extensionequipmentEntity, String token);

    CustomResponse read(String id);

    CustomResponse delete(String id);

    CustomResponse importData(MultipartFile file);

    // Drops the ES index and rebuilds it from the primary store (Postgres); skips DELETED records
    CustomResponse loadFromPrimaryExtensionequipment();
}