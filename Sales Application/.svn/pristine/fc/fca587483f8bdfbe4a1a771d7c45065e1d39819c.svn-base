package com.b07.store;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.b07.database.helper.DatabaseInsertHelper;
import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.database.helper.DatabaseUpdateHelper;
import com.b07.exceptions.AuthenticationFailedException;
import com.b07.exceptions.ConnectionFailedException;
import com.b07.exceptions.ConstraintViolationException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.ValueExistsException;
import com.b07.inventory.Inventory;
import com.b07.inventory.Item;
import com.b07.inventory.ItemTypes;
import com.b07.users.Account;
import com.b07.users.AccountSummary;
import com.b07.users.Admin;
import com.b07.users.Customer;
import com.b07.users.Employee;
import com.b07.users.Roles;
import com.b07.users.User;

/**
 * The Class SalesApplicationUtility.
 * 
 * Interprets user inputs and passing command outputs
 */
public class SalesApplicationController {

  /**
   * MISC Methods
   ***********************************************************************************/
  /**
   * First time set up. Adds one of each Roles Adds one user of each role Adds one of each ItemTypes
   *
   * @throws ConstraintViolationException when input constraints are not met
   * @throws ValueExistsException when value already exists in database
   * @throws DatabaseInsertException when insert unexpectedly fails
   * @throws SQLException when an unexpected sql exception occured
   * @throws ConnectionFailedException failed to connect to database
   */
  protected static void firstTimeSetUp() throws ConstraintViolationException, ValueExistsException,
      DatabaseInsertException, SQLException, ConnectionFailedException {
    int result = 0;

    // attempt to read database structure, should fail if not initialized
    try {
      result = DatabaseSelectHelper.getRoleId(Roles.ADMIN);
    } catch (SQLException e) {
      System.out.println("First Initialization");
      Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
      DatabaseDriverExtender.initialize(connection);
      connection.close();
    }

    // add default roles, users, and items
    if (result == 0) {
      int adminId = DatabaseInsertHelper.insertRole("ADMIN");
      int employeeId = DatabaseInsertHelper.insertRole("EMPLOYEE");
      int customerId = DatabaseInsertHelper.insertRole("CUSTOMER");
      int newUserId =
          DatabaseInsertHelper.insertNewUser("HeadAdmin", 1, "defaultLocation", "default123");
      int newUserId2 =
          DatabaseInsertHelper.insertNewUser("Employee1", 2, "defaultLocation", "default123");
      int newUserId3 =
          DatabaseInsertHelper.insertNewUser("Customer1", 3, "defaultLocation", "default123");
      DatabaseInsertHelper.insertUserRole(newUserId, adminId);
      DatabaseInsertHelper.insertUserRole(newUserId2, employeeId);
      DatabaseInsertHelper.insertUserRole(newUserId3, customerId);
      System.out.println("AdminId: " + newUserId + " Pass: " + "default123");
      System.out.println("EmployeeId: " + newUserId2 + " Pass: " + "default123");
      System.out.println("CustomerId: " + newUserId3 + " Pass: " + "default123");
      ItemTypes[] items = ItemTypes.class.getEnumConstants();
      for (ItemTypes i : items) {
        System.out.println(i.toString());
        int itemId = DatabaseInsertHelper.insertItem(i.toString(), new BigDecimal(15));
        DatabaseInsertHelper.insertInventory(itemId, 10);
      }
    } else {
      System.out.println("Database Already Initialized");
    }
  }

  /**
   * User login GUI.
   *
   * @param role the role
   * @return the loggedin user
   * @throws SQLException when an unexpected sql exception occured
   * @throws IOException when an unexpected I/O exception occured
   * @throws AuthenticationFailedException when failed to authenticate user
   * @throws ConstraintViolationException when input constraints are not met
   */
  protected static User userLogin(Roles role) throws SQLException, IOException,
      AuthenticationFailedException, ConstraintViolationException {
    int userId = 0;
    String userPass = "";
    User workingUser = null;
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    String input;
    int status = -1;

    while (status != 0) {
      if (userId == 0) {
        System.out.println("Please Enter User Id");
      } else {
        System.out.println("Please Enter User Password");
      }

      input = bufferedReader.readLine();
      if (userId == 0) {
        try {
          userId = Integer.parseInt(input);
        } catch (NumberFormatException e) {
          System.out.println("ID should be a positive number");
          continue;
        }
      } else {
        userPass = input;
      }

      if (!userPass.equals("")) {
        workingUser = DatabaseSelectHelper.getUserDetails(userId);
        if (workingUser != null) {
          int roleId = DatabaseSelectHelper.getUserRoleId(workingUser.getId());
          String userRole = DatabaseSelectHelper.getRoleName(roleId);

          if (Roles.getRole(userRole).equals(role) && workingUser.authenticated(userPass)) {
            System.out.println("Authenticated");
          } else {
            System.out.println("Invalid User Credential");
            throw new AuthenticationFailedException();
          }
        } else {
          System.out.println("Invalid User Credential");
          throw new AuthenticationFailedException();
        }
        status = 0;
      }
    }
    return workingUser;
  }


  /**
   * Context Menu GUI.
   *
   * @return the user
   * @throws IOException when an unexpected I/O exception occured
   * @throws SQLException when an unexpected sql exception occured
   * @throws AuthenticationFailedException when failed to authenticate user
   * @throws DatabaseInsertException when insert unexpectedly fails
   */
  protected static void contextMenu()
      throws IOException, SQLException, AuthenticationFailedException, DatabaseInsertException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    String input;
    String menu =
        "\n1 - Employee Login\n" + "2 - Customer Login\n" + "0 - Exit\n" + "Enter Selection:";
    int status = -1;

    while (status != 0) {
      if (status == -1) {
        System.out.println(menu);
      }

      input = bufferedReader.readLine();

      try {
        status = Integer.parseInt(input);
      } catch (NumberFormatException e) {
        continue;
      }

      if (status == 1) {
        User employee = SalesApplicationController.userLogin(Roles.EMPLOYEE);
        SalesApplicationController.employeeGui((Employee) employee);
        status = 0;
      } else if (status == 2) {
        User customer = SalesApplicationController.userLogin(Roles.CUSTOMER);
        SalesApplicationController.customerGui((Customer) customer);
        status = 0;
      }
    }
  }

  /**
   * Admin Methods
   ***********************************************************************************/

  /**
   * Admin GUI.
   *
   * @param admin the admin
   * @throws IOException when an unexpected I/O exception occured
   * @throws AuthenticationFailedException when failed to authenticate user
   * @throws SQLException when an unexpected sql exception occured
   * @throws ClassNotFoundException the class not found exception
   * @throws ValueExistsException the value exists exception
   * @throws ConnectionFailedException the connection failed exception
   * @throws DatabaseInsertException the database insert exception
   */
  protected static void adminGui(Admin admin)
      throws IOException, AuthenticationFailedException, SQLException, ClassNotFoundException,
      ValueExistsException, ConnectionFailedException, DatabaseInsertException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    String input;
    String menu = "\n1 - Promote Employee\n" + "2 - View Books\n" + "3 - View Customer Accounts\n"
        + "4 - Serialize Current Database\n" + "5 - Deserialize Backup Database\n" + "0 - Exit\n"
        + "Enter Selection";
    AdminInterface adminInterface = new AdminInterface(admin);
    int status = -1;

    if (admin != null && !admin.authenticated(null)) {
      throw new AuthenticationFailedException();
    }

    while (status != 0) {
      if (status == -1) {
        System.out.println(menu);
      }

      input = bufferedReader.readLine();

      try {
        status = Integer.parseInt(input);
      } catch (NumberFormatException e) {
        continue;
      }

      if (status == 1) {
        SalesApplicationController.promoteEmployee(adminInterface);
        status = -1;
      } else if (status == 2) {
        SalesApplicationController.viewBooks(adminInterface);
        status = -1;
      } else if (status == 3) {
        SalesApplicationController.viewAccounts(adminInterface);
        status = -1;
      } else if (status == 4) {
        SalesApplicationController.serialize(adminInterface);
        status = -1;
      } else if (status == 5) {
        SalesApplicationController.deserialize(adminInterface);
        status = -1;
      }
    }
  }

  /**
   * View historic sale records.
   *
   * @param adminInterface the admin interface
   * @throws SQLException when an unexpected sql exception occured
   */
  protected static void viewBooks(AdminInterface adminInterface) throws SQLException {
    if (adminInterface.hasCurrentAdmin()) {
      int customerRoleId = DatabaseSelectHelper.getRoleId(Roles.CUSTOMER);
      List<Integer> customerIds = DatabaseSelectHelper.getUsersByRole(customerRoleId);
      HashMap<Item, Integer> soldItems = new HashMap<>();
      BigDecimal price = new BigDecimal("0.00");

      for (int customerId : customerIds) {
        Customer customer = (Customer) DatabaseSelectHelper.getUserDetails(customerId);
        List<Sale> sales = DatabaseSelectHelper.getSalesToUser(customerId);

        for (Sale sale : sales) {
          System.out.println("Customer: " + customer.getName());
          System.out.println("Purchase Number: " + sale.getId());
          System.out.println("Total Purchase Price: $" + sale.getTotalPrice());
          price = price.add(sale.getTotalPrice());
          System.out.println("Itemized Breakdown: ");
          Sale itemizedSale = DatabaseSelectHelper.getItemizedSaleById(sale.getId());
          HashMap<Item, Integer> itemMap = itemizedSale.getItemMap();

          for (HashMap.Entry<Item, Integer> entry : itemMap.entrySet()) {
            System.out.print("\t" + entry.getKey().getName() + ": " + entry.getValue() + "\n");
            if (!soldItems.containsKey(entry.getKey())) {
              soldItems.put(entry.getKey(), entry.getValue());
            } else {
              int curQuantity = soldItems.get(entry.getKey());
              soldItems.replace(entry.getKey(), entry.getValue() + curQuantity);
            }
          }
          System.out.println("-------------------------------------");
        }
      }

      for (HashMap.Entry<Item, Integer> entry : soldItems.entrySet()) {
        System.out.println("Number " + entry.getKey().getName() + " Sold: " + entry.getValue());
      }

      System.out.println("TOTAL SALES: $" + price);
    } else {
      System.out.println("Permission Denied");
    }
  }

  /**
   * View accounts.
   *
   * @param adminInterface the admin interface
   * @throws IOException when an unexpected I/O exception occured
   * @throws SQLException when an unexpected sql exception occured
   */
  protected static void viewAccounts(AdminInterface adminInterface)
      throws IOException, SQLException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    String input;
    int customerId = 0;
    int active = 0;


    while (active == 0) {
      if (customerId == 0) {
        System.out.println("Enter Customer ID");
      } else {
        System.out.println("1. Active Accounts \n" + "2. Inactive Accounts");
      }

      input = bufferedReader.readLine();

      if (customerId == 0) {
        try {
          customerId = Integer.parseInt(input);
        } catch (NumberFormatException e) {
          System.out.println("Invalid Id");
        }
      } else if (active == 0) {
        try {
          active = Integer.parseInt(input);
        } catch (NumberFormatException e) {
          continue;
        }
      }

      if (active != 0) {
        User customer = DatabaseSelectHelper.getUserDetails(customerId);
        Roles role = DatabaseSelectHelper.getUserRole(customerId);

        if (customer != null && role.equals(Roles.CUSTOMER)) {
          List<Account> accounts = new ArrayList<Account>();

          if (active == 1) {
            accounts = adminInterface.getActiveAccounts((Customer) customer);
            System.out.println("Active Accounts For Customer:");
          } else {
            accounts = adminInterface.getInactiveAccounts((Customer) customer);
            System.out.println("Inactive Accounts For Customer:");
          }

          if (accounts.size() > 0) {
            for (Account a : accounts) {
              System.out.println(a.getId());
            }
          } else {
            System.out.println("No Accounts Found");
          }
        } else {
          System.out.println("Invalid Id");
          customerId = 0;
          active = 0;
        }
      }
    }
  }

  /**
   * Promote employee.
   *
   * @param adminInterface the admin interface
   * @throws IOException when an unexpected I/O exception occured
   * @throws AuthenticationFailedException when failed to authenticate user
   * @throws SQLException when an unexpected sql exception occured
   */
  protected static void promoteEmployee(AdminInterface adminInterface)
      throws IOException, AuthenticationFailedException, SQLException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    String input;
    int employeeId = 0;

    while (employeeId == 0) {
      if (employeeId == 0) {
        System.out.println("Enter Employee ID");
      }

      input = bufferedReader.readLine();

      if (employeeId == 0) {
        try {
          employeeId = Integer.parseInt(input);
        } catch (NumberFormatException e) {
          System.out.println("Invalid Id");
        }
      }

      if (employeeId != 0) {
        try {
          User employee = DatabaseSelectHelper.getUserDetails(employeeId);
          if (employee == null) {
            System.out.println("Invalid employee ID");
            employeeId = 0;
          } else {
            Roles role = DatabaseSelectHelper.getUserRole(employee.getId());
            if (employee != null && role != null && role.equals(Roles.EMPLOYEE)) {
              boolean result = adminInterface.promoteEmployee((Employee) employee);

              if (result) {
                System.out.println("Done");
              } else {
                System.out.println("Promotion Failed");
              }
            } else {
              System.out.println("Employee does not exist or is already an Admin");
            }
          }
        } catch (ConstraintViolationException e) {
          System.out.println("Invalid employee ID");
          employeeId = 0;
        }
      }
    }
  }

  /**
   * Serialize.
   *
   * @param adminInterface the admin interface
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws AuthenticationFailedException the authentication failed exception
   * @throws SQLException the SQL exception
   * @throws ConstraintViolationException the constraint violation exception
   */
  protected static void serialize(AdminInterface adminInterface) throws IOException,
      AuthenticationFailedException, SQLException, ConstraintViolationException {
    System.out.println(
        "Warning: This Operation Will Override The Existing database_copy.ser " + "if it exists");
    System.out.println("To Confirm This Action, Enter The Following Exactly: SERIALIZEDATABASE");
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    String input = bufferedReader.readLine();

    if (input.equals("SERIALIZEDATABASE")) {
      adminInterface.serializeDatabase("database_copy.ser");
      System.out.println("Database Serialized Under ./database_copy.ser");
    } else {
      System.out.println("Operation Canceled.");
    }
  }

  /**
   * Deserialize.
   *
   * @param adminInterface the admin interface
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws AuthenticationFailedException the authentication failed exception
   * @throws SQLException the SQL exception
   * @throws ClassNotFoundException the class not found exception
   * @throws ValueExistsException the value exists exception
   * @throws ConnectionFailedException the connection failed exception
   * @throws DatabaseInsertException the database insert exception
   */
  protected static void deserialize(AdminInterface adminInterface)
      throws IOException, AuthenticationFailedException, SQLException, ClassNotFoundException,
      ValueExistsException, ConnectionFailedException, DatabaseInsertException {
    System.out.println("Warning: This Operation Will Destroy The Existing SQLLite Database,\n"
        + "and Create a Backup Under ./database_copy_back.ser");
    System.out.println("This will Restore the Database to the State Stored in database_copy.ser");
    System.out.println("To Confirm This Action, Enter The Following Exactly: DESERIALIZEDATABASE");
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    String input = bufferedReader.readLine();

    if (input.equals("DESERIALIZEDATABASE")) {
      try {
        if (adminInterface.deserializeDatabase("./database_copy.ser")) {
          System.out.println("Database Deserialized With ./database_copy.ser");
        } else {
          System.out.println(
              "Permission Denied:" + " This admin is not in the previous serialized_database");
        }
      } catch (FileNotFoundException e) {
        System.out.println("Cannot Find File: ./database_copy.ser or ./inventorymgmt.db\n"
            + "Operation Canceled.");
      }
    } else {
      System.out.println("Operation Canceled.");
    }
  }

  /**
   * Employee Methods
   *******************************************************************************/

  /**
   * Employee GUI.
   *
   * @param employee the employee
   * @throws IOException when an unexpected I/O exception occured
   * @throws AuthenticationFailedException when failed to authenticate user
   * @throws SQLException when an unexpected sql exception occured
   * @throws DatabaseInsertException when insert unexpectedly fails
   */
  protected static void employeeGui(Employee employee)
      throws IOException, AuthenticationFailedException, SQLException, DatabaseInsertException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    String input;
    EmployeeInterface employeeInterface =
        new EmployeeInterface(employee, DatabaseSelectHelper.getInventory());
    String gui = "\n1. Authenticate New Employee\n" + "2. Make New User\n" + "3. Make New Account\n"
        + "4. Make New Employee\n" + "5. Restock Inventory\n" + "6 - Exit\n" + "Enter Selection:";
    int status = -1;

    while (status != 6) {
      if (status == -1) {
        System.out.println(gui);
      }

      input = bufferedReader.readLine();

      try {
        status = Integer.parseInt(input);
      } catch (NumberFormatException e) {
        continue;
      }

      if (status == 1) {
        SalesApplicationController.createNewUser(employeeInterface, Roles.EMPLOYEE);
        status = -1;
      } else if (status == 2) {
        SalesApplicationController.createNewUser(employeeInterface, Roles.CUSTOMER);
        status = -1;
      } else if (status == 3) {
        SalesApplicationController.createAccount(employeeInterface);
        status = -1;
      } else if (status == 4) {
        SalesApplicationController.createNewUser(employeeInterface, Roles.EMPLOYEE);
        status = -1;
      } else if (status == 5) {
        SalesApplicationController.reStockInventory(employeeInterface);
        status = -1;
      }
    }
  }

  /**
   * Add item GUI.
   *
   * @param employeeInterface the employee interface
   * @throws IOException when an unexpected I/O exception occured
   * @throws SQLException when an unexpected sql exception occured
   * @throws AuthenticationFailedException when failed to authenticate user
   * @throws DatabaseInsertException when insert unexpectedly fails
   */
  protected static void createAccount(EmployeeInterface employeeInterface)
      throws IOException, SQLException, AuthenticationFailedException, DatabaseInsertException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    String input;
    int userId = 0;

    while (userId == 0) {
      if (userId == 0) {
        System.out.println("Enter User ID");
      }

      input = bufferedReader.readLine();

      if (userId == 0) {
        try {
          userId = Integer.parseInt(input);
        } catch (NumberFormatException e) {
          System.out.println("Invalid Id");
        }
      }

      if (userId != 0) {
        try {
          int accId = employeeInterface.createAccount(userId);
          System.out.println("Account Created ID: " + accId);
        } catch (ConstraintViolationException e) {
          System.out.println("Customer ID Not Valid");
        } catch (ValueExistsException e) {
          System.out.println("User has an existing account");
        }
      }
    }
  }

  /**
   * Re-stock Inventory GUI.
   *
   * @param employeeInterface the employee interface
   * @throws IOException when an unexpected I/O exception occured
   * @throws SQLException when an unexpected sql exception occured
   */
  protected static void reStockInventory(EmployeeInterface employeeInterface)
      throws IOException, SQLException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    String input;
    int itemId = 0;
    int quantity = 0;

    while (quantity == 0) {
      if (itemId == 0) {
        System.out.println("Enter Item ID");
      } else if (quantity == 0) {
        System.out.println("Enter Desired Re-Stock Quantity");
      }

      input = bufferedReader.readLine();

      if (itemId == 0) {
        try {
          itemId = Integer.parseInt(input);
        } catch (NumberFormatException e) {
          System.out.println("Invalid Id");
        }
      } else if (quantity == 0) {
        try {
          quantity = Integer.parseInt(input);
        } catch (NumberFormatException e) {
          System.out.println("Quantity Must Be A Positive Number");
        }
      }

      if (quantity != 0) {
        try {
          boolean result =
              employeeInterface.restockInventory(DatabaseSelectHelper.getItem(itemId), quantity);
          if (result) {
            System.out.println("Items Added");
          } else {
            System.out.println("Invalid Item ID");
          }
        } catch (ConstraintViolationException e) {
          System.out.println("Failed to Re-Stock Item, Input(s) Do Not Satisfy Contraints");
        }
      }
    }
  }

  /**
   * Creates new user
   *
   * @param employeeInterface the employee interface
   * @param role the role
   * @return the int
   * @throws AuthenticationFailedException when failed to authenticate user
   * @throws SQLException when an unexpected sql exception occured
   * @throws IOException when an unexpected I/O exception occured
   * @throws DatabaseInsertException when insert unexpectedly fails
   */
  protected static int createNewUser(EmployeeInterface employeeInterface, Roles role)
      throws AuthenticationFailedException, SQLException, IOException, DatabaseInsertException {
    String name = null;
    int age = 0;
    String address = null;
    String password = null;

    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    String input;
    int status = -1;

    while (status != 0) {
      if (name == null) {
        System.out.println("Please Enter User Name");
      } else if (age == 0) {
        System.out.println("Please Enter User Age");
      } else if (address == null) {
        System.out.println("Please Enter User Address");
      } else {
        System.out.println("Please Enter User Password");
      }

      input = bufferedReader.readLine();

      if (name == null) {
        name = input;
      } else if (age == 0) {
        try {
          age = Integer.parseInt(input);
        } catch (NumberFormatException e) {
          System.out.println("Age should be a positive number");
          continue;
        }
      } else if (address == null) {
        address = input;
      } else if (password == null) {
        password = input;
      }

      if (password != null) {
        try {
          int id = 0;
          if (role.equals(Roles.CUSTOMER)) {
            id = employeeInterface.createCustomer(name, age, address, password);
            System.out.println("Customer Created With ID: " + id);
          } else if (role.equals(Roles.EMPLOYEE)) {
            id = employeeInterface.createEmployee(name, age, address, password);
            System.out.println("Employee Created With ID: " + id);
          } else {
            System.out.println("Role Selected Not Supported");
          }
          return id;
        } catch (ConstraintViolationException e) {
          System.out.println("Failed To Create New User, Input(s) Do Not Satisfy Contraints");
        } catch (ValueExistsException e) {
          System.out.println("User with this Id already has a role");
        }
        status = -1;
      }
    }
    return 0;
  }

  /**
   * Customer Methods
   *******************************************************************************/

  /**
   * Customer GUI.
   *
   * @param customer the customer
   * @throws IOException when an unexpected I/O exception occured
   * @throws SQLException when an unexpected sql exception occured
   * @throws AuthenticationFailedException when failed to authenticate user
   * @throws DatabaseInsertException when insert unexpectedly fails
   */
  protected static void customerGui(Customer customer)
      throws IOException, SQLException, AuthenticationFailedException, DatabaseInsertException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    String input;
    String gui = "\n1. List current items in cart\n" + "2. Add a quantity of an item to the cart\n"
        + "3. Check total price of items in the cart\n"
        + "4. Remove a quantity of an item from the cart\n" + "5. Check out\n"
        + "6. Restore Previous Cart\n" + "7. Exit\n" + "Enter Selection:";
    ShoppingCart cart = new ShoppingCart(customer);
    int accountId = 0;

    int status = -1;

    while (status != 7) {
      if (status == -1) {
        System.out.println(gui);
      }

      input = bufferedReader.readLine();

      try {
        status = Integer.parseInt(input);
      } catch (NumberFormatException e) {
        continue;
      }

      if (status == 1) {
        if (cart.getItems().size() == 0) {
          System.out.println("Cart Empty");
        } else {
          System.out.println("Cart:");
        }
        for (Item i : cart.getCart().keySet()) {
          System.out.println(i.getName() + " | Quantity: " + cart.getCart().get(i));
        }
        status = -1;
      } else if (status == 2) {
        SalesApplicationController.addItem(cart);
        status = -1;
      } else if (status == 3) {
        System.out.println("Total Price: " + cart.getTotal());
        status = -1;
      } else if (status == 4) {
        SalesApplicationController.removeItem(cart);
        status = -1;
      } else if (status == 5) {
        status = SalesApplicationController.checkout(cart, accountId);
      } else if (status == 6) {
        accountId = SalesApplicationController.restoreCart(cart);
        status = -1;
      } else if (status == 7) {
        SalesApplicationController.saveCart(cart);
      }
    }
  }

  /**
   * Checkout.
   *
   * @param cart the cart
   * @param accountId the account id
   * @return the int
   * @throws ConstraintViolationException the constraint violation exception
   * @throws SQLException the SQL exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  protected static int checkout(ShoppingCart cart, int accountId)
      throws ConstraintViolationException, SQLException, IOException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    System.out.println("Total: " + cart.getTotal() + "\n" + "After Tax: "
        + cart.getTotal().multiply(cart.getTaxRate()).setScale(2, RoundingMode.CEILING));

    System.out.println("Enter 1 if you would like to confirm your check out. "
        + "To continue shopping, enter anything else");
    String input = bufferedReader.readLine();

    if (input.compareTo("1") == 0) {
      if (cart.getItems().size() == 0) {
        System.out.println("Your cannot check out with an empty cart");
      } else if (cart.checkOutCustomer()) {
        if (accountId != 0) {
          DatabaseUpdateHelper.updateAccountStatus(accountId, false);
        }
        System.out.println("You have successfully checked out");
      } else {
        System.out.println("Required quantity out of stock");
      }
      return 7;
    }
    return -1;
  }

  /**
   * Save cart GUI.
   *
   * @param cart the cart
   * @throws DatabaseInsertException when insert unexpectedly fails
   * @throws IOException when an unexpected I/O exception occured
   * @throws SQLException when an unexpected sql exception occured
   */
  protected static void saveCart(ShoppingCart cart)
      throws DatabaseInsertException, IOException, SQLException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    System.out.println("If You Wish To Save Your Cart. Enter YES Now.");
    String input = bufferedReader.readLine();

    if (input.equals("YES")) {
      HashMap<Item, Integer> sCart = cart.getCart();
      List<Account> account =
          DatabaseSelectHelper.getUserActiveAccounts(cart.getCustomer().getId());

      // check if active account exist
      if (account.size() != 0 && sCart.keySet().size() != 0) {
        AccountSummary summary = DatabaseSelectHelper.getAccountDetails(account.get(0).getId());

        // check if account is already in use
        if (summary.getSummary().size() == 0) {
          for (Item i : sCart.keySet()) {
            DatabaseInsertHelper.insertAccountLine(account.get(0).getId(), i.getId(), sCart.get(i));
          }
          System.out.println("Cart Saved To Account ID: " + account.get(0).getId());
        } else {
          System.out.println("Customer active account already in use");
        }

      } else {
        System.out.println("Customer doesnt have an active account to save items on");
      }
    }
  }

  /**
   * Restore cart GUI.
   *
   * @param cart the cart
   * @return accountID of associated cart
   * @throws SQLException when an unexpected sql exception occured
   * @throws AuthenticationFailedException when failed to authenticate user
   */
  protected static int restoreCart(ShoppingCart cart)
      throws SQLException, AuthenticationFailedException {
    // restore shopping cart
    List<Account> accounts = DatabaseSelectHelper.getUserActiveAccounts(cart.getCustomer().getId());
    int accountID = 0;
    if (accounts.size() != 0) {
      AccountSummary accSummary = DatabaseSelectHelper.getAccountDetails(accounts.get(0).getId());
      HashMap<Item, Integer> summary = accSummary.getSummary();

      if (summary.size() != 0) {
        for (Item i : summary.keySet()) {
          cart.addItem(i, summary.get(i));
        }
        System.out.println("Cart Restored");
        accountID = accSummary.getId();
      } else {
        System.out.println("Cart Restore Failed: No stored cart was found");
      }
    } else {
      System.out.println("Customer does not have an active account");
    }
    return accountID;
  }

  /**
   * Add item GUI.
   *
   * @param cart the cart
   * @throws IOException when an unexpected I/O exception occured
   * @throws SQLException when an unexpected sql exception occured
   */
  protected static void addItem(ShoppingCart cart) throws IOException, SQLException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    String input;
    Item item = null;
    int quantity = 0;
    Inventory inv = DatabaseSelectHelper.getInventory();
    HashMap<Item, Integer> inventory = inv.getItemMap();
    for (Item i : inventory.keySet()) {
      int amount = inventory.get(i);
      System.out.println("ID: " + i.getId() + " Name: " + i.getName() + " Price: " + i.getPrice()
          + " Amount Avaliable: " + amount);
    }

    while (quantity == 0) {
      if (item == null) {
        System.out.println("Enter Item ID");
      } else if (quantity == 0) {
        System.out.println("Enter Desired Quantity");
      }

      input = bufferedReader.readLine();

      if (item == null) {
        try {
          int itemId = Integer.parseInt(input);
          if ((item = DatabaseSelectHelper.getItem(itemId)) == null) {
            itemId = 0;
            System.out.println("Invalid Id");
          }
        } catch (NumberFormatException e) {
          System.out.println("Invalid Id");
        }
      } else if (quantity == 0) {
        try {
          quantity = Integer.parseInt(input);
          if (quantity < 1) {
            quantity = 0;
            System.out.println("quantity must be a positive number");
          }
        } catch (NumberFormatException e) {
          System.out.println("quantity must be a positive number");
        }
      }

      if (quantity != 0) {
        cart.addItem(item, quantity);
        System.out.println("Items Added");
      }
    }
  }

  /**
   * Removes item GUI.
   *
   * @param cart the cart
   * @throws IOException when an unexpected I/O exception occured
   * @throws SQLException when an unexpected sql exception occured
   */
  protected static void removeItem(ShoppingCart cart) throws IOException, SQLException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    String input;
    Item item = null;
    int quantity = 0;

    if (cart.getItems().size() != 0) {
      while (quantity == 0) {
        if (item == null) {
          System.out.println("Enter Item ID");
        } else if (quantity == 0) {
          System.out.println("Enter Desired Quantity");
        }

        input = bufferedReader.readLine();

        if (item == null) {
          try {
            int itemId = Integer.parseInt(input);
            boolean exists = false;
            for (Item i : cart.getItems()) {
              if (i.getId() == itemId) {
                exists = true;
                break;
              }
            }
            if (!exists) {
              itemId = 0;
              System.out.println("Invalid Id");
            } else {
              item = DatabaseSelectHelper.getItem(itemId);
            }
          } catch (NumberFormatException e) {
            System.out.println("Invalid Id");
          }
        } else if (quantity == 0) {
          try {
            quantity = Integer.parseInt(input);
            if (quantity < 1) {
              quantity = 0;
              System.out.println("quantity must be a positive number");
            }
          } catch (NumberFormatException e) {
            System.out.println("quantity must be a positive number");
          }
        }

        if (quantity != 0) {
          cart.removeItem(item, quantity);
          System.out.println("Items Removed");
        }
      }
    } else {
      System.out.println("Your Cart is Empty");
    }
  }
}
