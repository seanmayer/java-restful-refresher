package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.io.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.io.repositories.AddressRepository;
import com.appsdeveloperblog.app.ws.io.repositories.UserRepository;
import com.appsdeveloperblog.app.ws.service.AddressService;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDTO;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  AddressRepository addressRepository;

  @Override
  public List<AddressDTO> getAddresses(String userId) {
    List<AddressDTO> returnValue = new ArrayList<>();
    
    UserEntity userEntity = userRepository.findByUserId(userId);
    if (userEntity == null) return null;

    Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(
      userEntity
    );
    for (AddressEntity addressEntity : addresses) {
      ModelMapper modelMapper = new ModelMapper();
      returnValue.add(modelMapper.map(addressEntity, AddressDTO.class));
    }

    return returnValue;
  }

  @Override
  public AddressDTO getAddress(String addressId) {
    AddressDTO returnValue = null;

    AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
    
    if (addressEntity != null) {
      returnValue = new ModelMapper().map(addressEntity, AddressDTO.class);
    }

    return returnValue;
  }
}
