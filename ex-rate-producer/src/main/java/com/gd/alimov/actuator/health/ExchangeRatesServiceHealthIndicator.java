package com.gd.alimov.actuator.health;

import com.gd.alimov.mvc.model.Rate;
import com.gd.alimov.service.ExchangeRatesService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ExchangeRatesServiceHealthIndicator implements HealthIndicator {

    @Autowired
    ExchangeRatesService service;

    @Override
    public Health health() {
        Rate rate = service.getExchangeRate("RUB", "USD");

        if (rate != null) {
            return Health.up().withDetail("rates", "OK").build();
        } else return Health.down().withDetail("rates", "NOT OK").build();
    }
}
