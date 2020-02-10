package com.b07.store;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.b07.database.DatabaseSelectorAndroid;
import com.b07.database.DatabaseUpdaterAndroid;
import com.b07.database.DeserializeSalesDatabase;
import com.b07.database.SerializeSalesDatabase;
import com.b07.exceptions.AuthenticationFailedException;
import com.b07.exceptions.ConnectionFailedException;
import com.b07.exceptions.ConstraintViolationException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.ValueExistsException;
import com.b07.inventory.Inventory;
import com.b07.inventory.Item;
import com.b07.users.Account;
import com.b07.users.Admin;
import com.b07.users.Customer;
import com.b07.users.Employee;
import com.b07.users.Roles;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class AdminInterface implements Serializable {
  // Current Admin
  private Admin currentAdmin;

  /**
   * Instantiates a new admin interface.
   *
   * @param admin the admin
   * @throws SQLException                  the SQL exception
   * @throws AuthenticationFailedException the authentication failed exception
   */
  public AdminInterface(Context appContext, Admin admin)
      throws SQLException, AuthenticationFailedException {
    this.setCurrentAdmin(appContext, admin);
  }

  /**
   * Sets the current admin.
   *
   * @param admin the new current admin
   * @throws SQLException                  the SQL exception
   * @throws AuthenticationFailedException the authentication failed exception
   */
  private void setCurrentAdmin(Context appContext, Admin admin)
      throws SQLException, AuthenticationFailedException {
    if (admin != null && admin.authenticated(appContext, null)) {
      this.currentAdmin = admin;
    } else {
      throw new AuthenticationFailedException();
    }
  }

  /**
   * Promote employee to admin
   *
   * @param employee the employee to promote
   * @return true, if successful, false otherwise
   * @throws SQLException the SQL exception
   */
  public boolean promoteEmployee(Context appContext, Employee employee)
      throws SQLException, AuthenticationFailedException, ConstraintViolationException {
    // if user is authenticated
    if (!currentAdmin.authenticated(appContext, null)) {
      throw new AuthenticationFailedException();
    }
    DatabaseUpdaterAndroid dbUpdater = new DatabaseUpdaterAndroid(appContext);
    DatabaseSelectorAndroid dbSelector = new DatabaseSelectorAndroid(appContext);

    int adminId = dbSelector.getRoleId(Roles.ADMIN);
    return dbUpdater.updateUserRole(adminId, employee.getId());
  }

  /**
   * View historic sale records.
   *
   * @return string with books
   * @throws SQLException when an unexpected sql exception occured
   */
  public String viewBooks(Context appContext) throws SQLException {
    DatabaseSelectorAndroid dbSelect = new DatabaseSelectorAndroid(appContext);
    int customerRoleId = dbSelect.getRoleId(Roles.CUSTOMER);
    List<Integer> customerIds = dbSelect.getUsersByRole(customerRoleId);
    HashMap<Item, Integer> soldItems = new HashMap<>();
    BigDecimal price = new BigDecimal("0.00");
    String books = "";

    for (int customerId : customerIds) {
      Customer customer = (Customer) dbSelect.getUserDetails(customerId);
      List<Sale> sales = dbSelect.getSalesToUser(customerId);

      for (Sale sale : sales) {
        books += "Customer: " + customer.getName() + "\nPurchase Number: " + sale.getId() +
            "\nTotal Purchase Price: $" + sale.getTotalPrice() + "\nItemized Breakdown: \n";
        price = price.add(sale.getTotalPrice());
        Sale itemizedSale = dbSelect.getItemizedSaleById(sale.getId());
        HashMap<Item, Integer> itemMap = itemizedSale.getItemMap();

        for (HashMap.Entry<Item, Integer> entry : itemMap.entrySet()) {
          books += "\t" + entry.getKey().getName() + ": " + entry.getValue() + "\n";
          if (!soldItems.containsKey(entry.getKey())) {
            soldItems.put(entry.getKey(), entry.getValue());
          } else {
            int curQuantity = soldItems.get(entry.getKey());
            soldItems.replace(entry.getKey(), entry.getValue() + curQuantity);
          }
        }
        books += "\n-------------------------------------\n";
      }
    }

    for (HashMap.Entry<Item, Integer> entry : soldItems.entrySet()) {
      books += "\nNumber " + entry.getKey().getName() + " Sold: " + entry.getValue();
    }

    books += "\nTOTAL SALES: $" + price;

    return books;
  }

  /**
   * @return string with inventory
   * @throws SQLException database connection error
   */
  public String viewInventory(Context appContext) throws SQLException {
    DatabaseSelectorAndroid dbSelect = new DatabaseSelectorAndroid(appContext);
    Inventory inventory = dbSelect.getInventory();

    String result = "";
    for (Item item : inventory.getItemMap().keySet()) {
      result += item.getName() + ": " + inventory.getItemMap().get(item) + "\n";
    }
    return result;
  }

  /**
   * get a string of all customer accounts
   *
   * @param appContext context of class
   * @param customerId customer id
   * @param active     if we want the active accounts
   * @return string of all customer accounts for android text view
   */
  public String viewAccounts(Context appContext, int customerId, boolean active) {
    DatabaseSelectorAndroid dbSelect = new DatabaseSelectorAndroid(appContext);
    List<Account> accounts;
    if (active) {
      accounts = dbSelect.getUserActiveAccounts(customerId);
    } else {
      accounts = dbSelect.getUserInactiveAccounts(customerId);
    }

    if (accounts.size() == 0) {
      return "No accounts found";
    }

    String accountsBook = "";
    // Concatenating id
    int iteration = 1;
    for (Account account : accounts) {

      accountsBook += "ID #" + iteration + ": " + account.getId() + "\n";
      iteration++;
    }
    return accountsBook;
  }

  /**
   * Serialize database.
   *
   * @param path the path
   * @throws ConstraintViolationException the constraint violation exception
   * @throws IOException                  Signals that an I/O exception has occurred.
   * @throws SQLException                 the SQL exception
   */
  public void serializeDatabase(Context appContext, String path)
      throws ConstraintViolationException, IOException, SQLException {
    SerializeSalesDatabase dbSerialize = new SerializeSalesDatabase(appContext);
    dbSerialize.serialize(path);
  }

  /**
   * Deserialize database.
   *
   * @param path the path
   * @return true, if successful
   * @throws ClassNotFoundException    the class not found exception
   * @throws ValueExistsException      the value exists exception
   * @throws IOException               Signals that an I/O exception has occurred.
   * @throws SQLException              the SQL exception
   * @throws ConnectionFailedException the connection failed exception
   * @throws DatabaseInsertException   the database insert exception
   */
  @RequiresApi(api = Build.VERSION_CODES.O)
  public boolean deserializeDatabase(Context appContext, String path)
      throws ClassNotFoundException, ValueExistsException, IOException, SQLException,
      ConnectionFailedException, DatabaseInsertException {
    DeserializeSalesDatabase dbDeserialize = new DeserializeSalesDatabase(appContext);
    return dbDeserialize.deserialize(appContext, path, this.currentAdmin);
  }
}
