package com.example.msemilbank.mapper;

import com.example.msemilbank.dao.entity.AccountEntity;
import com.example.msemilbank.model.get.AccountGetDto;
import com.example.msemilbank.model.set.AccountSetDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountGetDto mapToDto(AccountEntity accountEntity);
    AccountEntity mapToEntity(AccountSetDto accountSetDto);

    List<AccountGetDto> mapToDtos(List<AccountEntity> accountEntities);
}
