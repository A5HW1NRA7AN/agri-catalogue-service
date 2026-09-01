package com.catalogue.verg.core.service;

import com.catalogue.verg.core.constants.NotificationTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.Map;

@Slf4j
@Component
public class NotificationUtil {

    private static final int MAX_RETRIES = 3;

    private final RestClient restClient;

    @Value("${org-user-service.base-url}")
    private String baseUrl;

    @Value("${org-user-service.api-key}")
    private String apiKey;

    public NotificationUtil(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public void sendNotification(
            String templateModule,
            String templateCodeSuffix,
            NotificationTemplate template,
            Map<String, String> templateVariables,
            String orgId
    ) {

        String templateCode =
                template.templateCode()
                        + "_"
                        + templateCodeSuffix;

        NotificationRequest request = new NotificationRequest(
                templateModule,
                templateCode,
                templateVariables,
                "PORTAL",
                null,
                null,
                orgId
        );

        Exception lastError = null;

        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {

            try {
                ResponseEntity<Void> response = restClient.post()
                        .uri(baseUrl + "/api/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("apikey", apiKey)
                        .body(request)
                        .retrieve()
                        .toBodilessEntity();

                log.info(
                        "Notification sent: templateModule={} templateCode={} attempt={} status={}",
                        templateModule,
                        templateCode,
                        attempt,
                        response.getStatusCode()
                );

                return;

            } catch (RestClientException e) {

                lastError = e;

                log.error(
                        "Notification failed: templateModule={} templateCode={} attempt={}/{} error={}",
                        templateModule,
                        templateCode,
                        attempt,
                        MAX_RETRIES,
                        e.getMessage(),
                        e
                );
            }
        }

        log.error(
                "Notification failed after {} attempts: templateModule={} templateCode={}",
                MAX_RETRIES,
                templateModule,
                templateCode,
                lastError
        );
    }

    /**
     * Request body sent to the notification service.
     */
    private record NotificationRequest(
            String templateModule,
            String templateCode,
            Map<String, String> templateVariables,
            String notificationChannel,
            String emailId,
            String phoneNumber,
            String orgId
    ) {
    }
}
