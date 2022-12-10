package com.appsdeveloperblog.app.ws.ui.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.appsdeveloperblog.app.ws.service.UserService;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDTO;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;
import com.appsdeveloperblog.app.ws.ui.model.response.UserRest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class UserControllerTest {

  @InjectMocks
  UserController userController;

  @Mock
  UserService userService;

  UserDto userDto;

  String userId = "asdfghjkl";
  String encryptedPassword = "qwertyuiop";

  @BeforeEach
  void setUp() throws Exception {
    userDto = new UserDto();
    userDto.setFirstName("Sean");
    userDto.setLastName("Mayer");
    userDto.setEmail("test@test.com");
    userDto.setEmailVerificationStatus(Boolean.FALSE);
    userDto.setUserId(userId);
    userDto.setAddresses(getAddressDTOs());
    userDto.setEncryptedPassword(encryptedPassword);
  }

  @Test
  void testGetUser() {
    when(userService.getUserByUserId(anyString())).thenReturn(userDto);

    UserRest userRest = userController.getUser(userId);

    assertNotNull(userRest);
    assertEquals(userId, userRest.getUserId());
    assertEquals(userDto.getFirstName(), userRest.getFirstName());
    assertEquals(userDto.getLastName(), userRest.getLastName());
    assertTrue(userDto.getAddresses().size() == userRest.getAddresses().size());
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
}
