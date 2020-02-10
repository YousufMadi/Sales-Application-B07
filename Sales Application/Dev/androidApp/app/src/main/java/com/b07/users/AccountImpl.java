package com.b07.users;

import java.io.Serializable;

public class AccountImpl implements Account, Serializable, Comparable<AccountImpl> {

  /**
   * Serial Id for Account Objects
   */
  private static final long serialVersionUID = 6894417088927807962L;
  private int id;
  private int userId;
  private int status = -1;

  public AccountImpl(int id, int userId) {
    this.setId(id);
    this.setUserId(userId);
  }

  public AccountImpl(int id, int userId, int status) {
    this.setId(id);
    this.setUserId(userId);
    this.setStatus(status);
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  @Override
  public int getUserId() {
    return userId;
  }

  @Override
  public void setUserId(int userId) {
    this.userId = userId;
  }

  @Override
  public int getStatus() {
    return status;
  }

  @Override
  public void setStatus(int status) {
    this.status = status;
  }

  @Override
  public int compareTo(AccountImpl o) {
    return Integer.compare(this.id, o.getId());
  }
}
