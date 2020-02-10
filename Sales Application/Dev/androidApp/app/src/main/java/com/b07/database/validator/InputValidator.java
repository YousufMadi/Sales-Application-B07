package com.b07.database.validator;

import com.b07.exceptions.ConstraintViolationException;

import java.math.BigDecimal;

public class InputValidator {
  /**
   * Validates the user name
   *
   * @param name the name of the user
   * @throws ConstraintViolationException the ConstraintViolationException
   */
  public static void validateUserName(String name) throws ConstraintViolationException {
    if (name == null || name.compareTo("") == 0 || !name.matches("[a-zA-Z]+$")) {
      throw new ConstraintViolationException();
    }
  }

  /**
   * Validates the user age
   *
   * @param age the age of the user
   * @throws ConstraintViolationException the ConstraintViolationException
   */
  public static void validateUserAge(int age) throws ConstraintViolationException {
    if (age <= 0) {
      throw new ConstraintViolationException();
    }
  }

  /**
   * Validates the user address
   *
   * @param address the address of the user
   * @throws ConstraintViolationException the ConstraintViolationException
   */
  public static void validateUserAddress(String address) throws ConstraintViolationException {
    if (address == null || address.length() > 100 || address.compareTo("") == 0) {
      throw new ConstraintViolationException();
    }
  }

  /**
   * Validates the user password
   *
   * @param password the password for the user
   * @throws ConstraintViolationException the ConstraintViolationException
   */
  public static void validateUserPassword(String password) throws ConstraintViolationException {
    if (password == null || password.compareTo("") == 0) {
      throw new ConstraintViolationException();
    }
  }

  /**
   * Validates the role name
   *
   * @param name the name of the role
   * @throws ConstraintViolationException the ConstraintViolationException
   */
  public static void validateRoleName(String name) throws ConstraintViolationException {
    if (name == null || name.compareTo("") == 0) {
      throw new ConstraintViolationException();
    }
  }

  /**
   * Validates the item name
   *
   * @param name the name of the item
   * @throws ConstraintViolationException the ConstraintViolationException
   */
  public static void validateItemName(String name) throws ConstraintViolationException {
    if (name.length() >= 64 || name.compareTo("") == 0) {
      throw new ConstraintViolationException();
    }
  }

  /**
   * Validates the price of the item
   *
   * @param price the price of the item
   * @throws ConstraintViolationException the ConstraintViolationException
   */
  public static void validateItemPrice(BigDecimal price) throws ConstraintViolationException {
    if (price.compareTo(BigDecimal.ZERO) <= 0) {
      throw new ConstraintViolationException();
    }
  }

  /**
   * Validates the quantity of the item
   *
   * @param quantity the quantity of the item
   * @throws ConstraintViolationException the ConstraintViolationException
   */
  public static void validateItemQuantity(int quantity) throws ConstraintViolationException {
    if (quantity < 0) {
      throw new ConstraintViolationException();
    }
  }

  /**
   * Validates the total sale price
   *
   * @param totalPrice the total sale price
   * @throws ConstraintViolationException the ConstraintViolationException
   */
  public static void validateSaleTotalPrice(BigDecimal totalPrice)
      throws ConstraintViolationException {
    if (totalPrice.compareTo(BigDecimal.ZERO) <= 0) {
      throw new ConstraintViolationException();
    }
  }
}
