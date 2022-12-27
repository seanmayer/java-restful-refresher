package com.appsdeveloperblog.app.ws.ui.model.response;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AddressRest extends RepresentationModel<AddressRest> {

  private String addressId;
  private String city;
  private String country;
  private String streetName;
  private String postalCode;
  private String type;
}
