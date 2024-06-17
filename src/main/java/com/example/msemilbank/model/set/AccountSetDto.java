package com.example.msemilbank.model.set;

import com.example.msemilbank.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountSetDto {
    private Currency currency;
}
