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
    Pageable pageableRequest = PageRequest.of(0, 2);
    Page<UserEntity> page = userRepository.findAllUsersWithConfirmedEmailAddress(
      pageableRequest
    );
    assertNotNull(page);

    List<UserEntity> userEntities = page.getContent();
    assertNotNull(userEntities);
    assertTrue(userEntities.size() == 2);
  }

  private void createRecords() {
    UserEntity userEntity = new UserEntity();
    userEntity.setFirstName("Sean");
    userEntity.setLastName("Mayer");
    userEntity.setUserId("qwertyuio123456");
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

    UserEntity userEntity2 = new UserEntity();
    userEntity2.setFirstName("Sean");
    userEntity2.setLastName("Mayer");
    userEntity2.setUserId("asdfghjkl123456");
    userEntity2.setEncryptPassword("asdfghjkl");
    userEntity2.setEmail("test1@test1.com");
    userEntity2.setEmailVerificationStatus(true);

    AddressEntity addressEntity2 = new AddressEntity();
    addressEntity2.setType("shipping");
    addressEntity2.setAddressId("asdfghjkl");
    addressEntity2.setCity("New York");
    addressEntity2.setCountry("USA");
    addressEntity2.setPostalCode("12345");
    addressEntity2.setStreetName("123 Street Name");

    List<AddressEntity> addresses2 = new ArrayList<>();
    addresses2.add(addressEntity2);

    userEntity2.setAddresses(addresses2);

    userRepository.save(userEntity2);

    recordsCreated = true;
  }
}
