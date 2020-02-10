package com.b07.store;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.database.helper.DatabaseUpdateHelper;
import com.b07.database.helper.DeserializeSalesDatabase;
import com.b07.database.helper.SerializeSalesDatabase;
import com.b07.exceptions.AuthenticationFailedException;
import com.b07.exceptions.ConnectionFailedException;
import com.b07.exceptions.ConstraintViolationException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.ValueExistsException;
import com.b07.users.Account;
import com.b07.users.Admin;
import com.b07.users.Customer;
import com.b07.users.Employee;
import com.b07.users.Roles;

/**
 * The Class AdminInterface.
 */
public class AdminInterface {

  /** The current admin. */
  private Admin currentAdmin;

  /**
   * Instantiates a new admin interface.
   *
   * @param admin the admin
   * @throws SQLException the SQL exception
   * @throws AuthenticationFailedException the authentication failed exception
   */
  public AdminInterface(Admin admin) throws SQLException, AuthenticationFailedException {
    this.setCurrentAdmin(admin);
  }

  /**
   * Sets the current admin.
   *
   * @param admin the new current admin
   * @throws SQLException the SQL exception
   * @throws AuthenticationFailedException the authentication failed exception
   */
  public void setCurrentAdmin(Admin admin) throws SQLException, AuthenticationFailedException {
    if (admin != null && admin.authenticated(null)) {
      this.currentAdmin = admin;
    } else {
      throw new AuthenticationFailedException();
    }
  }

  /**
   * Checks for current admin.
   *
   * @return true, if successful
   */
  public boolean hasCurrentAdmin() {
    return (currentAdmin != null);
  }

  /**
   * Promote employee to admin.
   *
   * @param Employee the employee to promote
   * @return true, if successful
   * @throws SQLException the SQL exception
   * @throws AuthenticationFailedException the authentication failed exception
   * @throws ConstraintViolationException the constraint violation exception
   */
  public boolean promoteEmployee(Employee Employee)
      throws SQLException, AuthenticationFailedException, ConstraintViolationException {
    int adminId = DatabaseSelectHelper.getRoleId(Roles.ADMIN);
    return DatabaseUpdateHelper.updateUserRole(adminId, Employee.getId());
  }

  /**
   * Gets the inactive accounts.
   *
   * @param customer the customer
   * @return the inactive accounts
   * @throws SQLException the SQL exception
   */
  public List<Account> getInactiveAccounts(Customer customer) throws SQLException {
    return DatabaseSelectHelper.getUserInActiveAccounts(customer.getId());
  }

  /**
   * Gets the active accounts.
   *
   * @param customer the customer
   * @return the active accounts
   * @throws SQLException the SQL exception
   */
  public List<Account> getActiveAccounts(Customer customer) throws SQLException {
    return DatabaseSelectHelper.getUserInActiveAccounts(customer.getId());
  }

  /**
   * Serialize database.
   *
   * @param path the path
   * @throws ConstraintViolationException the constraint violation exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws SQLException the SQL exception
   */
  public void serializeDatabase(String path)
      throws ConstraintViolationException, IOException, SQLException {
    SerializeSalesDatabase.serialize(path);
  }

  /**
   * Deserialize database.
   *
   * @param path the path
   * @return true, if successful
   * @throws ClassNotFoundException the class not found exception
   * @throws ValueExistsException the value exists exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws SQLException the SQL exception
   * @throws ConnectionFailedException the connection failed exception
   * @throws DatabaseInsertException the database insert exception
   */
  public boolean deserializeDatabase(String path)
      throws ClassNotFoundException, ValueExistsException, IOException, SQLException,
      ConnectionFailedException, DatabaseInsertException {
    return DeserializeSalesDatabase.deserialize(path, this.currentAdmin);
  }
}
