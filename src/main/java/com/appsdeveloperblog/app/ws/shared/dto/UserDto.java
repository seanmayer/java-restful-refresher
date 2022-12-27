package com.appsdeveloperblog.app.ws.shared.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDto implements Serializable {

  private static final long serialVersionUID = -89898989898989898L;
  private long id;
  private String userId;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private String encryptedPassword;
  private String emailVerificationToken;
  private Boolean emailVerificationStatus = false;
  private List<AddressDTO> addresses;
}
