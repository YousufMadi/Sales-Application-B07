package com.b07.store;

import android.content.Context;

import com.b07.database.DatabaseInserterAndroid;
import com.b07.database.DatabaseSelectorAndroid;
import com.b07.database.DatabaseUpdaterAndroid;
import com.b07.database.validator.DatabaseInsertValidator;
import com.b07.exceptions.AuthenticationFailedException;
import com.b07.exceptions.ConstraintViolationException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.ValueExistsException;
import com.b07.inventory.Inventory;
import com.b07.inventory.Item;
import com.b07.users.Employee;
import com.b07.users.Roles;

import java.io.Serializable;
import java.sql.SQLException;

/**
 * The Class EmployeeInterface.
 */
public class EmployeeInterface implements Serializable {
  private Employee currentEmployee;
  private Inventory inventory;

  /**
   * Instantiates a new employee interface.
   *
   * @param employee  the employee
   * @param inventory the inventory
   * @throws SQLException                  when an unexpected sql exception occurred
   * @throws AuthenticationFailedException when failed to authenticate user
   */
  public EmployeeInterface(Context appContext, Employee employee, Inventory inventory) throws AuthenticationFailedException, SQLException {
    this.setCurrentEmployee(appContext, employee);
    this.inventory = inventory;
  }

  /**
   * Instantiates a new employee interface.
   *
   * @param inventory the inventory
   */
  public EmployeeInterface(Inventory inventory) {
    this.inventory = inventory;
  }

  /**
   * Sets the current employee.
   *
   * @param employee the new current employee
   * @throws SQLException                  when an unexpected sql exception occurred
   * @throws AuthenticationFailedException when failed to authenticate user
   */
  private void setCurrentEmployee(Context appContext, Employee employee)
      throws SQLException, AuthenticationFailedException {
    if (employee != null && employee.authenticated(appContext, null)) {
      this.currentEmployee = employee;
    } else {
      throw new AuthenticationFailedException();
    }
  }

  /**
   * Checks for current employee.
   *
   * @return true, if successful
   */
  private boolean hasCurrentEmployee() {
    return (currentEmployee != null);
  }

  /**
   * Restock inventory.
   *
   * @param item     the item
   * @param quantity the quantity
   * @return true, if successful
   * @throws ConstraintViolationException when input constraints are not met
   * @throws SQLException                 when an unexpected sql exception occurred
   */
  public boolean restockInventory(Context appContext, Item item, int quantity)
      throws SQLException, ConstraintViolationException {
    DatabaseSelectorAndroid dbSelect = new DatabaseSelectorAndroid(appContext);
    DatabaseUpdaterAndroid dbUpdate = new DatabaseUpdaterAndroid(appContext);
    DatabaseInsertValidator dbInsertVal = new DatabaseInsertValidator(appContext);
    boolean result = false;

    if (item == null) {
      return false;
    }
    dbInsertVal.checkItemExistence(item.getId());

    if (hasCurrentEmployee()) {
      int currentQuantity = dbSelect.getInventoryQuantity(item.getId());
      result =
          dbUpdate.updateInventoryQuantity(quantity + currentQuantity, item.getId());

      if (result) {
        this.inventory = dbSelect.getInventory();
      }
    }

    return result;
  }

  /**
   * Creates the customer.
   *
   * @param name     the name
   * @param age      the age
   * @param address  the address
   * @param password the password
   * @return the int
   * @throws DatabaseInsertException      when insert unexpectedly fails
   * @throws SQLException                 when an unexpected sql exception occured
   * @throws ConstraintViolationException when input constraints are not met
   * @throws ValueExistsException         when value already exists in database
   */
  private int createCustomer(Context appContext, String name, int age, String address, String password)
      throws ConstraintViolationException, DatabaseInsertException,
      SQLException {
    DatabaseInserterAndroid dbInsert = new DatabaseInserterAndroid(appContext);
    DatabaseSelectorAndroid dbSelect = new DatabaseSelectorAndroid(appContext);
    int userId = 0;

    if (hasCurrentEmployee()) {
      userId = dbInsert.insertNewUser(name, age, address, password);
      int roleId = dbSelect.getRoleId(Roles.CUSTOMER);
      dbInsert.insertUserRole(userId, roleId);
    }

    return userId;
  }

  /**
   * Creates the employee.
   *
   * @param name     the name
   * @param age      the age
   * @param address  the address
   * @param password the password
   * @return the int
   * @throws DatabaseInsertException      when insert unexpectedly fails
   * @throws SQLException                 when an unexpected sql exception occured
   * @throws ConstraintViolationException when input constraints are not met
   * @throws ValueExistsException         when value already exists in database
   */
  private int createEmployee(Context appContext, String name, int age, String address, String password)
      throws ConstraintViolationException, DatabaseInsertException,
      SQLException {
    DatabaseInserterAndroid dbInsert = new DatabaseInserterAndroid(appContext);
    DatabaseSelectorAndroid dbSelect = new DatabaseSelectorAndroid(appContext);
    int userId = 0;

    if (hasCurrentEmployee()) {
      userId = dbInsert.insertNewUser(name, age, address, password);
      int roleId = dbSelect.getRoleId(Roles.EMPLOYEE);
      dbInsert.insertUserRole(userId, roleId);
    }

    return userId;
  }

  /**
   * creates a new account for a user
   *
   * @param appContext context of class
   * @param userId     user id
   * @param active     whether the account is active or not
   * @return id of the new account
   * @throws DatabaseInsertException insert error into database
   * @throws SQLException            database connection error
   */
  public int createAccount(Context appContext, int userId, boolean active) throws DatabaseInsertException, SQLException {
    int accountId = 0;

    if (hasCurrentEmployee()) {
      DatabaseInserterAndroid dbInsert = new DatabaseInserterAndroid(appContext);
      accountId = dbInsert.insertAccount(userId, active);
    }

    return accountId;
  }

  /**
   * creates a new user
   *
   * @param appContext context of class
   * @param role       role for new user
   * @param name       user name
   * @param age        user age
   * @param address    user address
   * @param password   user password
   * @return returns the new user id
   * @throws DatabaseInsertException insert error into the database
   * @throws SQLException            database connection error
   */
  public int makeNewUser(Context appContext, Roles role, String name, int age, String address, String password) throws DatabaseInsertException, SQLException {
    int id;
    if (role.equals(Roles.CUSTOMER)) {
      id = createCustomer(appContext, name, age, address, password);

    } else {
      id = createEmployee(appContext, name, age, address, password);
    }
    return id;
  }
}
