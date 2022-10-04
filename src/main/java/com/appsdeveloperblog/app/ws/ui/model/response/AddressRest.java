package com.appsdeveloperblog.app.ws.ui.model.response;

import org.springframework.hateoas.RepresentationModel;

public class AddressRest  extends RepresentationModel<AddressRest>{

  private String addressId;
  private String city;
  private String country;
  private String streetName;
  private String postalCode;
  private String type;

  public String getAddressId() {
    return this.addressId;
  }

  public void setAddressId(String addressId) {
    this.addressId = addressId;
  }

  public String getCity() {
    return this.city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCountry() {
    return this.country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getStreetName() {
    return this.streetName;
  }

  public void setStreetName(String streetName) {
    this.streetName = streetName;
  }

  public String getPostalCode() {
    return this.postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
