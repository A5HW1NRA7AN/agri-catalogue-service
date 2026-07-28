package com.catalogue.verg.locationobject.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.catalogue.verg.core.dto.CustomResponse;
import com.catalogue.verg.core.elasticsearch.dto.SearchCriteria;
import org.springframework.web.multipart.MultipartFile;


public interface LocationObjectService {

    CustomResponse createLocationObject(JsonNode locationObjectEntity);

    CustomResponse searchLocationObject(SearchCriteria searchCriteria);

    CustomResponse assignLocationObject(JsonNode locationObjectEntity, String token);

    CustomResponse read(String id);

    CustomResponse delete(String id);

    CustomResponse importData(MultipartFile file);
}