package com.b07.database.helper;

import com.b07.database.DatabaseUpdater;
import com.b07.database.helper.validators.DatabaseUpdateValidator;
import com.b07.exceptions.ConstraintViolationException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

// TODO: Auto-generated Javadoc
/**
 * The Class DatabaseUpdateHelper.
 */
public class DatabaseUpdateHelper extends DatabaseUpdater {
  /**
   * Update role name.
   *
   * @param name the name
   * @param id the id
   * @return true, if successful
   * @throws SQLException when an unexpected sql exception occured
   * @throws ConstraintViolationException when input constraints are not met
   */
  public static boolean updateRoleName(String name, int id)
      throws SQLException, ConstraintViolationException {
    DatabaseUpdateValidator.updateRoleNameValidator(name, id);

    boolean complete = false;
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      complete = DatabaseUpdater.updateRoleName(name, id, connection);
    } finally {
      connection.close();
    }
    return complete;
  }

  /**
   * Update user name.
   *
   * @param name the name
   * @param userId the user id
   * @return true, if successful
   * @throws SQLException when an unexpected sql exception occured
   * @throws ConstraintViolationException when input constraints are not met
   */
  public static boolean updateUserName(String name, int userId)
      throws SQLException, ConstraintViolationException {
    DatabaseUpdateValidator.updateUserNameValidator(name, userId);

    boolean complete = false;
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      complete = DatabaseUpdater.updateUserName(name, userId, connection);
    } finally {
      connection.close();
    }
    return complete;
  }

  /**
   * Update user age.
   *
   * @param age the age
   * @param userId the user id
   * @return true, if successful
   * @throws SQLException when an unexpected sql exception occured
   * @throws ConstraintViolationException when input constraints are not met
   */
  public static boolean updateUserAge(int age, int userId)
      throws SQLException, ConstraintViolationException {
    DatabaseUpdateValidator.updateUserAgeValidator(age, userId);

    boolean complete = false;
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      complete = DatabaseUpdater.updateUserAge(age, userId, connection);
    } finally {
      connection.close();
    }
    return complete;
  }

  /**
   * Update user address.
   *
   * @param address the address
   * @param userId the user id
   * @return true, if successful
   * @throws SQLException when an unexpected sql exception occured
   * @throws ConstraintViolationException when input constraints are not met
   */
  public static boolean updateUserAddress(String address, int userId)
      throws SQLException, ConstraintViolationException {
    DatabaseUpdateValidator.updateUserAddressValidator(address, userId);

    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = false;
    try {
      complete = DatabaseUpdater.updateUserAddress(address, userId, connection);
    } finally {
      connection.close();
    }
    return complete;
  }

  /**
   * Update user role.
   *
   * @param roleId the role id
   * @param userId the user id
   * @return true, if successful
   * @throws SQLException when an unexpected sql exception occured
   * @throws ConstraintViolationException when input constraints are not met
   */
  public static boolean updateUserRole(int roleId, int userId)
      throws SQLException, ConstraintViolationException {
    DatabaseUpdateValidator.updateUserRoleValidator(roleId, userId);

    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = false;
    try {
      complete = DatabaseUpdater.updateUserRole(roleId, userId, connection);
    } finally {
      connection.close();
    }
    return complete;
  }

  /**
   * Update item name.
   *
   * @param name the name
   * @param itemId the item id
   * @return true, if successful
   * @throws SQLException when an unexpected sql exception occured
   * @throws ConstraintViolationException when input constraints are not met
   */
  public static boolean updateItemName(String name, int itemId)
      throws SQLException, ConstraintViolationException {
    DatabaseUpdateValidator.updateItemNameValidator(name, itemId);

    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = false;
    try {
      complete = DatabaseUpdater.updateItemName(name, itemId, connection);
    } finally {
      connection.close();
    }
    return complete;
  }

  /**
   * Update item price.
   *
   * @param price the price
   * @param itemId the item id
   * @return true, if successful
   * @throws SQLException when an unexpected sql exception occured
   * @throws ConstraintViolationException when input constraints are not met
   */
  public static boolean updateItemPrice(BigDecimal price, int itemId)
      throws SQLException, ConstraintViolationException {
    DatabaseUpdateValidator.updateItemPriceValidator(price, itemId);

    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = false;
    try {
      complete = DatabaseUpdater.updateItemPrice(price.setScale(2), itemId, connection);
    } finally {
      connection.close();
    }
    return complete;
  }

  /**
   * Update inventory quantity.
   *
   * @param quantity the quantity
   * @param itemId the item id
   * @return true, if successful
   * @throws SQLException when an unexpected sql exception occured
   * @throws ConstraintViolationException when input constraints are not met
   */
  public static boolean updateInventoryQuantity(int quantity, int itemId)
      throws SQLException, ConstraintViolationException {
    DatabaseUpdateValidator.updateInventoryQuantityValidator(quantity, itemId);

    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = false;
    try {
      complete = DatabaseUpdater.updateInventoryQuantity(quantity, itemId, connection);
    } finally {
      connection.close();
    }
    return complete;
  }

  /**
   * Update account status.
   *
   * @param accountId the account id
   * @param active the active
   * @return true, if successful
   * @throws SQLException when an unexpected sql exception occured
   * @throws ConstraintViolationException when input constraints are not met
   */
  public static boolean updateAccountStatus(int accountId, boolean active)
      throws SQLException, ConstraintViolationException {
    DatabaseUpdateValidator.updateAccountStatusValidator(accountId);

    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = false;
    try {
      complete = DatabaseUpdater.updateAccountStatus(accountId, active, connection);
    } finally {
      connection.close();
    }
    return complete;
  }
}
