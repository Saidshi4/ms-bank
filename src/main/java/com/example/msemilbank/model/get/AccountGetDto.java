package com.example.msemilbank.model.get;

import com.example.msemilbank.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountGetDto {
    private String accountNumber;
    private Currency currency;
    private Double amount;
}
