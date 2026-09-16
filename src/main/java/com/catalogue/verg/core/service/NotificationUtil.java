package com.catalogue.verg.core.service;

import com.catalogue.verg.core.constants.NotificationTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.util.Map;

@Slf4j
@Component
public class NotificationUtil {

    private static final int MAX_RETRIES = 3;
    private static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(2);
    private static final Duration READ_TIMEOUT = Duration.ofSeconds(5);
    private static final long RETRY_BACKOFF_MILLIS = 250L;

    private final RestClient restClient;

    @Value("${org-user-service.base-url}")
    private String baseUrl;

    @Value("${org-user-service.api-key}")
    private String apiKey;

    public NotificationUtil(RestClient.Builder restClientBuilder) {
        // Without these the client has no timeout at all, so an unresponsive notification
        // service holds the caller's request thread and its open transaction.
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(CONNECT_TIMEOUT);
        requestFactory.setReadTimeout(READ_TIMEOUT);

        this.restClient = restClientBuilder.requestFactory(requestFactory).build();
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

            } catch (HttpClientErrorException e) {

                // 4xx is permanent: unknown template code, module mismatch, or nobody in this
                // org holds the receiver role. Retrying cannot change it.
                log.error(
                        "Notification rejected: templateModule={} templateCode={} orgId={} status={} body={}",
                        templateModule,
                        templateCode,
                        orgId,
                        e.getStatusCode(),
                        e.getResponseBodyAsString(),
                        e
                );

                return;

            } catch (Exception e) {

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

                if (attempt < MAX_RETRIES) {
                    try {
                        Thread.sleep(RETRY_BACKOFF_MILLIS * attempt);
                    } catch (InterruptedException interrupted) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
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
