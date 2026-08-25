package com.catalogue.verg.core.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class AuthValidationServiceImpl implements AuthValidationService {

        private final RestTemplate restTemplate;
        private final ObjectMapper objectMapper;

        @Value("${oas.auth.validate-url}")
        private String authValidateUrl;

        @Value("${oas.auth.api-key:}")
        private String apiKey;

        public AuthValidationServiceImpl(
                        RestTemplate restTemplate,
                        ObjectMapper objectMapper) {
                this.restTemplate = restTemplate;
                this.objectMapper = objectMapper;
        }

        @Override
        public JsonNode validateToken(String authorizationHeader) {

                if (authorizationHeader == null || authorizationHeader.isBlank()) {
                        throw new IllegalArgumentException(
                                        "Authorization header is required");
                }

                try {

                        // Remove "Bearer " before sending the token to OAS
                        String token = authorizationHeader;

                        if (token.startsWith("Bearer ")) {
                                token = token.substring(7);
                        }

                        // Prepare request headers
                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);

                        // OAS requires API key using "apikey" header
                        if (apiKey != null && !apiKey.isBlank()) {
                                headers.set("apikey", apiKey);
                        }

                        // OAS expects the token in the request body
                        ObjectNode body = objectMapper.createObjectNode();
                        body.put("token", token);

                        HttpEntity<JsonNode> requestEntity = new HttpEntity<>(body, headers);

                        // Call OAS Auth Service
                        ResponseEntity<JsonNode> response = restTemplate.exchange(
                                        authValidateUrl,
                                        HttpMethod.POST,
                                        requestEntity,
                                        JsonNode.class);

                        // Check HTTP response
                        if (!response.getStatusCode().is2xxSuccessful()) {
                                throw new IllegalStateException(
                                                "Token validation failed. HTTP status: "
                                                                + response.getStatusCode());
                        }

                        JsonNode responseBody = response.getBody();

                        if (responseBody == null || responseBody.isNull()) {
                                throw new IllegalStateException(
                                                "Empty response from Auth Service");
                        }

                        System.out.println(responseBody);
                        return extractUserContext(responseBody);

                } catch (RestClientException e) {

                        // Diagnostic information.
                        // Actual API key is never printed.
                        System.out.println(
                                        "OAS URL: " + authValidateUrl);

                        System.out.println(
                                        "OAS API KEY PRESENT: "
                                                        + (apiKey != null && !apiKey.isBlank()));

                        System.out.println(
                                        "OAS ERROR: " + e.getMessage());

                        throw new IllegalStateException(
                                        "Unable to communicate with OAS Auth Service: "
                                                        + e.getMessage(),
                                        e);
                }
        }

        private JsonNode extractUserContext(JsonNode response) {

                /*
                 * Expected OAS response:
                 *
                 * {
                 * "message": null,
                 * "params": {
                 * "status": "success"
                 * },
                 * "responseCode": "OK",
                 * "result": {
                 * "sub": "...",
                 * "active": true,
                 * "preferred_username": "...",
                 * "entity_type": "MAKER",
                 * "user_id": "..."
                 * }
                 * }
                 */

                JsonNode data = response;

                // Extract the "result" object
                return data;
        }

}