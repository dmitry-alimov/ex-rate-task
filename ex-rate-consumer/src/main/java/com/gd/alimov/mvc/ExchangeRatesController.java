package com.gd.alimov.mvc;

import com.gd.alimov.client.ConsumerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeRatesController {

    @Autowired
    ConsumerClient client;

    @GetMapping("api/get")
    public ResponseEntity<String> getExchangeRateByPairOfCurrencyCodes(@RequestParam String base, @RequestParam String rate) {
        return client.getRate(base, rate);
    }
}
