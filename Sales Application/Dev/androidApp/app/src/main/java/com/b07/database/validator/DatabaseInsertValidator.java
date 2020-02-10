package com.b07.database.validator;

import android.content.Context;

import com.b07.database.DatabaseSelectorAndroid;
import com.b07.exceptions.ConstraintViolationException;
import com.b07.exceptions.ValueExistsException;
import com.b07.inventory.Item;
import com.b07.inventory.ItemTypes;
import com.b07.store.SalesLog;
import com.b07.users.Roles;
import com.b07.users.User;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class DatabaseInsertValidator {
  DatabaseSelectorAndroid dbSelect;

  public DatabaseInsertValidator(Context context) {
    dbSelect = new DatabaseSelectorAndroid(context);
  }

  /**
   * Validates whether role can be inserted
   *
   * @param name the name of the role
   * @throws SQLException                 the SQLException
   * @throws ConstraintViolationException the ConstraintViolationException
   * @throws ValueExistsException         the ValueExistsException
   */
  public void validateRoleInsert(String name)
      throws SQLException, ConstraintViolationException, ValueExistsException {
    List<Integer> roleIds = dbSelect.getRoleIds();
    boolean exist = false;

    // check if role already exists
    for (int id : roleIds) {
      if (name != null && dbSelect.getRoleName(id).equals(name)) {
        exist = true;
      }
    }

    // if a role with name already exists in database
    if (!exist) {
      // check for constraints
      if (!Roles.checkRole(name)) {
        throw new ConstraintViolationException();
      }
    } else {
      throw new ValueExistsException();
    }
  }

  /**
   * Validates whether the new user can be inserted
   *
   * @param name     the name of the user
   * @param age      the age of the user
   * @param address  the address of the user
   * @param password the password for the user
   * @throws ConstraintViolationException the ConstraintViolationException
   */
  public void validateNewUserInsert(String name, int age, String address, String password)
      throws ConstraintViolationException {
    InputValidator.validateUserName(name);
    InputValidator.validateUserAge(age);
    InputValidator.validateUserAddress(address);
    InputValidator.validateUserName(name);
  }

  /**
   * Validates whether user role can be inserted
   *
   * @param userId the user's id
   * @param roleId the id of the role
   * @throws ConstraintViolationException the ConstraintViolationException
   * @throws SQLException                 the SQLException
   */
  public void validateUserRoleInsert(int userId, int roleId)
      throws ConstraintViolationException, SQLException {
    if ((dbSelect.getRoleName(roleId) == null)) {
      throw new ConstraintViolationException();
    }
  }

  /**
   * Validates whether item can be inserted
   *
   * @param name  the item name
   * @param price the item price
   * @throws SQLException                 the SQLException
   * @throws ConstraintViolationException the ConstraintViolationException
   * @throws ValueExistsException         the ValueExistsException
   */
  public void validateItemInsert(String name, BigDecimal price)
      throws SQLException, ConstraintViolationException, ValueExistsException {
    InputValidator.validateItemName(name);
    InputValidator.validateItemPrice(price);

    List<Item> itemIds = dbSelect.getAllItems();
    boolean exist = false;

    // check if item already exists
    for (Item item : itemIds) {
      if (item.getName().equals(name)) {
        exist = true;
      }
    }
    if (exist) {
      throw new ValueExistsException();
    }

    if (!ItemTypes.checkItem(name)) {
      throw new ConstraintViolationException();
    }
  }

  /**
   * Validates whether item can be inserted in inventory
   *
   * @param itemId   the id for the item
   * @param quantity the quantity of the item to be inserted
   * @throws ConstraintViolationException the ConstraintViolationException
   * @throws SQLException                 the SQLException
   */
  public void validateInventoryInsert(int itemId, int quantity)
      throws ConstraintViolationException, SQLException {
    InputValidator.validateItemQuantity(quantity);

    if (dbSelect.getItem(itemId) == null) {
      throw new ConstraintViolationException();
    }
  }

  /**
   * Validates whether sale can be inserted
   *
   * @param userId     the id of the user whose sale it is
   * @param totalPrice the total price of the sale
   * @throws ConstraintViolationException the ConstraintViolationException
   * @throws SQLException                 the SQLException
   */
  public void validateSaleInsert(int userId, BigDecimal totalPrice)
      throws ConstraintViolationException, SQLException {
    InputValidator.validateSaleTotalPrice(totalPrice);
    checkUserExistence(userId);
  }

  /**
   * Validates whether an item can be inserted in itemized sale
   *
   * @param saleId   the id of the sale
   * @param itemId   the id of the item
   * @param quantity the quantity of the item
   * @throws ValueExistsException         the ValueExistsException
   * @throws SQLException                 the SQLException
   * @throws ConstraintViolationException the ConstraintViolationException
   */
  public void validateItemizedSaleInsert(int saleId, int itemId, int quantity)
      throws ValueExistsException, SQLException, ConstraintViolationException {
    InputValidator.validateItemQuantity(quantity);

    boolean saleItemExist = false;

    // need to check unique combo between saleId and itemId
    SalesLog salesLog = dbSelect.getItemizedSales();
    if (salesLog.getSalesLog().get(saleId) != null) {
      Set<Item> items = salesLog.getSalesLog().get(saleId).getItemMap().keySet();
      for (Item i : items) {
        if (i.getId() == itemId) {
          saleItemExist = true;
        }
      }
    }
    if (saleItemExist) {
      throw new ValueExistsException();
    }
  }

  /**
   * Validates whether account can be inserted
   *
   * @param userId the id of the user
   * @throws ConstraintViolationException the ConstraintViolationException
   * @throws SQLException                 the SQLException
   * @throws ValueExistsException         the ValueExistsException
   */
  public void validateAccountInsert(int userId)
      throws ConstraintViolationException, SQLException, ValueExistsException {
    checkUserExistence(userId);

    if (dbSelect.getUserActiveAccounts(userId).size() != 0) {
      throw new ValueExistsException();
    } else if (dbSelect.getUserRole(userId) != Roles.CUSTOMER) {
      throw new ConstraintViolationException();
    }
  }

  /**
   * Validates whether item can be inserted into account line
   *
   * @param accountId the id for the account
   * @param itemId    the id for the item
   * @param quantity  the quantity of the item
   * @throws ConstraintViolationException the ConstraintViolationException
   * @throws SQLException                 the SQLException
   */
  public void validateAccountLineInsert(int accountId, int itemId, int quantity)
      throws ConstraintViolationException, SQLException {
    InputValidator.validateItemQuantity(quantity);

    if (dbSelect.getCustomerIdByAccountId(accountId) == -1
        || dbSelect.getItem(itemId) == null) {
      throw new ConstraintViolationException();
    }
  }

  /**
   * Check whether user exists
   *
   * @param userId the id of the user
   * @throws SQLException                 the SQLException
   * @throws ConstraintViolationException the ConstraintViolationException
   */
  public void checkUserExistence(int userId)
      throws SQLException, ConstraintViolationException {
    User user = dbSelect.getUserDetails(userId);
    if (user == null) {
      throw new ConstraintViolationException();
    }
  }

  /**
   * Check whether item exists
   *
   * @param itemId the id of the item
   * @throws ConstraintViolationException the ConstraintViolationException
   * @throws SQLException                 the SQLException
   */
  public void checkItemExistence(int itemId)
      throws ConstraintViolationException, SQLException {
    if (dbSelect.getItem(itemId) == null) {
      throw new ConstraintViolationException();
    }
  }

  public void checkAccountExistence(int accountId) throws SQLException, ConstraintViolationException {
    if (dbSelect.getAccountDetails(accountId) == null) {
      throw new ConstraintViolationException();
    }
  }
}
