package com.b07.database.helper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.b07.database.DatabaseSelector;
import com.b07.database.helper.validators.InputValidator;
import com.b07.exceptions.ConstraintViolationException;
import com.b07.inventory.Inventory;
import com.b07.inventory.InventoryImpl;
import com.b07.inventory.Item;
import com.b07.inventory.ItemImpl;
import com.b07.inventory.ItemTypes;
import com.b07.store.Sale;
import com.b07.store.SaleImpl;
import com.b07.store.SalesLog;
import com.b07.store.SalesLogImpl;
import com.b07.users.Account;
import com.b07.users.AccountImpl;
import com.b07.users.AccountSummary;
import com.b07.users.AccountSummaryImpl;
import com.b07.users.Admin;
import com.b07.users.Customer;
import com.b07.users.Employee;
import com.b07.users.Roles;
import com.b07.users.User;

/**
 * The Class DatabaseSelectHelper.
 */
public class DatabaseSelectHelper extends DatabaseSelector {
  /**
   * Gets the role ids. InputValidator.checkUserExistence
   * 
   * @return the roleIds
   * @throws SQLException when an unexpected sql exception occured
   */
  public static List<Integer> getRoleIds() throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getRoles(connection);
    List<Integer> ids = new ArrayList<>();

    try {
      while (results.next()) {
        ids.add(results.getInt("ID"));
      }
    } finally {
      connection.close();
      results.close();
    }

    return ids;
  }

  /**
   * Gets the role id.
   *
   * @param name the name
   * @return the role id if sucessful, zero otherwise
   * @throws SQLException when an unexpected sql exception occured
   */
  public static int getRoleId(Roles name) throws SQLException {
    List<Integer> roleIds = getRoleIds();
    for (int i : roleIds) {
      String role = getRoleName(i);
      if (role.equals(name.toString())) {
        return i;
      }
    }
    return 0;
  }

  /**
   * Gets the role name.
   *
   * @param roleId the role id
   * @return the role name if sucessful, emptyString otherwise
   * @throws SQLException when an unexpected sql exception occured
   */
  public static String getRoleName(int roleId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    List<Integer> ids = getRoleIds();
    String role = null;

    try {
      if (ids.contains(roleId)) {
        role = DatabaseSelector.getRole(roleId, connection);
      }
    } finally {
      connection.close();
    }

    return role;
  }

  /**
   * Gets the user role id.
   *
   * @param userId the user id
   * @return the userRoleId if it exists
   * @throws SQLException when an unexpected sql exception occured
   * @throws ConstraintViolationException when input constraints are not met
   */
  public static int getUserRoleId(int userId) throws SQLException, ConstraintViolationException {
    InputValidator.checkUserExistence(userId);

    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    int roleId = DatabaseSelector.getUserRole(userId, connection);
    connection.close();
    return roleId;
  }

  /**
   * Gets the users by role.
   *
   * @param roleId the role id
   * @return the userIds if it exists
   * @throws SQLException when an unexpected sql exception occured
   */
  public static List<Integer> getUsersByRole(int roleId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getUsersByRole(roleId, connection);
    List<Integer> userIds = new ArrayList<>();

    try {
      while (results.next()) {
        userIds.add(results.getInt("USERID"));
      }
    } finally {
      results.close();
      connection.close();
    }

    return userIds;
  }

  /**
   * Get the Roles of userId.
   *
   * @param userId the user to get Roles for
   * @return respective Roles of userId
   * @throws SQLException when an unexpected sql exception occured
   */
  public static Roles getUserRole(int userId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    String roleName = null;

    try {
      int roleId = DatabaseSelectHelper.getUserRole(userId, connection);
      roleName = getRoleName(roleId);
    } finally {
      connection.close();
    }

    return Roles.getRole(roleName);
  }

  /**
   * Creates the user.
   *
   * @param id the id
   * @param name the name
   * @param age the age
   * @param address the address
   * @return the user if successful, null otherwise
   * @throws SQLException when an unexpected sql exception occured
   */
  private static User createUser(int id, String name, int age, String address) throws SQLException {
    User user = null;
    Roles role = getUserRole(id);
    if (role != null) {
      if (role.equals(Roles.ADMIN)) {
        user = new Admin(id, name, age, address);
      } else if (role.equals(Roles.EMPLOYEE)) {
        user = new Employee(id, name, age, address);
      } else if (role.equals(Roles.CUSTOMER)) {
        user = new Customer(id, name, age, address);
      }
    }
    return user;
  }

  /**
   * Gets the users details.
   *
   * @return list of users
   * @throws SQLException when an unexpected sql exception occured
   */
  public static List<User> getUsersDetails() throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getUsersDetails(connection);
    List<User> users = new ArrayList<>();

    try {
      while (results.next()) {
        User user = createUser(results.getInt("ID"), results.getString("NAME"),
            results.getInt("AGE"), results.getString("ADDRESS"));
        users.add(user);
      }
    } finally {
      results.close();
      connection.close();
    }

    return users;
  }

  /**
   * Get the user details.
   *
   * @param userId the userId
   * @return User object if userId exists, null otherwise
   * @throws SQLException when an unexpected sql exception occured
   */
  public static User getUserDetails(int userId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getUserDetails(userId, connection);
    User user = null;

    try {
      while (results.next()) {
        user = createUser(results.getInt("ID"), results.getString("NAME"), results.getInt("AGE"),
            results.getString("ADDRESS"));
      }
    } catch (SQLException e) {
      connection.close();
      return null;
    } finally {
      connection.close();
    }
    results.close();

    return user;
  }

  /**
   * Gets the password.
   *
   * @param userId the user id
   * @return the hashed password
   * @throws SQLException when an unexpected sql exception occured
   * @throws ConstraintViolationException when input constraints are not met
   */
  public static String getPassword(int userId) throws SQLException, ConstraintViolationException {
    InputValidator.checkUserExistence(userId);

    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    String password = DatabaseSelector.getPassword(userId, connection);
    connection.close();
    return password;
  }

  /**
   * Creates the item.
   *
   * @param id the id
   * @param name the name
   * @param price the price
   * @return the new created item
   * @throws SQLException when an unexpected sql exception occured
   */
  private static Item createItem(int id, String name, BigDecimal price) throws SQLException {
    Item newItem = null;
    ItemTypes item = ItemTypes.getItemType(name);

    if (item != null) {
      newItem = new ItemImpl(id, name, price);
    }

    return newItem;
  }

  /**
   * Gets the all items.
   *
   * @return all the item if successful, null otherwise
   * @throws SQLException when an unexpected sql exception occured
   */
  public static List<Item> getAllItems() throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getAllItems(connection);
    List<Item> items = new ArrayList<>();

    try {
      while (results.next()) {
        Item item = createItem(results.getInt("ID"), results.getString("NAME"),
            new BigDecimal(results.getString("PRICE")));
        items.add(item);
      }
    } finally {
      results.close();
      connection.close();
    }

    return items;
  }

  /**
   * Gets the item.
   *
   * @param itemId the item id
   * @return the item if successful, null otherwise
   * @throws SQLException when an unexpected sql exception occured
   */
  public static Item getItem(int itemId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getItem(itemId, connection);
    Item item = null;

    try {
      while (results.next()) {
        item = createItem(results.getInt("ID"), results.getString("NAME"),
            new BigDecimal(results.getString("PRICE")));
      }
    } finally {
      results.close();
      connection.close();
    }
    return item;
  }

  /**
   * Gets the inventory.
   *
   * @return the inventory if successful, null otherwise
   * @throws SQLException when an unexpected sql exception occured
   */
  public static Inventory getInventory() throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getInventory(connection);
    HashMap<Item, Integer> inventory = new HashMap<Item, Integer>();

    try {
      while (results.next()) {
        Item item = getItem(results.getInt("ITEMID"));
        if (item != null) {
          inventory.put(item, results.getInt("QUANTITY"));
        }
      }
    } finally {
      results.close();
      connection.close();
    }

    return new InventoryImpl(inventory, inventory.size());
  }

  /**
   * Gets the inventory quantity.
   *
   * @param itemId the item id
   * @return the inventory quantity if successful, null otherwise
   * @throws SQLException when an unexpected sql exception occured
   */
  public static int getInventoryQuantity(int itemId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    int quantity = -1;
    try {
      if (DatabaseSelectHelper.getItem(itemId) != null) {
        quantity = DatabaseSelector.getInventoryQuantity(itemId, connection);
      }
    } finally {
      connection.close();
    }

    return quantity;
  }

  /**
   * Gets the sales.
   *
   * @return the saleslog if successful, null otherwise
   * @throws SQLException when an unexpected sql exception occured
   */
  public static SalesLog getSales() throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getSales(connection);
    HashMap<Integer, Sale> salesLog = new HashMap<Integer, Sale>();

    try {
      while (results.next()) {
        Sale sale =
            new SaleImpl(results.getInt("ID")).setUser(getUserDetails(results.getInt("USERID")))
                .setTotalPrice(new BigDecimal(results.getString("TOTALPRICE")));
        salesLog.put(sale.getId(), sale);
      }
    } finally {
      results.close();
      connection.close();
    }

    return new SalesLogImpl(salesLog);
  }

  /**
   * Gets the sale by id.
   *
   * @param saleId the sale id
   * @return the sale if successful, null otherwise
   * @throws SQLException when an unexpected sql exception occured
   */
  public static Sale getSaleById(int saleId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getSaleById(saleId, connection);
    Sale sale = null;

    try {
      while (results.next()) {
        sale = new SaleImpl(results.getInt("ID")).setUser(getUserDetails(results.getInt("USERID")))
            .setTotalPrice(new BigDecimal(results.getString("TOTALPRICE")));
      }
    } finally {
      results.close();
      connection.close();
    }

    return sale;
  }

  /**
   * Gets the sales to user.
   *
   * @param userId the user id
   * @return the list of sales if successful, null otherwise
   * @throws SQLException when an unexpected sql exception occured
   */
  public static List<Sale> getSalesToUser(int userId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelectHelper.getSalesToUser(userId, connection);
    List<Sale> sales = new ArrayList<Sale>();

    try {
      while (results.next()) {
        Sale sale =
            new SaleImpl(results.getInt("ID")).setUser(getUserDetails(results.getInt("USERID")))
                .setTotalPrice(new BigDecimal(results.getString("TOTALPRICE")));
        sales.add(sale);
      }
    } finally {
      results.close();
      connection.close();
    }

    return sales;
  }

  /**
   * Gets the itemized sale by id.
   *
   * @param saleId the sale id
   * @return the sale if successful, null otherwise
   * @throws SQLException when an unexpected sql exception occured
   */
  public static Sale getItemizedSaleById(int saleId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getItemizedSaleById(saleId, connection);
    HashMap<Item, Integer> saleItems = new HashMap<Item, Integer>();
    Sale salesImpl = null;

    try {
      while (results.next()) {
        saleItems.put(getItem(results.getInt("ITEMID")), results.getInt("QUANTITY"));
      }
      salesImpl = new SaleImpl(results.getInt("ID")).setItemMap(saleItems);
    } finally {
      results.close();
      connection.close();
    }

    return salesImpl;
  }

  /**
   * Gets the itemized sales.
   *
   * @return the saleslog if successful
   * @throws SQLException when an unexpected sql exception occured
   */
  public static SalesLog getItemizedSales() throws SQLException {
    HashMap<Integer, Sale> resSalesLog = new HashMap<Integer, Sale>();

    SalesLog sales = DatabaseSelectHelper.getSales();
    for (int saleId : sales.getSalesLog().keySet()) {
      resSalesLog.put(saleId, DatabaseSelectHelper.getItemizedSaleById(saleId));
    }

    return new SalesLogImpl(resSalesLog);
  }

  /**
   * Gets the user accounts.
   *
   * @param userId the user id
   * @return the list of accounts if successful
   * @throws SQLException when an unexpected sql exception occured
   */
  public static List<Account> getUserAccounts(int userId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getUserAccounts(userId, connection);
    List<Account> accounts = new ArrayList<>();

    try {
      while (results.next()) {
        Account account = new AccountImpl(results.getInt("ID"), userId);
        accounts.add(account);
      }
    } finally {
      results.close();
      connection.close();
    }
    return accounts;
  }

  /**
   * Gets the customer id by account id.
   *
   * @param accountId the account id
   * @return the customer id by account id
   * @throws SQLException when an unexpected sql exception occured
   */
  public static int getCustomerIdByAccountId(int accountId) throws SQLException {
    int customerRoleId = getRoleId(Roles.CUSTOMER);
    List<Integer> customers = getUsersByRole(customerRoleId);
    for (int customerId : customers) {
      List<Account> customerAccounts = getUserAccounts(customerId);
      for (Account account : customerAccounts) {
        int customerAccountId = account.getId();
        if (customerAccountId == accountId) {
          return account.getUserId();
        }
      }
    }
    return -1;
  }

  /**
   * Gets the account details.
   *
   * @param accountId the account id
   * @return the accountsummary if successful
   * @throws SQLException when an unexpected sql exception occured
   */
  public static AccountSummary getAccountDetails(int accountId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getAccountDetails(accountId, connection);
    HashMap<Item, Integer> summary = new HashMap<Item, Integer>();
    AccountSummary accountSummaries = null;

    try {
      while (results.next()) {
        Item item = DatabaseSelectHelper.getItem(results.getInt("ITEMID"));
        summary.put(item, results.getInt("QUANTITY"));
      }
      accountSummaries = new AccountSummaryImpl(accountId, summary);

    } finally {
      results.close();
      connection.close();
    }

    return accountSummaries;
  }

  /**
   * Gets the user active accounts.
   *
   * @param userId the user id
   * @return the user active accounts
   * @throws SQLException when an unexpected sql exception occured
   */
  public static List<Account> getUserActiveAccounts(int userId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getUserActiveAccounts(userId, connection);
    List<Account> accounts = new ArrayList<Account>();

    try {
      while (results.next()) {
        Account account = new AccountImpl(results.getInt("Id"), userId, 1);
        accounts.add(account);
      }
    } finally {
      results.close();
      connection.close();
    }

    return accounts;
  }

  /**
   * Gets the user Inactive accounts.
   *
   * @param userId the user id
   * @return the user active accounts
   * @throws SQLException when an unexpected sql exception occured
   */
  public static List<Account> getUserInActiveAccounts(int userId) throws SQLException {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getUserInactiveAccounts(userId, connection);
    List<Account> accounts = new ArrayList<Account>();

    try {
      while (results.next()) {
        Account account = new AccountImpl(results.getInt("Id"), userId, 0);
        accounts.add(account);
      }
    } finally {
      results.close();
      connection.close();
    }

    return accounts;
  }
}
