package com.example.msemilbank.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public enum Currency {
    AZN(1.70),
    USD(1.0),
    EUR(0.85);

    private final double rate;
    public static final double sameCurrency = 1.001;
    public static final double differentCurrency = 1.01;

    public static Double convert(Currency from, Currency to, Double amount){
        return amount / from.rate * to.rate;
    }
}
