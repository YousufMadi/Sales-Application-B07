package com.b07.exceptions;

public class ConstraintViolationException extends DatabaseInsertException {

  /**
   * Serial Id for contraint violation exceptions
   */
  private static final long serialVersionUID = -3627188904945396317L;

  /**
   * Gets the error code
   *
   * @return the error code
   */
  public String getErrorCode() {
    return String.valueOf(serialVersionUID);
  }
}
