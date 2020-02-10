package com.b07.database;

import android.content.Context;

import com.b07.database.validator.DatabaseUpdateValidator;
import com.b07.exceptions.ConstraintViolationException;

import java.math.BigDecimal;
import java.sql.SQLException;

public class DatabaseUpdaterAndroid {
  private DatabaseDriverAndroid db;
  private DatabaseUpdateValidator dbValidator;

  public DatabaseUpdaterAndroid(Context context) {
    db = new DatabaseDriverAndroid(context);
    dbValidator = new DatabaseUpdateValidator(context);
  }

  public boolean updateRoleName(String name, int id) throws ConstraintViolationException, SQLException {
    dbValidator.updateRoleNameValidator(name, id);

    boolean complete = false;
    try {
      complete = db.updateRoleName(name, id);
    } finally {
      db.close();
    }
    return complete;
  }

  /**
   * Update user name.
   *
   * @param name   the name
   * @param userId the user id
   * @return true, if successful
   */
  public boolean updateUserName(String name, int userId) throws ConstraintViolationException, SQLException {
    dbValidator.updateUserNameValidator(name, userId);

    boolean complete = false;
    try {
      complete = db.updateUserName(name, userId);
    } finally {
      db.close();
    }
    return complete;
  }

  /**
   * Update user age.
   *
   * @param age    the age
   * @param userId the user id
   * @return true, if successful
   */
  public boolean updateUserAge(int age, int userId) throws ConstraintViolationException, SQLException {
    dbValidator.updateUserAgeValidator(age, userId);

    boolean complete = false;

    try {
      complete = db.updateUserAge(age, userId);
    } finally {
      db.close();
    }
    return complete;
  }

  /**
   * Update user address.
   *
   * @param address the address
   * @param userId  the user id
   * @return true, if successful
   */
  public boolean updateUserAddress(String address, int userId) throws ConstraintViolationException, SQLException {
    dbValidator.updateUserAddressValidator(address, userId);

    boolean complete = false;
    try {
      complete = db.updateUserAddress(address, userId);
    } finally {
      db.close();
    }
    return complete;
  }

  /**
   * Update user role.
   *
   * @param roleId the role id
   * @param userId the user id
   * @return true, if successful
   */
  public boolean updateUserRole(int roleId, int userId) throws ConstraintViolationException, SQLException {
    dbValidator.updateUserRoleValidator(roleId, userId);

    boolean complete = false;
    try {
      complete = db.updateUserRole(roleId, userId);
    } finally {
      db.close();
    }
    return complete;
  }

  /**
   * Update item name.
   *
   * @param name   the name
   * @param itemId the item id
   * @return true, if successful
   */
  public boolean updateItemName(String name, int itemId) throws ConstraintViolationException, SQLException {
    dbValidator.updateItemNameValidator(name, itemId);

    boolean complete = false;
    try {
      complete = db.updateItemName(name, itemId);
    } finally {
      db.close();
    }
    return complete;
  }

  /**
   * Update item price.
   *
   * @param price  the price
   * @param itemId the item id
   * @return true, if successful
   */
  public boolean updateItemPrice(BigDecimal price, int itemId) throws ConstraintViolationException, SQLException {
    dbValidator.updateItemPriceValidator(price, itemId);

    boolean complete = false;
    try {
      complete = db.updateItemPrice(price.setScale(2), itemId);
    } finally {
      db.close();
    }
    return complete;
  }

  /**
   * Update inventory quantity.
   *
   * @param quantity the quantity
   * @param itemId   the item id
   * @return true, if successful
   */
  public boolean updateInventoryQuantity(int quantity, int itemId) throws ConstraintViolationException, SQLException {
    dbValidator.updateInventoryQuantityValidator(quantity, itemId);

    boolean complete = false;
    try {
      complete = db.updateInventoryQuantity(quantity, itemId);
    } finally {
      db.close();
    }
    return complete;
  }

  public boolean updateAccountStatus(int accountId, boolean active) throws SQLException, ConstraintViolationException {
    dbValidator.updateAccountStatusValidator(accountId);

    boolean complete = false;
    try {
      complete = db.updateAccountStatus(accountId, active);
    } finally {
      db.close();
    }
    return complete;
  }
}
