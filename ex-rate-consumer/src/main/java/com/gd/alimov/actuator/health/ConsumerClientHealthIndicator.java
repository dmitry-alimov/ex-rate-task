package com.gd.alimov.actuator.health;

import com.gd.alimov.client.ConsumerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Component
public class ConsumerClientHealthIndicator implements HealthIndicator {

    @Autowired
    private ConsumerClient client;

    @Value("${app.actuator.health.connection.server.url}")
    private String serverUrl;

    @Override
    public Health health() {

        Map<String, String> details = new HashMap<>();

        if (!checkServerConnection()) {
            details.put("check connection", "NOT OK");
            return Health.down().withDetails(details).build();
        } else details.put("check connection", "OK");

        if (client == null) {
            details.put("client", "NULL");
            return Health.down().withDetails(details).build();
        } else details.put("client", "OK");

        ResponseEntity<String> response = client.getExchangeRate("RUB", "USD");

        details.put("httpStatus response", String.valueOf(response.getStatusCode()));

        if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
            return Health.down().withDetails(details).build();
        }

        return Health.up().withDetails(details).build();
    }

    public boolean checkServerConnection() {
        try {
            URL serverURL = new URL(serverUrl);

            HttpURLConnection connection = (HttpURLConnection) serverURL.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int code = connection.getResponseCode();

            return code == 200;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
