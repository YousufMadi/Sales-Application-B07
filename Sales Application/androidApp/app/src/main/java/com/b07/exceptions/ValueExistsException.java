package com.b07.exceptions;

public class ValueExistsException extends DatabaseInsertException {

  /**
   * serialID for a value already existing exceptions.
   */
  private static final long serialVersionUID = 240645268328702797L;

  /**
   * Gets the error code
   *
   * @return the error code
   */
  public String getErrorCode() {
    return String.valueOf(serialVersionUID);
  }
}
