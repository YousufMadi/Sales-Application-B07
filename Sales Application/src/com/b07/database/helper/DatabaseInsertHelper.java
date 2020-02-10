package com.b07.database.helper;

import com.b07.database.DatabaseInserter;
import com.b07.database.helper.DatabaseDriverHelper;
import com.b07.database.helper.validators.DatabaseInsertValidator;
import com.b07.exceptions.ConstraintViolationException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.ValueExistsException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * The Class DatabaseInsertHelper.
 */
public class DatabaseInsertHelper extends DatabaseInserter {

  /**
   * Insert role into database.
   *
   * @param name the name of role
   * @return the roleId
   * @throws DatabaseInsertException when insert unexpectedly fails
   * @throws SQLException when an unexpected sql exception occured
   * @throws ConstraintViolationException when input constraints are not met
   * @throws ValueExistsException when value already exists in database
   */
  public static int insertRole(String name) throws DatabaseInsertException, SQLException,
      ConstraintViolationException, ValueExistsException {
    DatabaseInsertValidator.validateRoleInsert(name);

    int roleId = 0;
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      roleId = DatabaseInserter.insertRole(name, connection);
    } finally {
      connection.close();
    }
    return roleId;
  }

  /**
   * Insert new user.
   *
   * @param name the name
   * @param age the age
   * @param address the address
   * @param password the password
   * @return the userId
   * @throws DatabaseInsertException when insert unexpectedly fails
   * @throws SQLException when an unexpected sql exception occured
   * @throws ConstraintViolationException when input constraints are not met
   */
  public static int insertNewUser(String name, int age, String address, String password)
      throws DatabaseInsertException, SQLException, ConstraintViolationException {
    DatabaseInsertValidator.validateNewUserInsert(name, age, address, password);

    int userId = 0;
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      userId = DatabaseInserter.insertNewUser(name, age, address, password, connection);
    } finally {
      connection.close();
    }
    return userId;
  }

  /**
   * Restore user.
   *
   * @param name the name
   * @param age the age
   * @param address the address
   * @param password the password
   * @return the int
   * @throws DatabaseInsertException when insert unexpectedly fails
   * @throws SQLException when an unexpected sql exception occured
   * @throws ConstraintViolationException when input constraints are not met
   */
  protected static int restoreUser(String name, int age, String address, String password)
      throws DatabaseInsertException, SQLException {
    int userId = 0;
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      userId = DatabaseDeserializeHelper.restoreUser(name, age, address, password, connection);
    } finally {
      connection.close();
    }
    return userId;
  }

  /**
   * Insert user role.
   *
   * @param userId the user id
   * @param roleId the role id
   * @return the userRoleId
   * @throws DatabaseInsertException when insert unexpectedly fails
   * @throws SQLException when an unexpected sql exception occured
   * @throws ValueExistsException when value already exists in database
   */
  public static int insertUserRole(int userId, int roleId)
      throws DatabaseInsertException, SQLException, ValueExistsException {
    DatabaseInsertValidator.validateUserRoleInsert(userId, roleId);

    int userRoleId = 0;
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      userRoleId = DatabaseInserter.insertUserRole(userId, roleId, connection);
    } finally {
      connection.close();
    }
    return userRoleId;
  }

  /**
   * Insert item.
   *
   * @param name the name of item
   * @param price the price of item
   * @return the itemId
   * @throws DatabaseInsertException when insert unexpectedly fails
   * @throws SQLException when an unexpected sql exception occured
   * @throws ConstraintViolationException when input constraints are not met
   * @throws ValueExistsException when value already exists in database
   */
  public static int insertItem(String name, BigDecimal price) throws DatabaseInsertException,
      SQLException, ConstraintViolationException, ValueExistsException {
    DatabaseInsertValidator.validateItemInsert(name, price);

    int itemId = 0;
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      itemId = DatabaseInserter.insertItem(name, price.setScale(2), connection);
    } finally {
      connection.close();
    }
    return itemId;
  }

  /**
   * Insert inventory.
   *
   * @param itemId the item id
   * @param quantity the quantity of the item
   * @return the inventoryId
   * @throws DatabaseInsertException when insert unexpectedly fails
   * @throws SQLException when an unexpected sql exception occured
   * @throws ConstraintViolationException when input constraints are not met
   */
  public static int insertInventory(int itemId, int quantity)
      throws DatabaseInsertException, SQLException, ConstraintViolationException {
    DatabaseInsertValidator.validateInventoryInsert(itemId, quantity);

    int inventoryId = 0;
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      inventoryId = DatabaseInserter.insertInventory(itemId, quantity, connection);
    } finally {
      connection.close();
    }
    return inventoryId;
  }

  /**
   * Insert sale.
   *
   * @param userId the user id
   * @param totalPrice the total price of sale
   * @return the saleId
   * @throws DatabaseInsertException when insert unexpectedly fails
   * @throws SQLException when an unexpected sql exception occured
   * @throws ConstraintViolationException when input constraints are not met
   * @throws ValueExistsException when value already exists in database
   */
  public static int insertSale(int userId, BigDecimal totalPrice) throws DatabaseInsertException,
      SQLException, ConstraintViolationException, ValueExistsException {
    DatabaseInsertValidator.validateSaleInsert(userId, totalPrice);

    int saleId = 0;
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      saleId = DatabaseInserter.insertSale(userId, totalPrice.setScale(2), connection);
    } finally {
      connection.close();
    }
    return saleId;
  }

  /**
   * Insert itemized sale.
   *
   * @param saleId the sale id
   * @param itemId the item id
   * @param quantity the quantity
   * @return the itemizedSaleId
   * @throws DatabaseInsertException when insert unexpectedly fails
   * @throws SQLException when an unexpected sql exception occured
   * @throws ConstraintViolationException when input constraints are not met
   * @throws ValueExistsException when value already exists in database
   */
  public static int insertItemizedSale(int saleId, int itemId, int quantity)
      throws DatabaseInsertException, SQLException, ConstraintViolationException,
      ValueExistsException {
    DatabaseInsertValidator.validateItemizedSaleInsert(saleId, itemId, quantity);

    int itemizedId = 0;
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      itemizedId = DatabaseInserter.insertItemizedSale(saleId, itemId, quantity, connection);
    } finally {
      connection.close();
    }
    return itemizedId;
  }

  /**
   * Insert account.
   *
   * @param userId the user id
   * @return the accountId
   * @throws DatabaseInsertException when insert unexpectedly fails
   * @throws SQLException when an unexpected sql exception occured
   */
  @Deprecated
  public static int insertAccount(int userId) throws DatabaseInsertException, SQLException {
    DatabaseInsertValidator.validateAccountInsert(userId);

    int accountId = 0;
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      accountId = DatabaseInserter.insertAccount(userId, connection);
    } finally {
      connection.close();
    }
    return accountId;
  }

  /**
   * Insert account.
   *
   * @param userId the user id
   * @param active true if desire account to be active
   * @return the accountId
   * @throws DatabaseInsertException when insert unexpectedly fails
   * @throws SQLException when an unexpected sql exception occured
   */
  public static int insertAccount(int userId, boolean active)
      throws DatabaseInsertException, SQLException {
    DatabaseInsertValidator.validateAccountInsert(userId);

    int accountId = 0;
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      accountId = DatabaseInserter.insertAccount(userId, active, connection);
    } finally {
      connection.close();
    }
    return accountId;
  }

  /**
   * Insert account summary.
   *
   * @param accountId account id
   * @param itemId item id
   * @param quantity the quantity
   * @return the accountLineId
   * @throws DatabaseInsertException when insert unexpectedly fails
   * @throws SQLException when an unexpected sql exception occured
   */
  public static int insertAccountLine(int accountId, int itemId, int quantity)
      throws DatabaseInsertException, SQLException {
    DatabaseInsertValidator.validateAccountLineInsert(accountId, itemId, quantity);

    int accountLineId = 0;
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    try {
      accountLineId = DatabaseInserter.insertAccountLine(accountId, itemId, quantity, connection);
    } finally {
      connection.close();
    }
    return accountLineId;
  }
}
