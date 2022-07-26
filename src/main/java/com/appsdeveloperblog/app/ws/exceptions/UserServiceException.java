package com.appsdeveloperblog.app.ws.exceptions;

public class UserServiceException extends RuntimeException {
 
  private static final long serialVersionUID = -89898989898989898L;
 
  public UserServiceException(String message) {
    super(message);
  }
 
  public UserServiceException(String message, Throwable cause) {
    super(message, cause);
  }
  
}
