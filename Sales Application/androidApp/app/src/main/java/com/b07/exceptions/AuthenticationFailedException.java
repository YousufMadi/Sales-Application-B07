package com.b07.exceptions;

public class AuthenticationFailedException extends Exception {

  /**
   * Exception for invalid credentials
   */
  private static final long serialVersionUID = -5780354996857343531L;

  /**
   * Gets the error code
   *
   * @return the error code
   */
  public String getErrorCode() {
    return String.valueOf(serialVersionUID);
  }
}
