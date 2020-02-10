package com.b07.database;

import android.content.Context;
import android.database.Cursor;

import com.b07.database.validator.DatabaseInsertValidator;
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

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseSelectorAndroid {
  private DatabaseDriverAndroid db;
  private Context appContext;

  public DatabaseSelectorAndroid(Context context) {
    db = new DatabaseDriverAndroid(context);
    this.appContext = context;
  }

  public List<Integer> getRoleIds() throws SQLException {
    Cursor cursor = db.getRoles();
    List<Integer> ids = new ArrayList<>();

    try {
      while (cursor.moveToNext()) {
        ids.add(cursor.getInt(cursor.getColumnIndex("ID")));
      }
    } finally {
      db.close();
      cursor.close();
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
  public int getRoleId(Roles name) throws SQLException {
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
  public String getRoleName(int roleId) throws SQLException {
    List<Integer> ids = getRoleIds();
    String role = null;


    try {
      if (ids.contains(roleId)) {
        role = db.getRole(roleId);
      }
    } finally {
      db.close();
    }

    return role;
  }

  /**
   * Gets the user role id.
   *
   * @param userId the user id
   * @return the userRoleId if it exists
   * @throws SQLException                 when an unexpected sql exception occured
   * @throws ConstraintViolationException when input constraints are not met
   */
  public int getUserRoleId(int userId) throws SQLException, ConstraintViolationException {
    DatabaseInsertValidator dbValidate = new DatabaseInsertValidator(appContext);
    dbValidate.checkUserExistence(userId);

    int roleId = db.getUserRole(userId);
    db.close();
    return roleId;
  }

  /**
   * Gets the users by role.
   *
   * @param roleId the role id
   * @return the userIds if it exists
   * @throws SQLException when an unexpected sql exception occured
   */
  public List<Integer> getUsersByRole(int roleId) throws SQLException {
    Cursor cursor = db.getUsersByRole(roleId);
    List<Integer> userIds = new ArrayList<>();

    try {
      while (cursor.moveToNext()) {
        userIds.add(cursor.getInt(cursor.getColumnIndex("USERID")));
      }
    } finally {
      cursor.close();
      db.close();
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
  public Roles getUserRole(int userId) throws SQLException {
    String roleName = null;

    try {
      int roleId = db.getUserRole(userId);
      roleName = getRoleName(roleId);
    } finally {
      db.close();
    }

    return Roles.getRole(roleName);
  }

  /**
   * Creates the user.
   *
   * @param id      the id
   * @param name    the name
   * @param age     the age
   * @param address the address
   * @return the user if successful, null otherwise
   * @throws SQLException when an unexpected sql exception occured
   */
  private User createUser(int id, String name, int age, String address) throws SQLException {
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
  public List<User> getUsersDetails() throws SQLException {
    Cursor cursor = db.getUsersDetails();
    List<User> users = new ArrayList<>();

    try {
      while (cursor.moveToNext()) {
        User user = createUser(cursor.getInt(cursor.getColumnIndex("ID")),
            cursor.getString(cursor.getColumnIndex("NAME")),
            cursor.getInt(cursor.getColumnIndex("AGE")),
            cursor.getString(cursor.getColumnIndex("ADDRESS")));
        users.add(user);
      }
    } finally {
      cursor.close();
      db.close();
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
  public User getUserDetails(int userId) throws SQLException {
    Cursor cursor = db.getUserDetails(userId);
    User user = null;

    try {
      while (cursor.moveToNext()) {
        user = createUser(cursor.getInt(cursor.getColumnIndex("ID")),
            cursor.getString(cursor.getColumnIndex("NAME")),
            cursor.getInt(cursor.getColumnIndex("AGE")),
            cursor.getString(cursor.getColumnIndex("ADDRESS")));
      }
    } catch (SQLException e) {
      db.close();
      return null;
    } finally {
      db.close();
    }
    cursor.close();

    return user;
  }

  /**
   * Gets the password.
   *
   * @param userId the user id
   * @return the hashed password
   * @throws SQLException                 when an unexpected sql exception occured
   * @throws ConstraintViolationException when input constraints are not met
   */
  public String getPassword(int userId) throws SQLException, ConstraintViolationException {
    DatabaseInsertValidator dbValidate = new DatabaseInsertValidator(appContext);
    dbValidate.checkUserExistence(userId);

    String password = db.getPassword(userId);
    db.close();
    return password;
  }

  /**
   * Creates the item.
   *
   * @param id    the id
   * @param name  the name
   * @param price the price
   * @return the new created item
   * @throws SQLException when an unexpected sql exception occured
   */
  private Item createItem(int id, String name, BigDecimal price) throws SQLException {
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
  public List<Item> getAllItems() throws SQLException {
    Cursor cursor = db.getAllItems();
    List<Item> items = new ArrayList<>();

    try {
      while (cursor.moveToNext()) {
        Item item = createItem(cursor.getInt(cursor.getColumnIndex("ID")),
            cursor.getString(cursor.getColumnIndex("NAME")),
            new BigDecimal(cursor.getString(cursor.getColumnIndex("PRICE"))));
        items.add(item);
      }
    } finally {
      cursor.close();
      db.close();
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
  public Item getItem(int itemId) throws SQLException {
    Cursor cursor = db.getItem(itemId);
    Item item = null;

    try {
      while (cursor.moveToNext()) {
        item = createItem(cursor.getInt(cursor.getColumnIndex("ID")),
            cursor.getString(cursor.getColumnIndex("NAME")),
            new BigDecimal(cursor.getString(cursor.getColumnIndex("PRICE"))));
      }
    } finally {
      cursor.close();
      db.close();
    }
    return item;
  }

  /**
   * Gets the inventory.
   *
   * @return the inventory if successful, null otherwise
   * @throws SQLException when an unexpected sql exception occured
   */
  public Inventory getInventory() throws SQLException {
    Cursor cursor = db.getInventory();
    HashMap<Item, Integer> inventory = new HashMap<Item, Integer>();

    try {
      while (cursor.moveToNext()) {
        Item item = getItem(cursor.getInt(cursor.getColumnIndex("ITEMID")));
        if (item != null) {
          inventory.put(item, cursor.getInt(cursor.getColumnIndex("QUANTITY")));
        }
      }
    } finally {
      cursor.close();
      db.close();
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
  public int getInventoryQuantity(int itemId) throws SQLException {
    int quantity = -1;
    try {
      if (db.getItem(itemId) != null) {
        quantity = db.getInventoryQuantity(itemId);
      }
    } finally {
      db.close();
    }

    return quantity;
  }

  /**
   * Gets the sales.
   *
   * @return the saleslog if successful, null otherwise
   * @throws SQLException when an unexpected sql exception occured
   */
  public SalesLog getSales() throws SQLException {
    Cursor cursor = db.getSales();
    HashMap<Integer, Sale> salesLog = new HashMap<Integer, Sale>();

    try {
      while (cursor.moveToNext()) {
        Sale sale = new SaleImpl(cursor.getInt(cursor.getColumnIndex("ID"))).
            setUser(getUserDetails(cursor.getInt(cursor.getColumnIndex("USERID")))).
            setTotalPrice(new BigDecimal(cursor.getString(cursor.getColumnIndex("TOTALPRICE"))));
        salesLog.put(sale.getId(), sale);
      }
    } finally {
      cursor.close();
      db.close();
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
  public Sale getSaleById(int saleId) throws SQLException {
    Cursor cursor = db.getSaleById(saleId);
    Sale sale = null;

    try {
      while (cursor.moveToNext()) {
        sale = new SaleImpl(cursor.getInt(cursor.getColumnIndex("ID"))).
            setUser(getUserDetails(cursor.getInt(cursor.getColumnIndex("USERID")))).
            setTotalPrice(new BigDecimal(cursor.getString(cursor.getColumnIndex("TOTALPRICE"))));
      }
    } finally {
      cursor.close();
      db.close();
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
  public List<Sale> getSalesToUser(int userId) throws SQLException {
    Cursor cursor = db.getSalesToUser(userId);
    List<Sale> sales = new ArrayList<Sale>();

    try {
      while (cursor.moveToNext()) {
        Sale sale = new SaleImpl(cursor.getInt(cursor.getColumnIndex("ID"))).
            setUser(getUserDetails(cursor.getInt(cursor.getColumnIndex("USERID")))).
            setTotalPrice(new BigDecimal(cursor.getString(cursor.getColumnIndex("TOTALPRICE"))));
        sales.add(sale);
      }
    } finally {
      cursor.close();
      db.close();
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
  public Sale getItemizedSaleById(int saleId) throws SQLException {
    Cursor cursor = db.getItemizedSaleById(saleId);
    HashMap<Item, Integer> saleItems = new HashMap<Item, Integer>();
    Sale salesImpl = null;

    try {
      while (cursor.moveToNext()) {
        saleItems.put(getItem(cursor.getInt(cursor.getColumnIndex("ITEMID"))),
            cursor.getInt(cursor.getColumnIndex("QUANTITY")));
      }
      salesImpl = new SaleImpl(saleId).setItemMap(saleItems);
    } finally {
      cursor.close();
      db.close();
    }

    return salesImpl;
  }

  /**
   * Gets the itemized sales.
   *
   * @return the saleslog if successful
   * @throws SQLException when an unexpected sql exception occured
   */
  public SalesLog getItemizedSales() throws SQLException {
    Cursor cursor = db.getItemizedSales();
    HashMap<Integer, Sale> resSalesLog = new HashMap<Integer, Sale>();

    try {
      while (cursor.moveToNext()) {
        HashMap<Item, Integer> saleItems = new HashMap<Item, Integer>();
        saleItems.put(getItem(cursor.getInt(cursor.getColumnIndex("ITEMID"))),
            cursor.getInt(cursor.getColumnIndex("QUANTITY")));
        Sale sale = new SaleImpl(cursor.getInt(cursor.getColumnIndex("SALEID"))).setItemMap(saleItems);
        resSalesLog.put(sale.getId(), sale);
      }
    } finally {
      cursor.close();
      db.close();
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
  public List<Account> getUserAccounts(int userId) throws SQLException {
    Cursor cursor = db.getUserAccounts(userId);
    List<Account> accounts = new ArrayList<>();

    try {
      while (cursor.moveToNext()) {
        Account account = new AccountImpl(cursor.getInt(cursor.getColumnIndex("ID")),
            userId);
        accounts.add(account);
      }
    } finally {
      cursor.close();
      db.close();
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
  public int getCustomerIdByAccountId(int accountId) throws SQLException {
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
  public AccountSummary getAccountDetails(int accountId) throws SQLException {
    Cursor cursor = db.getAccountDetails(accountId);
    HashMap<Item, Integer> summary = new HashMap<Item, Integer>();
    AccountSummary accountSummaries = null;

    try {
      while (cursor.moveToNext()) {
        Item item = getItem(cursor.getInt(cursor.getColumnIndex("ITEMID")));
        summary.put(item, cursor.getInt(cursor.getColumnIndex("QUANTITY")));
      }
      accountSummaries = new AccountSummaryImpl(accountId, summary);

    } finally {
      cursor.close();
      db.close();
    }

    return accountSummaries;
  }

  public List<Account> getUserActiveAccounts(int userId) {
    Cursor cursor = db.getUserActiveAccounts(userId);
    List<Account> accounts = new ArrayList<>();

    try {
      while (cursor.moveToNext()) {
        Account account = new AccountImpl(cursor.getInt(cursor.getColumnIndex("ID")),
            userId);
        accounts.add(account);
      }
    } finally {
      cursor.close();
      db.close();
    }
    return accounts;
  }

  public List<Account> getUserInactiveAccounts(int userId) {
    Cursor cursor = db.getUserInactiveAccounts(userId);
    List<Account> accounts = new ArrayList<>();

    try {
      while (cursor.moveToNext()) {
        Account account = new AccountImpl(cursor.getInt(cursor.getColumnIndex("ID")),
            userId);
        accounts.add(account);
      }
    } finally {
      cursor.close();
      db.close();
    }
    return accounts;
  }
}