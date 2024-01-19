package com.personalplugsite.data.exception;

public class UserNotAuthenticatedException extends RuntimeException {

  private static final long serialVersionUID = 4605018501708310420L;

  public UserNotAuthenticatedException(String message) {
    super(message);
  }

  public UserNotAuthenticatedException(String message, Throwable cause) {
    super(message, cause);
  }
}
