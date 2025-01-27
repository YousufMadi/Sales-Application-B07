package com.b07.exceptions;

public class DatabaseInsertException extends Exception {

  /**
   * serialID for insert exceptions.
   */
  private static final long serialVersionUID = -3819578110674910585L;

  /**
   * Gets the error code
   *
   * @return the error code
   */
  public String getErrorCode() {
    return String.valueOf(serialVersionUID);
  }
}
