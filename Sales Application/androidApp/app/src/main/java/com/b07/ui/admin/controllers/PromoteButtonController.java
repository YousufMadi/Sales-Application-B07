package com.b07.ui.admin.controllers;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.b07.database.DatabaseSelectorAndroid;
import com.b07.database.DatabaseUpdaterAndroid;
import com.b07.exceptions.AuthenticationFailedException;
import com.b07.exceptions.ConstraintViolationException;
import com.b07.store.AdminInterface;
import com.b07.users.Employee;
import com.b07.users.Roles;
import com.b07.users.User;

import java.sql.SQLException;

public class PromoteButtonController implements View.OnClickListener {
  private Context appContext;
  private DatabaseUpdaterAndroid dbUpdate;
  private DatabaseSelectorAndroid dbSelect;
  private EditText employeeId;
  private AdminInterface adminInterface;
  Toast message;

  /**
   * Method to instantiate a button controller.
   *
   * @param context        is the context of the app.
   * @param employeeId     is the Id of the employee being promoted.
   * @param adminInterface is the admin doing the promotion's interface.
   */


  public PromoteButtonController(Context context, EditText employeeId, AdminInterface adminInterface) {
    this.appContext = context;
    this.employeeId = employeeId;
    this.adminInterface = adminInterface;
    dbSelect = new DatabaseSelectorAndroid(context);
    dbUpdate = new DatabaseUpdaterAndroid(context);

  }

  @Override
  public void onClick(View view) {
    try {
      int userId = Integer.parseInt(employeeId.getText().toString());
      User employee = dbSelect.getUserDetails(userId);

      if (employee == null) {
        message = Toast.makeText(appContext, "This ID does not exist", Toast.LENGTH_SHORT);
        message.show();
      } else {
        int userRole = dbSelect.getUserRoleId(userId);
        String roleName = dbSelect.getRoleName(userRole);
        if (!roleName.equalsIgnoreCase(Roles.EMPLOYEE.toString())) {
          message = Toast.makeText(appContext, "This user is not an Employee", Toast.LENGTH_SHORT);
          message.show();
        } else {
          if (adminInterface.promoteEmployee(appContext, (Employee) employee)) {
            message = Toast.makeText(appContext, employee.getName() + " has been promoted!", Toast.LENGTH_SHORT);
            message.show();
          }
          message = Toast.makeText(appContext, "Oops, something went wrong!", Toast.LENGTH_SHORT);
          message.show();
        }
      }
    } catch (SQLException e) {
      message = Toast.makeText(appContext, "Error connecting to database", Toast.LENGTH_SHORT);
      message.show();
    } catch (ConstraintViolationException | NumberFormatException e) {
      message = Toast.makeText(appContext, "Not a valid ID", Toast.LENGTH_SHORT);
      message.show();
    } catch (AuthenticationFailedException a) {
      message = Toast.makeText(appContext, "Could not authenticate employee", Toast.LENGTH_SHORT);
      message.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}