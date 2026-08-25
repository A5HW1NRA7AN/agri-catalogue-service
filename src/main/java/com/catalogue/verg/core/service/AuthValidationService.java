package com.catalogue.verg.core.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface AuthValidationService {

    JsonNode validateToken(String authorizationHeader);
}