package com.appsdeveloperblog.app.ws.ui.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloperblog.app.ws.ui.model.request.UserDetailsRequestModel;

@RestController
@RequestMapping("/users") // http://localhost:8080/users
public class UserController {

  @GetMapping(value="")
  public String getUser(@RequestBody UserDetailsRequestModel userDetails) {
    return null;
  }

  @PostMapping(value="")
  public String createUser() {
    return "Create user was called";
  }

  @PutMapping(value="")
  public String updateUser() {
    return "Update user was called";
  }

  @DeleteMapping(value="")
  public String deleteUser() {
    return "Delete user was called";
  }
}
