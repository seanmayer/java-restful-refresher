package com.appsdeveloperblog.app.ws.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.appsdeveloperblog.app.ws.io.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.io.repositories.UserRepository;
import com.appsdeveloperblog.app.ws.shared.AmazonSES;
import com.appsdeveloperblog.app.ws.shared.Utils;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDTO;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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

  @Mock
  AmazonSES amazonSES;

  String userId = "asdfghjkl";
  String encryptedPassword = "qwertyuiop";
  UserEntity userEntity;

  @BeforeEach
  void setUp() throws Exception {
    userEntity = new UserEntity();
    userEntity.setId(1L);
    userEntity.setFirstName("Sean");
    userEntity.setLastName("Mayer");
    userEntity.setUserId(userId);
    userEntity.setEncryptPassword(encryptedPassword);
    userEntity.setEmail("test@test.com");
    userEntity.setEmailVerificationToken("lkjhgfdsa");
    userEntity.setAddresses(getAddressesEntity());
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
    when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

    UserDto userDto = new UserDto();
    userDto.setAddresses(getAddressDTOs());
    userDto.setFirstName("Sean");
    userDto.setLastName("Mayer");
    userDto.setPassword("12345678");
    userDto.setEmail("test@test.com");

    UserDto storedUserDetails = userService.createUser(userDto);
    assertNotNull(storedUserDetails);
    assertEquals(userEntity.getFirstName(), storedUserDetails.getFirstName());
    assertEquals(userEntity.getLastName(), storedUserDetails.getLastName());
    assertNotNull(storedUserDetails.getUserId());
    assertEquals(
      storedUserDetails.getAddresses().size(),
      userEntity.getAddresses().size()
    );
    verify(utils, times(storedUserDetails.getAddresses().size()))
      .generateAddressId(30);
    verify(bCryptPasswordEncoder, times(1)).encode("12345678");
    verify(userRepository, times(1)).save(any(UserEntity.class));
  }

  private List<AddressDTO> getAddressDTOs() {
    AddressDTO addressDto = new AddressDTO();
    addressDto.setType("testshipping");
    addressDto.setCity("testcity");
    addressDto.setCountry("testcountry");
    addressDto.setStreetName("teststreetname");
    addressDto.setPostalCode("testpostalcode");

    AddressDTO billingAddressDto = new AddressDTO();
    billingAddressDto.setType("testbilling");
    billingAddressDto.setCity("testcity");
    billingAddressDto.setCountry("testcountry");
    billingAddressDto.setStreetName("teststreetname");
    billingAddressDto.setPostalCode("testpostalcode");

    List<AddressDTO> addresses = new ArrayList<>();
    addresses.add(addressDto);
    addresses.add(billingAddressDto);

    return addresses;
  }

  private List<AddressEntity> getAddressesEntity() {
    List<AddressDTO> addresses = getAddressDTOs();
    Type listType = new TypeToken<List<AddressEntity>>() {}.getType();
    return new ModelMapper().map(addresses, listType);
  }

  @AfterEach
  void tearDown() throws Exception {}
}
