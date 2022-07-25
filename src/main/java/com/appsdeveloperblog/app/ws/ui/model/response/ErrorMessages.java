package com.appsdeveloperblog.app.ws.ui.model.response;

public enum ErrorMessages {
 MISSING_REQUIRED_FIELD("Missing required field. Please check documentation for required fields"),
 RECORD_ALREADY_EXISTS("Record already exists"),
 RECORD_NOT_FOUND("Record not found"),
 INTERNAL_SERVER_ERROR("Internal server error"),
 AUTENTICATION_FAILED("Authentication failed"),
 COULD_NOT_UPDATE_RECORD("Could not update record"),
 COULD_NOT_DELETE_RECORD("Could not delete record"),
 EMAIL_ADDRESS_NOT_VERIFIED("Email address has not been verified");

 private String errorMessage;

 ErrorMessages(String errorMessage) {
  this.errorMessage = errorMessage;
 }

 /**
  * @return the errorMessage
  */
 public String getErrorMessage() {
  return errorMessage;
 }

 /*
  * errorMessage the error message to set
  */
 public void setErrorMessage(String errorMessage) {
  this.errorMessage = errorMessage;
 }
}
