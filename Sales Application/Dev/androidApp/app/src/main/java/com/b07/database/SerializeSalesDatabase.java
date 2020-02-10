package com.b07.database;

import android.content.Context;

import com.b07.exceptions.ConstraintViolationException;
import com.b07.inventory.Inventory;
import com.b07.inventory.Item;
import com.b07.inventory.ItemImpl;
import com.b07.store.SalesLog;
import com.b07.users.Account;
import com.b07.users.AccountImpl;
import com.b07.users.AccountSummary;
import com.b07.users.User;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SerializeSalesDatabase {
  private ObjectOutputStream oos;
  private Context appContext;
  private DatabaseSelectorAndroid dbSelect;

  public SerializeSalesDatabase(Context appContext) {
    this.appContext = appContext;
    dbSelect = new DatabaseSelectorAndroid(appContext);
  }

  public void serialize(String path)
      throws IOException, SQLException, ConstraintViolationException {
    FileOutputStream fos = new FileOutputStream(path);
    oos = new ObjectOutputStream(fos);

    try {
      List<Integer> roleIds = dbSelect.getRoleIds();
      List<User> users = dbSelect.getUsersDetails();
      List<Item> items = dbSelect.getAllItems();
      Inventory inventory = dbSelect.getInventory();
      SalesLog sales = dbSelect.getSales();
      SalesLog itemizedSales = dbSelect.getItemizedSales();

      serializeRole(roleIds);
      serializeUsers(users);
      serializeItems(items);
      serializeInventory(inventory);
      serializeSales(sales);
      serializeItemizedSales(itemizedSales);
    } finally {
      oos.close();
    }
  }

  private void serializeRole(List<Integer> roleIds) throws SQLException, IOException {
    HashMap<Integer, String> roles = new HashMap<Integer, String>();

    Collections.sort(roleIds);

    for (int roleId : roleIds) {
      roles.put(roleId, dbSelect.getRoleName(roleId));
    }

    oos.writeObject(roles);
  }

  private void serializeUsers(List<User> users)
      throws SQLException, IOException, ConstraintViolationException {
    HashMap<User, String> userRoles = new HashMap<User, String>();
    List<String> passwords = new ArrayList<String>();
    List<AccountImpl> accounts = new ArrayList<AccountImpl>();
    List<AccountSummary> accSummaries = new ArrayList<AccountSummary>();

    for (User user : users) {
      passwords.add(dbSelect.getPassword(user.getId()));

      for (Account a : dbSelect.getUserActiveAccounts(user.getId())) {
        AccountSummary accSummary = dbSelect.getAccountDetails(a.getId());
        accSummaries.add(accSummary);
        accounts.add((AccountImpl) a);
      }

      for (Account a : dbSelect.getUserInactiveAccounts(user.getId())) {
        AccountSummary accSummary = dbSelect.getAccountDetails(a.getId());
        accSummaries.add(accSummary);
        accounts.add((AccountImpl) a);
      }

      userRoles.put(user, dbSelect.getUserRole(user.getId()).toString());
    }

    Collections.sort(accounts);
    oos.writeObject(users);
    oos.writeObject(userRoles);
    oos.writeObject(passwords);
    oos.writeObject(accounts);
    oos.writeObject(accSummaries);
  }

  private void serializeItems(List<Item> items) throws IOException {
    List<ItemImpl> itemsConc = new ArrayList<ItemImpl>();
    for (Item i : items) {
      itemsConc.add((ItemImpl) i);
    }

    Collections.sort(itemsConc);
    oos.writeObject(itemsConc);
  }

  private void serializeInventory(Inventory inventory)
      throws IOException {
    oos.writeObject(inventory);
  }

  private void serializeSales(SalesLog sales) throws IOException {
    oos.writeObject(sales);
  }

  private void serializeItemizedSales(SalesLog itemizedSales)
      throws IOException {
    oos.writeObject(itemizedSales);
  }
}
