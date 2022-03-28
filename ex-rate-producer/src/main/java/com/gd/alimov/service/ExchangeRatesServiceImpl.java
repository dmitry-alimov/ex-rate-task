package com.gd.alimov.service;

import com.gd.alimov.mvc.model.Rate;
import com.gd.alimov.mvc.model.enums.CurrencyCode;
import com.google.gson.Gson;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.NoSuchElementException;

@Service
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

    public Rate getCurrency(String base, String rate) {
        CurrencyCode baseCode = null;
        CurrencyCode rateCode = null;

        try {
            baseCode = CurrencyCode.valueOf(base);
            rateCode = CurrencyCode.valueOf(rate);
        } catch (IllegalArgumentException e) {
            LOGGER.warn("No currency codes constant");
        }

        initialRates();

        if (baseCode != null && rateCode != null) {
            try {
                final CurrencyCode finalBaseCode = baseCode;
                final CurrencyCode finalRateCode = rateCode;
                return new Rate(baseCode,
                        rateCode,
                        rates.stream().filter(r ->
                                        r.getBase().equals(finalBaseCode))
                                .filter(r ->
                                        r.getRate().equals(finalRateCode))
                                .findFirst()
                                .orElseThrow()
                                .getCurrency());
            } catch (NoSuchElementException e) {
                LOGGER.warn("No currency present");
            }
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
