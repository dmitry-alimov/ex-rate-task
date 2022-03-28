package com.gd.alimov.service;

import com.gd.alimov.mvc.model.Rate;

public interface ExchangeRatesService {
    Rate getCurrency(String base, String rate);

    String toJsonBody(Rate rate);
}
