package com.appsdeveloperblog.app.ws.ui.model.request;

public class PasswordResetModel {

  public String token;
  public String password;

  public String getToken() {
    return this.token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
