package com.b07.database.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import com.b07.exceptions.ConnectionFailedException;
import com.b07.exceptions.ConstraintViolationException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.ValueExistsException;
import com.b07.inventory.Inventory;
import com.b07.inventory.Item;
import com.b07.store.Sale;
import com.b07.store.SalesLog;
import com.b07.users.AccountImpl;
import com.b07.users.AccountSummary;
import com.b07.users.Admin;
import com.b07.users.Roles;
import com.b07.users.User;

/**
 * The Class DeserializeSalesDatabase.
 */
public class DeserializeSalesDatabase {

  private static ObjectInputStream ois;
  private static HashMap<Integer, String> roles;
  private static List<User> users;
  private static HashMap<User, String> userRoles;
  private static List<String> passwords;
  private static List<AccountImpl> accounts;
  private static List<AccountSummary> accSummaries;
  private static List<Item> items;
  private static Inventory inventory;
  private static SalesLog sales;
  private static SalesLog itemizedSales;

  /**
   * Deserialize a backedup database and replace current sqlite database with
   *
   * @param path the path of .ser file to deserialize
   * @param admin the admin
   * @return true, if successful
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws SQLException the SQL exception
   * @throws ClassNotFoundException the class not found exception
   * @throws ConnectionFailedException the connection failed exception
   * @throws ValueExistsException the value exists exception
   * @throws DatabaseInsertException the database insert exception
   */
  public static boolean deserialize(String path, Admin admin)
      throws IOException, SQLException, ClassNotFoundException, ConnectionFailedException,
      ValueExistsException, DatabaseInsertException {
    // create backup of current database
    SerializeSalesDatabase.serialize("./database_copy_back.ser");

    DeserializeSalesDatabase.readSer(path);

    if (DeserializeSalesDatabase.checkAdminExistence(admin)) {
      // try restoring database to one specified in path, restore current backup
      // if process fails.
      try {
        DeserializeSalesDatabase.restoreDatabase();
      } catch (Exception e) {
        e.printStackTrace();
        readSer("./database_copy_back.ser");
        DeserializeSalesDatabase.restoreDatabase();
        return false;
      }
    } else {
      return false;
    }

    return true;
  }

  /**
   * Read content in ser file and assign them to class statics
   *
   * @param path the path
   * @throws ConstraintViolationException the constraint violation exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws SQLException the SQL exception
   * @throws ClassNotFoundException the class not found exception
   */
  @SuppressWarnings("unchecked")
  private static void readSer(String path)
      throws ConstraintViolationException, IOException, SQLException, ClassNotFoundException {
    FileInputStream fis = new FileInputStream(path);
    ois = new ObjectInputStream(fis);

    try {
      DeserializeSalesDatabase.roles = (HashMap<Integer, String>) ois.readObject();
      DeserializeSalesDatabase.users = (List<User>) ois.readObject();
      DeserializeSalesDatabase.userRoles = (HashMap<User, String>) ois.readObject();
      DeserializeSalesDatabase.passwords = (List<String>) ois.readObject();
      DeserializeSalesDatabase.accounts = (List<AccountImpl>) ois.readObject();
      DeserializeSalesDatabase.accSummaries = (List<AccountSummary>) ois.readObject();
      DeserializeSalesDatabase.items = (List<Item>) ois.readObject();
      DeserializeSalesDatabase.inventory = (Inventory) ois.readObject();
      DeserializeSalesDatabase.sales = (SalesLog) ois.readObject();
      DeserializeSalesDatabase.itemizedSales = (SalesLog) ois.readObject();
    } finally {
      ois.close();
    }
  }

  /**
   * Check admin existence in backed up database file
   *
   * @param admin the admin
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  private static boolean checkAdminExistence(Admin admin) throws SQLException {
    if (admin != null) {
      for (User u : users) {
        if (u.getId() == admin.getId()) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Restore database.
   *
   * @throws ConnectionFailedException the connection failed exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws SQLException the SQL exception
   * @throws ConstraintViolationException the constraint violation exception
   * @throws ValueExistsException the value exists exception
   * @throws DatabaseInsertException the database insert exception
   */
  private static void restoreDatabase() throws ConnectionFailedException, IOException, SQLException,
      ConstraintViolationException, ValueExistsException, DatabaseInsertException {
    File file = new File("inventorymgmt.db");
    Files.deleteIfExists(file.toPath());

    Connection connection = DatabaseDeserializeHelper.connectOrCreateDataBase();

    try {
      DatabaseDeserializeHelper.initializeDatabase();
    } finally {
      connection.close();
    }

    DeserializeSalesDatabase.restoreRoles();
    DeserializeSalesDatabase.restoreUsers();
    DeserializeSalesDatabase.restoreAccounts();
    DeserializeSalesDatabase.restoreItems();
    DeserializeSalesDatabase.restoreInventory();
    DeserializeSalesDatabase.restoreAccountSummaries();
    DeserializeSalesDatabase.restoreSales();
    DeserializeSalesDatabase.restoreItemizedSales();
  }

  /**
   * Restore roles.
   *
   * @throws ConstraintViolationException the constraint violation exception
   * @throws ValueExistsException the value exists exception
   * @throws DatabaseInsertException the database insert exception
   * @throws SQLException the SQL exception
   */
  private static void restoreRoles() throws ConstraintViolationException, ValueExistsException,
      DatabaseInsertException, SQLException {
    for (Integer Id : roles.keySet()) {
      DatabaseInsertHelper.insertRole(roles.get(Id));
    }
  }

  /**
   * Restore users.
   *
   * @throws DatabaseInsertException the database insert exception
   * @throws SQLException the SQL exception
   */
  private static void restoreUsers() throws DatabaseInsertException, SQLException {
    for (int i = 0; i < users.size(); i++) {
      int userId = DatabaseInsertHelper.restoreUser(users.get(i).getName(), users.get(i).getAge(),
          users.get(i).getAddress(), passwords.get(i));

      int roleId = DatabaseSelectHelper.getRoleId(Roles.getRole(userRoles.get(users.get(i))));
      DatabaseInsertHelper.insertUserRole(userId, roleId);
    }
  }

  /**
   * Restore accounts.
   *
   * @throws DatabaseInsertException the database insert exception
   * @throws SQLException the SQL exception
   */
  private static void restoreAccounts() throws DatabaseInsertException, SQLException {
    for (AccountImpl a : accounts) {
      if (a.getStatus() == 1) {
        DatabaseInsertHelper.insertAccount(a.getUserId(), true);
      } else {
        DatabaseInsertHelper.insertAccount(a.getUserId(), false);
      }
    }
  }

  /**
   * Restore account summaries.
   *
   * @throws DatabaseInsertException the database insert exception
   * @throws SQLException the SQL exception
   */
  private static void restoreAccountSummaries() throws DatabaseInsertException, SQLException {
    for (AccountSummary aSummary : accSummaries) {
      HashMap<Item, Integer> summary = aSummary.getSummary();
      for (Item i : summary.keySet()) {
        DatabaseInsertHelper.insertAccountLine(aSummary.getId(), i.getId(), summary.get(i));
      }
    }
  }

  /**
   * Restore items.
   *
   * @throws ConstraintViolationException the constraint violation exception
   * @throws ValueExistsException the value exists exception
   * @throws DatabaseInsertException the database insert exception
   * @throws SQLException the SQL exception
   */
  private static void restoreItems() throws ConstraintViolationException, ValueExistsException,
      DatabaseInsertException, SQLException {
    for (Item i : items) {
      DatabaseInsertHelper.insertItem(i.getName(), i.getPrice());
    }
  }

  /**
   * Restore inventory.
   *
   * @throws ConstraintViolationException the constraint violation exception
   * @throws DatabaseInsertException the database insert exception
   * @throws SQLException the SQL exception
   */
  private static void restoreInventory()
      throws ConstraintViolationException, DatabaseInsertException, SQLException {
    for (Item i : inventory.getItemMap().keySet()) {
      DatabaseInsertHelper.insertInventory(i.getId(), inventory.getItemMap().get(i));
    }
  }

  /**
   * Restore sales.
   *
   * @throws ConstraintViolationException the constraint violation exception
   * @throws ValueExistsException the value exists exception
   * @throws DatabaseInsertException the database insert exception
   * @throws SQLException the SQL exception
   */
  private static void restoreSales() throws ConstraintViolationException, ValueExistsException,
      DatabaseInsertException, SQLException {
    for (int saleId : sales.getSalesLog().keySet()) {
      Sale sale = sales.getSalesLog().get(saleId);
      DatabaseInsertHelper.insertSale(sale.getUser().getId(), sale.getTotalPrice());
    }
  }

  /**
   * Restore itemized sales.
   *
   * @throws ConstraintViolationException the constraint violation exception
   * @throws ValueExistsException the value exists exception
   * @throws DatabaseInsertException the database insert exception
   * @throws SQLException the SQL exception
   */
  private static void restoreItemizedSales() throws ConstraintViolationException,
      ValueExistsException, DatabaseInsertException, SQLException {
    for (int saleId : itemizedSales.getSalesLog().keySet()) {

      Sale sale = itemizedSales.getSalesLog().get(saleId);
      HashMap<Item, Integer> itemMap = itemizedSales.getSalesLog().get(saleId).getItemMap();

      for (Item i : itemMap.keySet()) {
        DatabaseInsertHelper.insertItemizedSale(sale.getId(), i.getId(), itemMap.get(i));
      }
    }
  }
}
