package com.appsdeveloperblog.app.ws.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.io.repositories.UserRepository;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {

  @InjectMocks
  UserServiceImpl userService;

  @Mock
  UserRepository userRepository;

  @BeforeEach
  void setUp() throws Exception {}

  @Test
   void testGetUser() {
    UserEntity userEntity = new UserEntity();
    userEntity.setId(1L);
    userEntity.setFirstName("Sean");
    userEntity.setUserId("asdfghjkl");
    userEntity.setEncryptPassword("qwertyuiop");

    when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

    UserDto userDto = userService.getUser("test@test.com");

    assertNotNull(userDto);
    assertEquals("Sean", userDto.getFirstName());
  }

  @AfterEach
  void tearDown() throws Exception {
  }
}
