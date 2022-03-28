package com.gd.alimov.mvc.model;

import com.gd.alimov.mvc.model.enums.CurrencyCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Rate {
    private final CurrencyCode base;
    private final CurrencyCode rate;
    private final Float currency;
}
