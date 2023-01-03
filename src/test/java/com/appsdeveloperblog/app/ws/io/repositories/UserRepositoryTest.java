package com.appsdeveloperblog.app.ws.io.repositories;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.appsdeveloperblog.app.ws.io.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;


@ExtendWith(SpringExtension.class)
public class UserRepositoryTest {
 
 @Autowired
 UserRepository userRepository;

 @BeforeEach
 void setUp() throws Exception {
  UserEntity userEntity = new UserEntity();
  userEntity.setFirstName("Sean");
  userEntity.setLastName("Mayer");
  userEntity.setUserId("asdfghjkl");
  userEntity.setEncryptPassword("asdfghjkl");
  userEntity.setEmail("test@test.com");
  userEntity.setEmailVerificationStatus(true);

  AddressEntity addressEntity = new AddressEntity();
  addressEntity.setType("shipping");
  addressEntity.setAddressId("asdfghjkl");
  addressEntity.setCity("New York");
  addressEntity.setCountry("USA");
  addressEntity.setPostalCode("12345");
  addressEntity.setStreetName("123 Street Name");

  List<AddressEntity> addresses = new ArrayList<>();
  addresses.add(addressEntity);

  userEntity.setAddresses(addresses);

  userRepository.save(userEntity);

 }

 @Test
 void testGetVerifiedUsers() {
  Pageable pageableRequest = PageRequest.of(0, 2);
  Page<UserEntity> page = userRepository.findAllUsersWithConfirmedEmailAddress(pageableRequest);
  //assertNotNull(page);

  //List<UserEntity> userEntities = page.getContent();
  //assertNotNull(userEntities);
  //assertTrue(userEntities.size() == 1);
 }

}