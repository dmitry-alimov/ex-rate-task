package com.gd.alimov.mvc;

import com.gd.alimov.client.ConsumerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeRatesController {

    @Autowired
    ConsumerClient client;

    @Value("${info.app.version}")
    private String appVersion;

    @GetMapping("api/get")
    public ResponseEntity<String> getExchangeRateByPairOfCurrencyCodes(@RequestParam String base, @RequestParam String to) {
        return client.getExchangeRate(base, to);
    }

    @GetMapping("api/version")
    public String getVersion() {
        return appVersion;
    }
}
