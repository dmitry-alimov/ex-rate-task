package com.gd.alimov.service;

import com.gd.alimov.mvc.model.Rate;

public interface ExchangeRatesService {
    Rate getExchangeRate(String base, String to);

    String toJsonBody(Rate to);
}
