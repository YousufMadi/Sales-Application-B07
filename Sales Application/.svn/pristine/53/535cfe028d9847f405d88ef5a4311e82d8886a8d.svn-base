package com.b07.users;

import android.content.Context;

import com.b07.database.DatabaseSelectorAndroid;
import com.b07.exceptions.ConstraintViolationException;
import com.b07.security.PasswordHelpers;

import java.io.Serializable;
import java.sql.SQLException;

/**
 * The Class User.
 *
 * @author Junchao Chen
 */
public abstract class User implements Serializable {
  /**
   * Serial Id for User object
   */
  private static final long serialVersionUID = -5752124381755801207L;

  /**
   * The id.
   */
  private int id;

  /**
   * The name.
   */
  private String name;

  /**
   * The age.
   */
  private int age;

  /**
   * The address.
   */
  private String address;

  /**
   * The role id.
   */
  private int roleId;

  /**
   * The authenticated.
   */
  private boolean authenticated = false;

  public User(int id, String name, int age, String address) {
    this.setId(id);
    this.setName(name);
    this.setAge(age);
    this.setAddress(address);
  }

  /**
   * Instantiates a new Customer.
   *
   * @param id            the id
   * @param name          the name
   * @param age           the age
   * @param address       the address
   * @param authenticated the authenticated
   */
  public User(int id, String name, int age, String address, boolean authenticated) {
    this.setId(id);
    this.setName(name);
    this.setAge(age);
    this.setAddress(address);
    this.overrideAuthentication(authenticated);
  }

  /**
   * Gets the id.
   *
   * @return the id
   */
  public int getId() {
    return this.id;
  }

  /**
   * Sets the id.
   *
   * @param id the new id
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Sets the name.
   *
   * @param name the new name
   */
  public void setName(String name) {
    if (name != null) {
      this.name = name;
    }
  }

  /**
   * Gets the age.
   *
   * @return the age
   */
  public int getAge() {
    return this.age;
  }

  /**
   * Sets the age.
   *
   * @param age the new age
   */
  public void setAge(int age) {
    this.age = age;
  }

  /**
   * Gets the address.
   *
   * @return the address
   */
  public String getAddress() {
    return this.address;
  }

  /**
   * Sets the address.
   *
   * @param address the new address
   */
  public void setAddress(String address) {
    if (address != null) {
      this.address = address;
    }
  }

  /**
   * Gets the roleId.
   *
   * @return the roleId
   */
  public int getRoleId() {
    return this.roleId;
  }

  /**
   * Override authentication.
   *
   * @param auth the auth
   */
  private void overrideAuthentication(boolean auth) {
    if (address != null) {
      this.authenticated = auth;
    }
  }

  /**
   * @param appContext appContext
   * @param password   user password
   * @return if user is authenticated
   * @throws SQLException
   */
  public final boolean authenticated(Context appContext, String password) throws SQLException {
    if (password != null && !this.authenticated) {
      try {
        DatabaseSelectorAndroid dbSelect = new DatabaseSelectorAndroid(appContext);
        String dbPassword = dbSelect.getPassword(this.id);
        if (dbPassword != null) {
          authenticated = PasswordHelpers.comparePassword(dbPassword
              , password);
        }
      } catch (ConstraintViolationException e) {
        return false;
      }
    }
    return authenticated;
  }
}
