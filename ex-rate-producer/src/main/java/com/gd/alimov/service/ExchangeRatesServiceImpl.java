package com.gd.alimov.service;

import com.gd.alimov.mvc.model.Rate;
import com.gd.alimov.mvc.model.enums.CurrencyCode;
import com.google.gson.Gson;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class ExchangeRatesServiceImpl implements ExchangeRatesService {

    Logger LOGGER = LoggerFactory.getLogger(ExchangeRatesServiceImpl.class);

    @Getter
    private List<Rate> rates;

    private void initialRates() {
        String stringFromJson = IOUtils.toString(
                ExchangeRatesServiceImpl.class.getResourceAsStream("/test.json"),
                StandardCharsets.UTF_8);

        this.rates = new Gson().fromJson(stringFromJson, ExchangeRatesServiceImpl.class).getRates();
    }

    public ExchangeRatesServiceImpl() {
    }

    public Rate getExchangeRate(String base, String to) {
        CurrencyCode baseCode;
        CurrencyCode rateCode;

        try {
            baseCode = CurrencyCode.valueOf(base);
            rateCode = CurrencyCode.valueOf(to);
        } catch (IllegalArgumentException e) {
            LOGGER.warn("No currency codes constant");
            e.printStackTrace();
            return null;
        }

        initialRates();

        try {
            final CurrencyCode finalBaseCode = baseCode;
            final CurrencyCode finalRateCode = rateCode;
            LOGGER.warn("Client send request to get rates");
            return new Rate(baseCode,
                    rateCode,
                    rates.stream().filter(r ->
                                    r.getBase().equals(finalBaseCode))
                            .filter(r ->
                                    r.getTo().equals(finalRateCode))
                            .findFirst()
                            .orElseThrow()
                            .getExchangeRate());
        } catch (NoSuchElementException e) {
            LOGGER.warn("No currency present");
            e.printStackTrace();
        }
        return null;
    }

    public String toJsonBody(Rate rate) {
        if (rate == null) {
            return "No currency for this currency codes";
        }
        Gson gson = new Gson();
        return gson.toJson(rate);
    }
}
