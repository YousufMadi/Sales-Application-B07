package com.b07.database.helper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import com.b07.exceptions.ConstraintViolationException;
import com.b07.inventory.Inventory;
import com.b07.inventory.Item;
import com.b07.inventory.ItemImpl;
import com.b07.store.SalesLog;
import com.b07.users.Account;
import com.b07.users.AccountImpl;
import com.b07.users.AccountSummary;
import com.b07.users.User;

/**
 * The Class SerializeSalesDatabase.
 */
public class SerializeSalesDatabase {

  private static ObjectOutputStream oos;

  /**
   * Serialize current sqllite database to specified path
   *
   * @param path the path
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws SQLException the SQL exception
   * @throws ConstraintViolationException the constraint violation exception
   */
  public static void serialize(String path)
      throws IOException, SQLException, ConstraintViolationException {
    FileOutputStream fos = new FileOutputStream(path);
    SerializeSalesDatabase.oos = new ObjectOutputStream(fos);

    try {
      List<Integer> roleIds = DatabaseSelectHelper.getRoleIds();
      List<User> users = DatabaseSelectHelper.getUsersDetails();
      List<Item> items = DatabaseSelectHelper.getAllItems();
      Inventory inventory = DatabaseSelectHelper.getInventory();
      SalesLog sales = DatabaseSelectHelper.getSales();
      SalesLog itemizedSales = DatabaseSelectHelper.getItemizedSales();

      SerializeSalesDatabase.serializeRole(roleIds);
      SerializeSalesDatabase.serializeUsers(users);
      SerializeSalesDatabase.serializeItems(items);
      SerializeSalesDatabase.serializeInventory(inventory);
      SerializeSalesDatabase.serializeSales(sales);
      SerializeSalesDatabase.serializeItemizedSales(itemizedSales);
    } finally {
      oos.close();
    }
  }

  /**
   * Serialize role.
   *
   * @param roleIds the role ids
   * @throws SQLException the SQL exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private static void serializeRole(List<Integer> roleIds) throws SQLException, IOException {
    HashMap<Integer, String> roles = new HashMap<Integer, String>();

    Collections.sort(roleIds);

    for (int roleId : roleIds) {
      roles.put(roleId, DatabaseSelectHelper.getRoleName(roleId));
    }

    oos.writeObject(roles);
  }

  /**
   * Serialize users.
   *
   * @param users the users
   * @throws SQLException the SQL exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ConstraintViolationException the constraint violation exception
   */
  private static void serializeUsers(List<User> users)
      throws SQLException, IOException, ConstraintViolationException {
    HashMap<User, String> userRoles = new HashMap<User, String>();
    List<String> passwords = new ArrayList<String>();
    List<AccountImpl> accounts = new ArrayList<AccountImpl>();
    List<AccountSummary> accSummaries = new ArrayList<AccountSummary>();

    for (User user : users) {
      passwords.add(DatabaseSelectHelper.getPassword(user.getId()));

      for (Account a : DatabaseSelectHelper.getUserActiveAccounts(user.getId())) {
        AccountSummary accSummary = DatabaseSelectHelper.getAccountDetails(a.getId());
        accSummaries.add(accSummary);
        accounts.add((AccountImpl) a);
      }

      for (Account a : DatabaseSelectHelper.getUserInActiveAccounts(user.getId())) {
        AccountSummary accSummary = DatabaseSelectHelper.getAccountDetails(a.getId());
        accSummaries.add(accSummary);
        accounts.add((AccountImpl) a);
      }

      userRoles.put(user, DatabaseSelectHelper.getUserRole(user.getId()).toString());
    }

    Collections.sort(accounts);
    oos.writeObject(users);
    oos.writeObject(userRoles);
    oos.writeObject(passwords);
    oos.writeObject(accounts);
    oos.writeObject(accSummaries);
  }

  /**
   * Serialize items.
   *
   * @param items the items
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private static void serializeItems(List<Item> items) throws IOException {
    List<ItemImpl> itemsConc = new ArrayList<ItemImpl>();
    for (Item i : items) {
      itemsConc.add((ItemImpl) i);
    }

    Collections.sort(itemsConc);
    oos.writeObject(itemsConc);
  }

  /**
   * Serialize inventory.
   *
   * @param inventory the inventory
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private static void serializeInventory(Inventory inventory) throws IOException {
    oos.writeObject(inventory);
  }

  /**
   * Serialize sales.
   *
   * @param sales the sales
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private static void serializeSales(SalesLog sales) throws IOException {
    oos.writeObject(sales);
  }

  /**
   * Serialize itemized sales.
   *
   * @param itemizedSales the itemized sales
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private static void serializeItemizedSales(SalesLog itemizedSales) throws IOException {
    oos.writeObject(itemizedSales);
  }
}
