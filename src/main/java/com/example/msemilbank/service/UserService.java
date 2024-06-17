package com.example.msemilbank.service;

import com.example.msemilbank.dao.repository.UserRepository;
import com.example.msemilbank.dao.entity.UserEntity;
import com.example.msemilbank.exception.NotFoundException;
import com.example.msemilbank.mapper.UserMapper;
import com.example.msemilbank.model.get.UserGetDto;
import com.example.msemilbank.model.set.UserSetDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private UserEntity findById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(()->new NotFoundException("User not found by user id " + userId));
    }

    public void saveUser(UserSetDto userSetDto){
        log.info("ActionLog.saveUser.start");
        UserEntity userEntity = userMapper.mapToEntity(userSetDto);
        userRepository.save(userEntity);
        log.info("ActionLog.saveUser.end");
    }

    public UserGetDto getUser(Long userId){
        log.info("ActionLog.getUser.start");
        UserEntity userEntity = findById(userId);
        UserGetDto userGetDto = userMapper.mapToDto(userEntity);
        log.info("ActionLog.getUser.end");
        return userGetDto;
    }

    public List<UserGetDto> getAllUsers(){
        log.info("ActionLog.getAllUser.start");
        List<UserEntity> userEntities = userRepository.findAll();
        List<UserGetDto> userGetDtos = userMapper.mapToDtos(userEntities);
        log.info("ActionLog.getAllUser.end");
        return userGetDtos;
    }
}
