package com.b07.users;

/**
 * The Interface Account.
 */
public interface Account {

  /**
   * Get the Id of account.
   *
   * @return the id.
   */
  public int getId();

  /**
   * Set the id of the account.
   *
   * @param id is id to be set to.
   */
  public void setId(int id);

  /**
   * Return the User Id.
   *
   * @return the id.
   */
  public int getUserId();

  /**
   * Set the User Id.
   *
   * @param userId is the user id to set to.
   */
  public void setUserId(int userId);

  /**
   * Gets the status.
   *
   * @return the status
   */
  public int getStatus();

  /**
   * Sets the status.
   *
   * @param status the new status
   */
  public void setStatus(int status);
}
