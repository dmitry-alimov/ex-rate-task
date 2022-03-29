package com.gd.alimov.client;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@Service
@Slf4j
public class ConsumerClient implements HealthIndicator {

    private final Logger LOGGER;

    private final RestTemplate restTemplate;

    public ConsumerClient(Logger logger, RestTemplate restTemplate) {
        LOGGER = logger;
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> getExchangeRate(String base, String to) {
        URI uri = null;

        try {
            uri = new URI("http",
                    null,
                    "localhost",
                    8081, "/api/get",
                    "base=" + base + "&" + "to=" + to,
                    null);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        LOGGER.info("Client send request to server...");

        ResponseEntity<String> responseEntity;

        try {
            responseEntity = restTemplate.exchange(Objects.requireNonNull(uri),
                    HttpMethod.GET,
                    new HttpEntity<>("base=" + base + "&" + "to=" + to),
                    String.class);

            LOGGER.info("Response status code is: " + responseEntity.getStatusCode());

            return responseEntity;
        } catch (HttpServerErrorException e) {
            LOGGER.error("Error code: " + e.getStatusCode());
        }
        return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
    }

    @Override
    public Health health() {
        String CONSUMER_CLIENT_SERVICE = "Consumer Client";

        if (isServiceHealthGood()) {
            return Health.up().withDetail(CONSUMER_CLIENT_SERVICE, "Service is running").build();
        }
        return Health.down().withDetail(CONSUMER_CLIENT_SERVICE, "Service is not available").build();
    }

    private boolean isServiceHealthGood() {
        return true;
    }
}
