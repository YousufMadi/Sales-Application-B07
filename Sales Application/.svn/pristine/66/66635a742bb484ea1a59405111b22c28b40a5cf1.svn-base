package com.b07.database;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class DeserializeSalesDatabase {
  private ObjectInputStream ois;
  private HashMap<Integer, String> roles;
  private List<User> users;
  private HashMap<User, String> userRoles;
  private List<String> passwords;
  private List<AccountImpl> accounts;
  private List<AccountSummary> accSummaries;
  private List<Item> items;
  private Inventory inventory;
  private SalesLog sales;
  private SalesLog itemizedSales;
  private SerializeSalesDatabase dbSerializeSales;
  private DatabaseInserterAndroid dbInsert;
  private DatabaseSelectorAndroid dbSelect;

  public DeserializeSalesDatabase(Context appContext) {
    dbSerializeSales = new SerializeSalesDatabase(appContext);
    dbInsert = new DatabaseInserterAndroid(appContext);
    dbSelect = new DatabaseSelectorAndroid(appContext);
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  public boolean deserialize(Context appContext, String path, Admin admin)
      throws IOException, SQLException, ClassNotFoundException,
      ConnectionFailedException, ValueExistsException, DatabaseInsertException {
    String oldPath = appContext.getFilesDir() + "/database_copy_back.ser";
    dbSerializeSales.serialize(oldPath);

    readSer(path);

    if (checkAdminExistence(admin)) {
      try {
        restoreDatabase(appContext, path);
      } catch (Exception e) {
        e.printStackTrace();
        readSer(oldPath);
        restoreDatabase(appContext, path);
        return false;
      }
    } else {
      return false;
    }

    return true;
  }

  @SuppressWarnings("unchecked")
  private void readSer(String path)
      throws ConstraintViolationException, IOException, SQLException, ClassNotFoundException {
    FileInputStream fis = new FileInputStream(path);
    ois = new ObjectInputStream(fis);

    try {
      roles = (HashMap<Integer, String>) ois.readObject();
      users = (List<User>) ois.readObject();
      userRoles = (HashMap<User, String>) ois.readObject();
      passwords = (List<String>) ois.readObject();
      accounts = (List<AccountImpl>) ois.readObject();
      accSummaries = (List<AccountSummary>) ois.readObject();
      items = (List<Item>) ois.readObject();
      inventory = (Inventory) ois.readObject();
      sales = (SalesLog) ois.readObject();
      itemizedSales = (SalesLog) ois.readObject();
    } finally {
      ois.close();
    }
  }

  private boolean checkAdminExistence(Admin admin) throws SQLException {
    if (admin != null) {
      for (User u : users) {
        if (u.getId() == admin.getId()) {
          return true;
        }
      }
    }
    return false;
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  private void restoreDatabase(Context appContext, String path)
      throws ConnectionFailedException, IOException, SQLException, ConstraintViolationException,
      ValueExistsException, DatabaseInsertException {
    File file = new File(appContext.getFilesDir().getParent() + "/databases/inventorymgmt.db");
    file.delete();

    // Initialize database

    restoreRoles();
    restoreUsers();
    restoreAccounts();
    restoreItems();
    restoreInventory();
    restoreAccountSummaries();
    restoreSales();
    restoreItemizedSales();
  }

  private void restoreRoles()
      throws ConstraintViolationException, ValueExistsException, DatabaseInsertException,
      SQLException {
    for (Integer Id : roles.keySet()) {
      dbInsert.insertRole(roles.get(Id));
    }
  }

  private void restoreUsers()
      throws DatabaseInsertException, SQLException {
    for (int i = 0; i < users.size(); i++) {
      int userId = dbInsert.restoreUser(
          users.get(i).getName(),
          users.get(i).getAge(),
          users.get(i).getAddress(),
          passwords.get(i));

      int roleId = dbSelect.getRoleId(Roles.getRole(userRoles.get(users.get(i))));
      dbInsert.insertUserRole(userId, roleId);
    }
  }

  private void restoreAccounts()
      throws DatabaseInsertException, SQLException {
    for (AccountImpl a : accounts) {
      if (a.getStatus() == 1) {
        dbInsert.insertAccount(a.getUserId(), true);
      } else {
        dbInsert.insertAccount(a.getUserId(), false);
      }
    }
  }

  private void restoreAccountSummaries()
      throws DatabaseInsertException, SQLException {
    for (AccountSummary aSummary : accSummaries) {
      HashMap<Item, Integer> summary = aSummary.getSummary();
      for (Item i : summary.keySet()) {
        dbInsert.insertAccountLine(aSummary.getId(), i.getId(), summary.get(i));
      }
    }
  }

  private void restoreItems()
      throws ConstraintViolationException, ValueExistsException, DatabaseInsertException,
      SQLException {
    for (Item i : items) {
      dbInsert.insertItem(i.getName(), i.getPrice());
    }
  }

  private void restoreInventory() throws ConstraintViolationException,
      DatabaseInsertException, SQLException {
    for (Item i : inventory.getItemMap().keySet()) {
      dbInsert.insertInventory(i.getId(), inventory.getItemMap().get(i));
    }
  }

  private void restoreSales()
      throws ConstraintViolationException, ValueExistsException, DatabaseInsertException,
      SQLException {
    for (int saleId : sales.getSalesLog().keySet()) {
      Sale sale = sales.getSalesLog().get(saleId);
      dbInsert.insertSale(sale.getUser().getId(), sale.getTotalPrice());
    }
  }

  private void restoreItemizedSales() throws ConstraintViolationException,
      ValueExistsException, DatabaseInsertException, SQLException {
    for (int saleId : itemizedSales.getSalesLog().keySet()) {

      Sale sale = itemizedSales.getSalesLog().get(saleId);
      HashMap<Item, Integer> itemMap = itemizedSales.getSalesLog().get(saleId).getItemMap();

      for (Item i : itemMap.keySet()) {
        dbInsert.insertItemizedSale(sale.getId(), i.getId(), itemMap.get(i));
      }
    }
  }
}
