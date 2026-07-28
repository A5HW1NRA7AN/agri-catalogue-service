package com.catalogue.verg.locationmapper.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.catalogue.verg.core.dto.CustomResponse;
import com.catalogue.verg.core.elasticsearch.dto.SearchCriteria;
import org.springframework.web.multipart.MultipartFile;


public interface LocationMapperService {

    CustomResponse createLocationMapper(JsonNode locationMapperEntity);

    CustomResponse searchLocationMapper(SearchCriteria searchCriteria);

    CustomResponse assignLocationMapper(JsonNode locationMapperEntity, String token);

    CustomResponse read(String id);

    CustomResponse delete(String id);

    CustomResponse importData(MultipartFile file);
}