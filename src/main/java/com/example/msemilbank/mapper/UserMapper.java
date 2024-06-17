package com.example.msemilbank.mapper;

import com.example.msemilbank.dao.entity.UserEntity;
import com.example.msemilbank.model.get.UserGetDto;
import com.example.msemilbank.model.set.UserSetDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "accountGetDtos", source = "accountEntities")
    UserGetDto mapToDto(UserEntity userEntity);
    UserEntity mapToEntity(UserSetDto userSetDto);

    List<UserGetDto> mapToDtos(List<UserEntity> userEntities);
}
