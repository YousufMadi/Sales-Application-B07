package com.b07.database.helper.validators;

import java.math.BigDecimal;
import java.sql.SQLException;
import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.exceptions.ConstraintViolationException;
import com.b07.users.Roles;

/**
 * The Class DatabaseUpdateValidator.
 */
public class DatabaseUpdateValidator {

  /**
   * Validates whether role name can be updated.
   *
   * @param name the name of the role
   * @param roleId the id of the role
   * @throws ConstraintViolationException the ConstraintViolationException
   * @throws SQLException the SQLException
   */
  public static void updateRoleNameValidator(String name, int roleId)
      throws ConstraintViolationException, SQLException {
    InputValidator.validateRoleName(name);

    if (!Roles.checkRole(name) || DatabaseSelectHelper.getRoleName(roleId) == null) {
      throw new ConstraintViolationException();
    }
  }

  /**
   * Validate whether user name can be updated.
   *
   * @param name the name of the user
   * @param userId the id of the user
   * @throws ConstraintViolationException the ConstraintViolationException
   * @throws SQLException the SQLException
   */
  public static void updateUserNameValidator(String name, int userId)
      throws ConstraintViolationException, SQLException {
    InputValidator.validateUserName(name);
    InputValidator.checkUserExistence(userId);
  }

  /**
   * Validates whether user age can be updated.
   *
   * @param age the age of the user
   * @param userId the id of the user
   * @throws ConstraintViolationException the ConstraintViolationException
   * @throws SQLException the SQLException
   */
  public static void updateUserAgeValidator(int age, int userId)
      throws ConstraintViolationException, SQLException {
    InputValidator.validateUserAge(age);
    InputValidator.checkUserExistence(userId);
  }

  /**
   * Validates whether user address can be updated.
   *
   * @param address the address of the user
   * @param userId the id of the user
   * @throws ConstraintViolationException the ConstraintViolationException
   * @throws SQLException the SQLException
   */
  public static void updateUserAddressValidator(String address, int userId)
      throws ConstraintViolationException, SQLException {
    InputValidator.validateUserAddress(address);
    InputValidator.checkUserExistence(userId);
  }

  /**
   * Validates whether user role can be updated.
   *
   * @param roleId the id of the role
   * @param userId the id of the user
   * @throws ConstraintViolationException the ConstraintViolationException
   * @throws SQLException the SQLException
   */
  public static void updateUserRoleValidator(int roleId, int userId)
      throws ConstraintViolationException, SQLException {
    InputValidator.checkUserExistence(userId);

    if (DatabaseSelectHelper.getRoleName(roleId) == null) {
      throw new ConstraintViolationException();
    }
  }

  /**
   * Validates whether item name can be updated.
   *
   * @param name the name of the item
   * @param itemId the id of the item
   * @throws ConstraintViolationException the ConstraintViolationException
   * @throws SQLException the SQLException
   */
  public static void updateItemNameValidator(String name, int itemId)
      throws ConstraintViolationException, SQLException {
    InputValidator.validateItemName(name);

    if (DatabaseSelectHelper.getItem(itemId) == null) {
      throw new ConstraintViolationException();
    }
  }

  /**
   * Validates whether item price can be updated.
   *
   * @param price the price of the item
   * @param itemId the id of the item
   * @throws ConstraintViolationException the ConstraintViolationException
   * @throws SQLException the SQLException
   */
  public static void updateItemPriceValidator(BigDecimal price, int itemId)
      throws ConstraintViolationException, SQLException {
    InputValidator.validateItemPrice(price);

    if (DatabaseSelectHelper.getItem(itemId) == null) {
      throw new ConstraintViolationException();
    }
  }

  /**
   * Validates whether inventory quantity can be updated.
   *
   * @param quantity the quantity of the item
   * @param itemId the id of the item
   * @throws ConstraintViolationException the ConstraintViolationException
   * @throws SQLException the SQLException
   */
  public static void updateInventoryQuantityValidator(int quantity, int itemId)
      throws ConstraintViolationException, SQLException {
    InputValidator.validateItemQuantity(quantity);

    if (DatabaseSelectHelper.getItem(itemId) == null) {
      throw new ConstraintViolationException();
    }
  }

  /**
   * Update account status validator.
   *
   * @param quantity the quantity
   * @param itemId the item id
   * @throws ConstraintViolationException the constraint violation exception
   * @throws SQLException the SQL exception
   */
  public static void updateAccountStatusValidator(int accountId)
      throws ConstraintViolationException, SQLException {
    InputValidator.validateAccountExistence(accountId);
  }

}
