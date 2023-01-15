package com.appsdeveloperblog.app.ws.ui.controller;

import com.appsdeveloperblog.app.ws.ui.model.request.UserLoginRequestModel;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
  @Operation(
    summary = "User Login",
    description = "The Login API returns the access token, which can be used to access other APIs",
    tags = { "authentication-controller" }
  )
  @ApiResponses(
    value = {
      @ApiResponse(
        code = 200,
        message = "OK",
        responseHeaders = {
          @ResponseHeader(
            name = "authorization",
            description = "Bearer <JWT value here>",
            response = String.class
          ),
          @ResponseHeader(
            name = "userId",
            description = "<Public User Id value here>",
            response = String.class
          ),
        }
      ),
    }
  )
  @PostMapping(
    path = "/users/login",
    consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE },
    produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
  )
  public void theFakeLogin(@RequestBody UserLoginRequestModel loginRequestModel) {
    throw new IllegalStateException(
      "This method should not be called. This method is implemented by Spring Security"
    );
  }
}
