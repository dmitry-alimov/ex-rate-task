package com.gd.alimov.mvc.controller;

import com.gd.alimov.service.ExchangeRatesService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ServerExchangeRatesController {

    @Autowired
    private ExchangeRatesService exchangeRatesService;

    @GetMapping("/api/get")
    public ResponseEntity<String> index(@RequestParam String base, @RequestParam String rate) {
        return new ResponseEntity<>(exchangeRatesService
                .toJsonBody(exchangeRatesService
                        .getCurrency(base, rate)),
                HttpStatus.OK);
    }
}