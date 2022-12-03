package com.appsdeveloperblog.app.ws.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.io.repositories.UserRepository;
import com.appsdeveloperblog.app.ws.shared.Utils;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {

  @InjectMocks
  UserServiceImpl userService;

  @Mock
  UserRepository userRepository;

  @Mock
  Utils utils;

  @Mock
  BCryptPasswordEncoder bCryptPasswordEncoder;

  String userId = "asdfghjkl";
  String encryptedPassword = "qwertyuiop";
  UserEntity userEntity;

  @BeforeEach
  void setUp() throws Exception {
    userEntity = new UserEntity();
    userEntity.setId(1L);
    userEntity.setFirstName("Sean");
    userEntity.setUserId(userId);
    userEntity.setEncryptPassword(encryptedPassword);
  }

  @Test
  void testGetUser() {
    when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

    UserDto userDto = userService.getUser("test@test.com");

    assertNotNull(userDto);
    assertEquals("Sean", userDto.getFirstName());
  }

  @Test
  void testGetUser_UsernameNotFoundException() {
    when(userRepository.findByEmail(anyString())).thenReturn(null);
    assertThrows(
      UsernameNotFoundException.class,
      () -> {
        userService.getUser("test@test.com");
      }
    );
  }

  @Test
  void testCreateUser() {
    when(userRepository.findByEmail(anyString())).thenReturn(null);
    when(utils.generateAddressId(anyInt())).thenReturn("zxcvbnm");
    when(utils.generatedUserId(anyInt())).thenReturn(userId);
    when(bCryptPasswordEncoder.encode(anyString()))
      .thenReturn(encryptedPassword);
    when(userRepository.save(any(UserEntity.class)))
      .thenReturn(userEntity);
  }

  @AfterEach
  void tearDown() throws Exception {}
}
