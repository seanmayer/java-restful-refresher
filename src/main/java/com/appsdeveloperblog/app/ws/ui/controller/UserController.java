package com.appsdeveloperblog.app.ws.ui.controller;

import com.appsdeveloperblog.app.ws.exceptions.UserServiceException;
import com.appsdeveloperblog.app.ws.service.UserService;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;
import com.appsdeveloperblog.app.ws.ui.model.request.UserDetailsRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.response.ErrorMessages;
import com.appsdeveloperblog.app.ws.ui.model.response.OperationStatusModel;
import com.appsdeveloperblog.app.ws.ui.model.response.RequestOperationStatus;
import com.appsdeveloperblog.app.ws.ui.model.response.UserRest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("users") // http://localhost:8080/users
public class UserController {

  @Autowired(required = true)
  UserService userService;

  @GetMapping(
    path = "/{id}",
    produces = {
      MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE,
    }
  )
  public UserRest getUser(@PathVariable String id) {
    UserRest returnValue = new UserRest();

    UserDto userDto = userService.getUserByUserId(id);
    BeanUtils.copyProperties(userDto, returnValue);

    return returnValue;
  }

  @PostMapping(
    consumes = {
      MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE,
    },
    produces = {
      MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE,
    }
  )
  public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails)
    throws Exception {
    UserRest returnValue = new UserRest();

    if (userDetails.getFirstName().isEmpty()) {
      throw new NullPointerException("The object is null");
    }

    UserDto userDto = new UserDto();
    BeanUtils.copyProperties(userDetails, userDto);

    UserDto createdUser = userService.createUser(userDto);
    BeanUtils.copyProperties(createdUser, returnValue);

    return returnValue;
  }

  @PutMapping(
    path = "/{id}",
    consumes = {
      MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE,
    },
    produces = {
      MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE,
    }
  )
  public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {
    UserRest returnValue = new UserRest();

    if (userDetails.getFirstName().isEmpty()) {
      throw new NullPointerException("The object is null");
    }

    UserDto userDto = new UserDto();
    BeanUtils.copyProperties(userDetails, userDto);

    UserDto updateUser = userService.updateUser(id, userDto);
    BeanUtils.copyProperties(updateUser, returnValue);

    return returnValue;
  }

  @DeleteMapping(
    path = "/{id}",
    produces = {
      MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE,
    })
  public OperationStatusModel deleteUser(@PathVariable String id) {

    OperationStatusModel returnValue = new OperationStatusModel();
    returnValue.setOperationName(RequestOperationName.DELETE.name());

    userService.deleteUser(id);

    returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

    return returnValue;
  }

  @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
    @RequestParam(value = "limit", defaultValue = "25") int limit) {

    List<UserRest> returnValue = new ArrayList<>();
    List<UserDto> users = userService.getUsers(page, limit);

    for (UserDto userDto : users) {
      UserRest userRest = new UserRest();
      BeanUtils.copyProperties(userDto, userRest);
      returnValue.add(userRest);
    }
    
    return returnValue;
  }  
}
