package com.gd.alimov.client;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class ConsumerClient {

    private final Logger LOGGER;

    private final RestTemplate restTemplate;

    public ConsumerClient(Logger logger, RestTemplate restTemplate) {
        LOGGER = logger;
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> getRate(String base, String rate) {
        URI uri = null;

        try {
            uri = new URI("http",
                    null,
                    "localhost",
                    8081, "/api/get",
                    "base=" + base + "&" + "rate=" + rate,
                    null);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        LOGGER.info("Client " + "send request to server...");

        Optional<ResponseEntity<String>> response = Optional.empty();

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(Objects.requireNonNull(uri),
                    HttpMethod.GET,
                    new HttpEntity<>("base=" + base + "&" + "rate=" + rate),
                    String.class);

            LOGGER.info("Response status code is: " + responseEntity.getStatusCode());

            response = Optional.of(responseEntity);

        } catch (HttpServerErrorException e) {
            LOGGER.error("Error code: " + e.getStatusCode());
        }
        return response.orElseGet(() -> new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST));
    }
}
