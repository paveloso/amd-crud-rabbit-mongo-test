package com.teststation.crudrabbitmongotest.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

@Component
public class WebhookIntegrationClientImpl implements IntegrationClient {

    private static final Logger LOGGER = Logger.getLogger(WebhookIntegrationClientImpl.class.getName());

    @Value("${integration.webhook.endpoint}")
    private String endpoint;

    @Override
    public int sendData(String json) {
        int responseCode = post(json).statusCode();
        if (responseCode == 200) {
            LOGGER.info("Data successfully passed to webhook: " + json);
        } else {
            LOGGER.severe("Webhook response is not 200");
        }
        return responseCode;
    }

    private HttpResponse<String> post(String body) {
        HttpResponse<String> response = null;
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI(endpoint))
                    .header("content-type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();
            response = getResponse(request);
        } catch (URISyntaxException ex) {
            LOGGER.severe("Building request failed. Check your parameters.");
        }
        return response;
    }

    private HttpResponse<String> getResponse(HttpRequest request) {
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException ex) {
            LOGGER.severe("Building response failed. Check your parameters.");
        }
        return response;
    }
}
