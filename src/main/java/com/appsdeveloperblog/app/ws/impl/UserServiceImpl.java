package com.appsdeveloperblog.app.ws.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.mobileappws.UserRepository;
import com.appsdeveloperblog.app.ws.service.UserService;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

 @Autowired
 UserRepository userRepository;

 @Override
 public UserDto createUser(UserDto user) {

  UserEntity userEntity = new UserEntity(); 
  BeanUtils.copyProperties(user, userEntity);

  userEntity.setEncryptPassword("test");
  userEntity.setUserId("testUserId");

  UserEntity storedUserDetails = userRepository.save(userEntity);

  UserDto returnValue = new UserDto();
  BeanUtils.copyProperties(storedUserDetails, returnValue);

  return returnValue;
 }

 
}
