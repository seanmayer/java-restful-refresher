package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.exceptions.UserServiceException;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.io.repositories.UserRepository;
import com.appsdeveloperblog.app.ws.service.UserService;
import com.appsdeveloperblog.app.ws.shared.Utils;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDTO;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;
import com.appsdeveloperblog.app.ws.ui.model.response.ErrorMessage;
import com.appsdeveloperblog.app.ws.ui.model.response.ErrorMessages;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  Utils utils;

  @Autowired
  BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public UserDto createUser(UserDto user) {

    if (userRepository.findByEmail(user.getEmail()) != null) {
      throw new RuntimeException("User already exist");
    }

    for (int i = 0; i < user.getAddresses().size(); i++) {
      AddressDTO address = user.getAddresses().get(i);
      address.setUserDetails(user);
      address.setAddressId(utils.generateAddressId(30));
      user.getAddresses().set(i, address);
    }

    //BeanUtils.copyProperties(user, userEntity);

    //UserDto is transfered to UserEntity
    //UserEntity is saved to database
    //UserEntity is transfered to UserDto
    ModelMapper modelMapper = new ModelMapper();
    UserEntity userEntity = modelMapper.map(user, UserEntity.class);

    String publicUserId = utils.generatedUserId(30);
    userEntity.setEncryptPassword(
      bCryptPasswordEncoder.encode(user.getPassword())
    );
    userEntity.setUserId(publicUserId);

    UserEntity storedUserDetails = userRepository.save(userEntity);

    //BeanUtils.copyProperties(storedUserDetails, returnValue);
    UserDto returnValue = modelMapper.map(storedUserDetails, UserDto.class);

    return returnValue;
  }

  @Override
  public UserDetails loadUserByUsername(String email)
    throws UsernameNotFoundException {
    UserEntity userEntity = userRepository.findByEmail(email);

    if (userEntity == null) {
      throw new UsernameNotFoundException(email);
    }

    return new User(
      userEntity.getEmail(),
      userEntity.getEncryptPassword(),
      new ArrayList<>()
    );
  }

  @Override
  public UserDto getUser(String email) {
    UserEntity userEntity = userRepository.findByEmail(email);

    if (userEntity == null) {
      throw new UsernameNotFoundException(email);
    }

    UserDto returnValue = new UserDto();
    BeanUtils.copyProperties(userEntity, returnValue);
    return returnValue;
  }

  @Override
  public UserDto getUserByUserId(String userId) {
    UserDto returnValue = new UserDto();
    UserEntity userEntity = userRepository.findByUserId(userId);
    if (userEntity == null) throw new UsernameNotFoundException(
      "User with ID " + userId + " not found"
    );
    BeanUtils.copyProperties(userEntity, returnValue);
    return returnValue;
  }

  @Override
  public UserDto updateUser(String userId, UserDto user) {
    UserDto returnValue = new UserDto();
    UserEntity userEntity = userRepository.findByUserId(userId);

    if (userEntity == null) {
      throw new UserServiceException(
        ErrorMessages.RECORD_NOT_FOUND.getErrorMessage()
      );
    }

    userEntity.setFirstName(user.getFirstName());
    userEntity.setLastName(user.getLastName());

    UserEntity updatedUserDetails = userRepository.save(userEntity);

    BeanUtils.copyProperties(updatedUserDetails, returnValue);

    return returnValue;
  }

  @Override
  public void deleteUser(String userId) {
    UserEntity userEntity = userRepository.findByUserId(userId);
    if (userEntity == null) {
      throw new UserServiceException(
        ErrorMessages.RECORD_NOT_FOUND.getErrorMessage()
      );
    }
    userRepository.delete(userEntity);
  }

  @Override
  public List<UserDto> getUsers(int page, int limit) {
    List<UserDto> returnValue = new ArrayList<>();
    Pageable pageableRequest = PageRequest.of(page, limit);
    Page<UserEntity> usersPage = userRepository.findAll(pageableRequest);
    List<UserEntity> users = usersPage.getContent();

    for (UserEntity userEntity : users) {
      UserDto userDto = new UserDto();
      BeanUtils.copyProperties(userEntity, userDto);
      returnValue.add(userDto);
    }

    return returnValue;
  }
}
