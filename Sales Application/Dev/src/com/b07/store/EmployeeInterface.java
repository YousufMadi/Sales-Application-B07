package com.b07.store;

import java.sql.SQLException;
import com.b07.database.helper.DatabaseInsertHelper;
import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.database.helper.DatabaseUpdateHelper;
import com.b07.database.helper.validators.InputValidator;
import com.b07.exceptions.AuthenticationFailedException;
import com.b07.exceptions.ConstraintViolationException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.ValueExistsException;
import com.b07.inventory.Inventory;
import com.b07.inventory.Item;
import com.b07.users.Employee;
import com.b07.users.Roles;

/**
 * The Class EmployeeInterface.
 */
public class EmployeeInterface {
  private Employee currentEmployee;
  private Inventory inventory;

  /**
   * Instantiates a new employee interface.
   *
   * @param employee the employee
   * @param inventory the inventory
   * @throws SQLException when an unexpected sql exception occured
   * @throws AuthenticationFailedException when failed to authenticate user
   */
  public EmployeeInterface(Employee employee, Inventory inventory)
      throws SQLException, AuthenticationFailedException {
    this.setCurrentEmployee(employee);
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
   * @throws SQLException when an unexpected sql exception occured
   * @throws AuthenticationFailedException when failed to authenticate user
   */
  public void setCurrentEmployee(Employee employee)
      throws SQLException, AuthenticationFailedException {
    if (employee != null && employee.authenticated(null)) {
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
  public boolean hasCurrentEmployee() {
    return (currentEmployee != null);
  }

  /**
   * Restock inventory.
   *
   * @param item the item
   * @param quantity the quantity
   * @return true, if successful
   * @throws SQLException when an unexpected sql exception occured
   * @throws ConstraintViolationException when input constraints are not met
   */
  public boolean restockInventory(Item item, int quantity)
      throws SQLException, ConstraintViolationException {
    boolean result = false;

    if (item == null) {
      return false;
    }
    InputValidator.checkItemExistence(item.getId());

    if (hasCurrentEmployee()) {
      int currentQuantity = DatabaseSelectHelper.getInventoryQuantity(item.getId());
      result =
          DatabaseUpdateHelper.updateInventoryQuantity(quantity + currentQuantity, item.getId());

      if (result) {
        this.inventory = DatabaseSelectHelper.getInventory();
      }
    }

    return result;
  }

  /**
   * Creates the customer.
   *
   * @param name the name
   * @param age the age
   * @param address the address
   * @param password the password
   * @return the int
   * @throws ConstraintViolationException when input constraints are not met
   * @throws ValueExistsException when value already exists in database
   * @throws DatabaseInsertException when insert unexpectedly fails
   * @throws SQLException when an unexpected sql exception occured
   */
  public int createCustomer(String name, int age, String address, String password)
      throws ConstraintViolationException, ValueExistsException, DatabaseInsertException,
      SQLException {
    int userId = 0;

    if (hasCurrentEmployee()) {
      userId = DatabaseInsertHelper.insertNewUser(name, age, address, password);
      int roleId = DatabaseSelectHelper.getRoleId(Roles.CUSTOMER);
      DatabaseInsertHelper.insertUserRole(userId, roleId);
    }

    return userId;
  }

  /**
   * Creates the employee.
   *
   * @param name the name
   * @param age the age
   * @param address the address
   * @param password the password
   * @return the int
   * @throws ConstraintViolationException when input constraints are not met
   * @throws ValueExistsException when value already exists in database
   * @throws DatabaseInsertException when insert unexpectedly fails
   * @throws SQLException when an unexpected sql exception occured
   */
  public int createEmployee(String name, int age, String address, String password)
      throws ConstraintViolationException, ValueExistsException, DatabaseInsertException,
      SQLException {
    int userId = 0;

    if (hasCurrentEmployee()) {
      userId = DatabaseInsertHelper.insertNewUser(name, age, address, password);
      int roleId = DatabaseSelectHelper.getRoleId(Roles.EMPLOYEE);
      DatabaseInsertHelper.insertUserRole(userId, roleId);
    }

    return userId;
  }


  /**
   * Creates the account.
   *
   * @param userId the user id
   * @return the accountId
   * @throws DatabaseInsertException when insert unexpectedly fails
   * @throws SQLException when an unexpected sql exception occured
   */
  public int createAccount(int userId) throws DatabaseInsertException, SQLException {
    int accountId = 0;

    if (hasCurrentEmployee()) {
      accountId = DatabaseInsertHelper.insertAccount(userId, true);
    }

    return accountId;
  }
}
