package com.appsdeveloperblog.app.ws.io.repositories;

import com.appsdeveloperblog.app.ws.io.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;

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

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryTest {

  @Autowired
  UserRepository userRepository;

  static boolean recordsCreated = false;

  @BeforeEach
  void setUp() throws Exception {
    if (!recordsCreated) createRecords();
  }

  @Test
  void testGetVerifiedUsers() {
   Pageable pageableRequest = PageRequest.of(1, 1);
   Page<UserEntity> page = userRepository.findAllUsersWithConfirmedEmailAddress(pageableRequest);
   assertNotNull(page);
   
         List<UserEntity> userEntities = page.getContent();
         assertNotNull(userEntities);
         assertTrue(userEntities.size() == 1);
  }

  private void createRecords() {
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

    recordsCreated = true;
  }
}
