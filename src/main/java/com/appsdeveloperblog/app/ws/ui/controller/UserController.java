package com.appsdeveloperblog.app.ws.ui.controller;

import com.appsdeveloperblog.app.ws.service.AddressService;
import com.appsdeveloperblog.app.ws.service.UserService;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDTO;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;
import com.appsdeveloperblog.app.ws.ui.model.request.PasswordResetModel;
import com.appsdeveloperblog.app.ws.ui.model.request.PasswordResetRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.request.UserDetailsRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.response.AddressRest;
import com.appsdeveloperblog.app.ws.ui.model.response.OperationStatusModel;
import com.appsdeveloperblog.app.ws.ui.model.response.RequestOperationStatus;
import com.appsdeveloperblog.app.ws.ui.model.response.UserRest;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users") // http://localhost:8080/users
public class UserController {

  @Autowired(required = true)
  UserService userService;

  @Autowired(required = true)
  AddressService addressesService;

  @Autowired(required = true)
  AddressService addressService;

  @Operation(
    summary =  "The Get User Details Web Service Endpoint",
    description = "${userController.GetUser.ApiOperation.Notes}"
  )
  @ApiImplicitParams(
    {
      @ApiImplicitParam(
        name = "authorization",
        value = "${userController.authorizationHeader.description}",
        paramType = "header",
        required = true,
        dataTypeClass = String.class
      ),
    }
  )
  @GetMapping(
    path = "/{id}",
    produces = {
      MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE,
    }
  )
  public UserRest getUser(@PathVariable String id) {
    UserRest returnValue = new UserRest();

    UserDto userDto = userService.getUserByUserId(id);
    new ModelMapper().map(userDto, returnValue);

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

    //UserDto userDto = new UserDto();
    //BeanUtils.copyProperties(userDetails, userDto);
    ModelMapper modelMapper = new ModelMapper();
    UserDto userDto = modelMapper.map(userDetails, UserDto.class);

    UserDto createdUser = userService.createUser(userDto);

    returnValue = modelMapper.map(createdUser, UserRest.class);

    return returnValue;
  }

  @ApiImplicitParams(
    {
      @ApiImplicitParam(
        name = "authorization",
        value = "${userController.authorizationHeader.description}",
        paramType = "header",
        required = true,
        dataTypeClass = String.class
      ),
    }
  )
  @PutMapping(
    path = "/{id}",
    consumes = {
      MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE,
    },
    produces = {
      MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE,
    }
  )
  public UserRest updateUser(
    @PathVariable String id,
    @RequestBody UserDetailsRequestModel userDetails
  ) {
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

  @ApiImplicitParams(
    {
      @ApiImplicitParam(
        name = "authorization",
        value = "${userController.authorizationHeader.description}",
        paramType = "header",
        required = true,
        dataTypeClass = String.class
      ),
    }
  )


  @PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.userId") // only admin can delete user or user can delete himself
  //@PreAuthorize("hasAuthority('DELETE_AUTHORITY')")
  @Secured("ROLE_ADMIN")
  @DeleteMapping(
    path = "/{id}",
    produces = {
      MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE,
    }
  )
  public OperationStatusModel deleteUser(@PathVariable String id) {
    OperationStatusModel returnValue = new OperationStatusModel();
    returnValue.setOperationName(RequestOperationName.DELETE.name());

    userService.deleteUser(id);

    returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

    return returnValue;
  }

  @ApiImplicitParams(
    {
      @ApiImplicitParam(
        name = "authorization",
        value = "${userController.authorizationHeader.description}",
        paramType = "header",
        required = true,
        dataTypeClass = String.class
      ),
    }
  )
  @GetMapping(
    produces = {
      MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE,
    }
  )
  public List<UserRest> getUsers(
    @RequestParam(value = "page", defaultValue = "0") int page,
    @RequestParam(value = "limit", defaultValue = "25") int limit
  ) {
    List<UserRest> returnValue = new ArrayList<>();
    List<UserDto> users = userService.getUsers(page, limit);

    for (UserDto userDto : users) {
      UserRest userRest = new UserRest();
      BeanUtils.copyProperties(userDto, userRest);
      returnValue.add(userRest);
    }

    return returnValue;
  }

  // http://localhost:8080/mobile-app-ws/users/{id}/addresses
  @ApiImplicitParams(
    {
      @ApiImplicitParam(
        name = "authorization",
        value = "${userController.authorizationHeader.description}",
        paramType = "header",
        required = true,
        dataTypeClass = String.class
      ),
    }
  )
  @GetMapping(
    path = "/{userId}/addresses",
    produces = {
      MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE,
    }
  )
  public CollectionModel<AddressRest> getUserAddresses(
    @PathVariable String userId
  ) {
    List<AddressRest> returnValue = new ArrayList<>();
    List<AddressDTO> addressesDTO = addressesService.getAddresses(userId);

    if (addressesDTO != null && !addressesDTO.isEmpty()) {
      Type listType = new TypeToken<List<AddressRest>>() {}.getType();
      returnValue = new ModelMapper().map(addressesDTO, listType);

      for (AddressRest addressRest : returnValue) {
        Link selfLink = WebMvcLinkBuilder
          .linkTo(
            WebMvcLinkBuilder
              .methodOn(UserController.class)
              .getUserAddress(addressRest.getAddressId(), userId)
          )
          .withSelfRel();
        addressRest.add(selfLink);
      }
    }

    //http://localhost:8080/users/{userId}
    Link userLink = WebMvcLinkBuilder
      .linkTo(UserController.class)
      .slash(userId)
      .withRel("user");

    Link selfLink = WebMvcLinkBuilder
      .linkTo(
        WebMvcLinkBuilder
          .methodOn(UserController.class)
          .getUserAddresses(userId)
      )
      .withSelfRel();

    return CollectionModel.of(returnValue, userLink, selfLink);
  }

  @ApiImplicitParams(
    {
      @ApiImplicitParam(
        name = "authorization",
        value = "${userController.authorizationHeader.description}",
        paramType = "header",
        required = true,
        dataTypeClass = String.class
      ),
    }
  )
  @GetMapping(
    path = "/{userId}/addresses/{addressId}",
    produces = {
      MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE,
    }
  )
  public EntityModel<AddressRest> getUserAddress(
    @PathVariable String addressId,
    @PathVariable String userId
  ) {
    AddressDTO addressDTO = addressService.getAddress(addressId);

    AddressRest returnValue = new ModelMapper()
      .map(addressDTO, AddressRest.class);

    //http://localhost:8080/users/{userId}
    Link userLink = WebMvcLinkBuilder
      .linkTo(UserController.class)
      .slash(userId)
      .withRel("user");

    //http://localhost:8080/users/{userId}/addresss/{addressId}
    Link userAddressesLink = WebMvcLinkBuilder
      .linkTo(
        WebMvcLinkBuilder
          .methodOn(UserController.class)
          .getUserAddresses(userId)
      )
      .withRel("addresses");
    //.slash(userId)
    //.slash("addresses")

    Link selfLink = WebMvcLinkBuilder
      .linkTo(
        WebMvcLinkBuilder
          .methodOn(UserController.class)
          .getUserAddress(addressId, userId)
      )
      .withSelfRel();
    //.slash(userId)
    //.slash("addresses")
    //.slash(addressId)

    //returnValue.add(userLink);
    //returnValue.add(userAddressesLink);
    //returnValue.add(selfLink);

    return EntityModel.of(
      returnValue,
      Arrays.asList(userLink, userAddressesLink, selfLink)
    );
  }

  // http://localhost:8080/mobile-app-ws/users/email-verification?token=dfasfa

  @GetMapping(
    path = "/email-verification",
    produces = { MediaType.APPLICATION_JSON_VALUE }
  )
  @CrossOrigin(origins = "*")
  public OperationStatusModel verifyEmailToken(
    @RequestParam(value = "token") String token
  ) {
    OperationStatusModel returnValue = new OperationStatusModel();
    returnValue.setOperationName(RequestOperationName.VERIFY_EMAIL.name());

    boolean isVerified = userService.verifyEmailToken(token);

    if (isVerified) {
      returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
    } else {
      returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
    }

    return returnValue;
  }

  /* http://localhost:8080/mobile-app-ws/password-reset */

  @PostMapping(
    path = "/password-reset-request",
    consumes = {
      MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
    }
  )
  public OperationStatusModel requestReset(
    @RequestBody PasswordResetRequestModel passwordResetModel
  ) {
    OperationStatusModel returnValue = new OperationStatusModel();

    boolean operationResult = userService.requestPasswordReset(
      passwordResetModel.getEmail()
    );

    returnValue.setOperationName(
      RequestOperationName.REQUEST_PASSWORD_RESET.name()
    );
    returnValue.setOperationResult(RequestOperationStatus.ERROR.name());

    if (operationResult) {
      returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
    }

    return returnValue;
  }

  @PostMapping(
    path = "/password-reset",
    consumes = {
      MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
    }
  )
  public OperationStatusModel resetPassword(
    @RequestBody PasswordResetModel passwordResetModel
  ) {
    OperationStatusModel returnValue = new OperationStatusModel();

    boolean operationResult = userService.resetPassword(
      passwordResetModel.getToken(),
      passwordResetModel.getPassword()
    );

    returnValue.setOperationName(RequestOperationName.PASSWORD_RESET.name());
    returnValue.setOperationResult(RequestOperationStatus.ERROR.name());

    if (operationResult) {
      returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
    }

    return returnValue;
  }
}
