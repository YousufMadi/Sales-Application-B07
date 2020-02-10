package com.b07.database.validator;

import android.content.Context;

import com.b07.database.DatabaseSelectorAndroid;
import com.b07.exceptions.ConstraintViolationException;
import com.b07.users.Roles;

import java.math.BigDecimal;
import java.sql.SQLException;

public class DatabaseUpdateValidator {
  private DatabaseSelectorAndroid dbSelect;
  private DatabaseInsertValidator dbValidate;

  public DatabaseUpdateValidator(Context context) {
    dbSelect = new DatabaseSelectorAndroid(context);
    dbValidate = new DatabaseInsertValidator(context
    );
  }

  /**
   * Validates whether role name can be updated
   *
   * @param name   the name of the role
   * @param roleId the id of the role
   * @throws ConstraintViolationException the ConstraintViolationException
   * @throws SQLException                 the SQLException
   */
  public void updateRoleNameValidator(String name, int roleId)
      throws ConstraintViolationException, SQLException {
    InputValidator.validateRoleName(name);

    if (!Roles.checkRole(name) || dbSelect.getRoleName(roleId) == null) {
      throw new ConstraintViolationException();
    }
  }

  /**
   * Validate whether user name can be updated
   *
   * @param name   the name of the user
   * @param userId the id of the user
   * @throws ConstraintViolationException the ConstraintViolationException
   * @throws SQLException                 the SQLException
   */
  public void updateUserNameValidator(String name, int userId)
      throws ConstraintViolationException, SQLException {
    InputValidator.validateUserName(name);
    dbValidate.checkUserExistence(userId);
  }

  /**
   * Validates whether user age can be updated
   *
   * @param age    the age of the user
   * @param userId the id of the user
   * @throws ConstraintViolationException the ConstraintViolationException
   * @throws SQLException                 the SQLException
   */
  public void updateUserAgeValidator(int age, int userId)
      throws ConstraintViolationException, SQLException {
    InputValidator.validateUserAge(age);
    dbValidate.checkUserExistence(userId);
  }

  /**
   * Validates whether user address can be updated
   *
   * @param address the address of the user
   * @param userId  the id of the user
   * @throws ConstraintViolationException the ConstraintViolationException
   * @throws SQLException                 the SQLException
   */
  public void updateUserAddressValidator(String address, int userId)
      throws ConstraintViolationException, SQLException {
    InputValidator.validateUserAddress(address);
    dbValidate.checkUserExistence(userId);
  }

  /**
   * Validates whether user role can be updated
   *
   * @param roleId the id of the role
   * @param userId the id of the user
   * @throws ConstraintViolationException the ConstraintViolationException
   * @throws SQLException                 the SQLException
   */
  public void updateUserRoleValidator(int roleId, int userId)
      throws ConstraintViolationException, SQLException {
    dbValidate.checkUserExistence(userId);

    if (dbSelect.getRoleName(roleId) == null) {
      throw new ConstraintViolationException();
    }
  }

  /**
   * Validates whether item name can be updated
   *
   * @param name   the name of the item
   * @param itemId the id of the item
   * @throws ConstraintViolationException the ConstraintViolationException
   * @throws SQLException                 the SQLException
   */
  public void updateItemNameValidator(String name, int itemId)
      throws ConstraintViolationException, SQLException {
    InputValidator.validateItemName(name);

    if (dbSelect.getItem(itemId) == null) {
      throw new ConstraintViolationException();
    }
  }

  /**
   * Validates whether item price can be updated
   *
   * @param price  the price of the item
   * @param itemId the id of the item
   * @throws ConstraintViolationException the ConstraintViolationException
   * @throws SQLException                 the SQLException
   */
  public void updateItemPriceValidator(BigDecimal price, int itemId)
      throws ConstraintViolationException, SQLException {
    InputValidator.validateItemPrice(price);

    if (dbSelect.getItem(itemId) == null) {
      throw new ConstraintViolationException();
    }
  }

  /**
   * Validates whether inventory quantity can be updated
   *
   * @param quantity the quantity of the item
   * @param itemId   the id of the item
   * @throws ConstraintViolationException the ConstraintViolationException
   * @throws SQLException                 the SQLException
   */
  public void updateInventoryQuantityValidator(int quantity, int itemId)
      throws ConstraintViolationException, SQLException {
    InputValidator.validateItemQuantity(quantity);

    if (dbSelect.getItem(itemId) == null) {
      throw new ConstraintViolationException();
    }
  }

  public void updateAccountStatusValidator(int accountId) throws SQLException, ConstraintViolationException {
    dbValidate.checkAccountExistence(accountId);
  }
}
