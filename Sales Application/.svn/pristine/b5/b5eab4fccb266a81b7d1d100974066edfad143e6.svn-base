package com.b07.store;

import java.io.IOException;
import java.sql.SQLException;
import com.b07.exceptions.AuthenticationFailedException;
import com.b07.exceptions.ConnectionFailedException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.users.Admin;
import com.b07.users.Roles;
import com.b07.users.User;

public class SalesApplication {

  /**
   * The Main SalesApplication
   * 
   * @param argv
   */
  public static void main(String[] argv) {
    try {
      // option -1 for first time initalization
      if (argv.length != 0 && argv[0].equals("-1")) {
        SalesApplicationController.firstTimeSetUp();
        System.out.println("Application Terminated");
        // option 1 for admin interface
      } else if (argv.length != 0 && argv[0].equals("1")) {
        User workingUser = SalesApplicationController.userLogin(Roles.ADMIN);
        SalesApplicationController.adminGui((Admin) workingUser);
        System.out.println("Application Terminated");
        // else for context menu
      } else {
        SalesApplicationController.contextMenu();
        System.out.println("Application Terminated");
      }
    } catch (ConnectionFailedException e) {
      System.out.println("Unable to Connect to Server |" + " Error Code: " + e.getErrorCode());
    } catch (SQLException e) {
      System.out.println("An Unexpected Error Has Occured While Communicating With Database |"
          + " Error Code: " + e.getErrorCode());
    } catch (IOException e) {
      System.out.println("Something Went Wrong While Reading Input. App Terminated |" + " Error : "
          + e.toString());
    } catch (AuthenticationFailedException e) {
      System.out.println("Unable to Verify User Authentication");
    } catch (DatabaseInsertException e) {
      System.out.println("An Unexpected Error Has Occured While Inserting Into Database |"
          + " Error Code: " + e.getErrorCode());
    } catch (Exception e) {
      e.printStackTrace();
      System.out
          .println("An Unexpected Error Has Occured | Contact Customer Support at 905-905-9005");
    }
  }
}
