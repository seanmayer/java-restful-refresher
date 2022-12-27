package com.appsdeveloperblog.app.ws.ui.model.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDetailsRequestModel {

  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private List<AddressRequestModel> addresses;
}
