package com.gd.alimov.service;

import com.gd.alimov.mvc.model.enums.CurrencyCode;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Endpoint(id = "currencies")
@Component
public class CustomActuatorEndpoint {

    @ReadOperation
    public Map<CurrencyCode, String> customEndpoint() {
        CurrencyCode currencyCode = CurrencyCode.valueOf("USD");
        List<CurrencyCode> list = Arrays.asList(Objects.requireNonNull(currencyCode).getAllCurrencyCode());
        return list.stream()
                .collect(Collectors.toMap(Function.identity(), CurrencyCode::getDescription));
    }
}
