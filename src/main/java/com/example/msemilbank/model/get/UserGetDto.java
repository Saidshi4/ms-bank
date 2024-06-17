package com.example.msemilbank.model.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGetDto {
    private Long id;
    private String name;
    private String surname;
    private List<AccountGetDto> accountGetDtos;
}
