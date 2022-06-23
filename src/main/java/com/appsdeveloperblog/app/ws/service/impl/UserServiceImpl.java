package com.appsdeveloperblog.app.ws.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appsdeveloperblog.app.ws.UserRepository;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.service.UserService;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

 @Autowired
 UserRepository userRepository;

 @Override
 public UserDto createUser(UserDto user) {

  if (userRepository.findByEmail(user.getEmail()) != null) {
   throw new RuntimeException("User already exist");
  }

  UserEntity userEntity = new UserEntity(); 
  BeanUtils.copyProperties(user, userEntity);

  userEntity.setEncryptPassword("test");
  userEntity.setUserId("testUserId1");

  UserEntity storedUserDetails = userRepository.save(userEntity);

  UserDto returnValue = new UserDto();
  BeanUtils.copyProperties(storedUserDetails, returnValue);

  return returnValue;
 }
}
