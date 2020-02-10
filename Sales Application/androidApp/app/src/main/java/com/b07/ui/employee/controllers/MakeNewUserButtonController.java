package com.b07.ui.employee.controllers;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.b07.R;
import com.b07.database.validator.InputValidator;
import com.b07.exceptions.ConstraintViolationException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.store.EmployeeInterface;
import com.b07.users.Roles;

import java.sql.SQLException;

public class MakeNewUserButtonController implements View.OnClickListener {
  private Context appContext;
  EmployeeInterface employeeInterface;

  Toast message;

  /**
   * Constructor to make new User.
   *
   * @param context           is the context.
   * @param employeeInterface is the employee interface from the assignment.
   */
  public MakeNewUserButtonController(Context context, EmployeeInterface employeeInterface) {
    this.appContext = context;
    this.employeeInterface = employeeInterface;
  }

  /**
   * Onclick method to add user.
   *
   * @param view is the view.
   */
  @Override
  public void onClick(View view) {
    Spinner spinnerRoles = (Spinner) ((AppCompatActivity) appContext).findViewById(R.id.spinnerNewUserRoles);
    EditText editName = (EditText) ((AppCompatActivity) appContext).findViewById(R.id.editNewUserName);
    EditText editAge = (EditText) ((AppCompatActivity) appContext).findViewById(R.id.editNewUserAge);
    EditText editAddress = (EditText) ((AppCompatActivity) appContext).findViewById(R.id.editNewUserAddress);
    EditText editPassword = (EditText) ((AppCompatActivity) appContext).findViewById(R.id.editNewUserPassword);

    int role = (int) spinnerRoles.getSelectedItemId();
    String name = editName.getText().toString();
    String age = editAge.getText().toString();
    String address = editAddress.getText().toString();
    String password = editPassword.getText().toString();
    createUser(name, role, age, address, password);

  }

  /**
   * Method to create the user.
   *
   * @param name     is the name.
   * @param role     is the role.
   * @param ageStr   is age in string format.
   * @param address  is address.
   * @param password is password.
   */
  private void createUser(String name, int role, String ageStr, String address, String password) {
    int age = 0;
    try {
      InputValidator.validateUserName(name);
    } catch (ConstraintViolationException e) {
      message = Toast.makeText(appContext, "Incorrect name input!", Toast.LENGTH_SHORT);
      message.show();
      return;
    }
    try {
      age = Integer.parseInt(ageStr);
      InputValidator.validateUserAge(age);
    } catch (NumberFormatException | ConstraintViolationException e) {
      message = Toast.makeText(appContext, "Incorrect age input!", Toast.LENGTH_SHORT);
      message.show();
      return;
    }
    try {
      InputValidator.validateUserAddress(address);
    } catch (ConstraintViolationException e) {
      message = Toast.makeText(appContext, "Incorrect address input!", Toast.LENGTH_SHORT);
      message.show();
      return;
    }
    try {
      InputValidator.validateUserPassword(password);
    } catch (ConstraintViolationException e) {
      message = Toast.makeText(appContext, "Incorrect password input!", Toast.LENGTH_SHORT);
      message.show();
      return;
    }
    try {
      int id = 0;
      if (role == 0) {
        id = employeeInterface.makeNewUser(appContext, Roles.EMPLOYEE, name, age, address, password);
        message = Toast.makeText(appContext, "Employee created with ID " + id, Toast.LENGTH_SHORT);
        message.show();
      } else if (role == 1) {
        id = employeeInterface.makeNewUser(appContext, Roles.CUSTOMER, name, age, address, password);
        message = Toast.makeText(appContext, "Customer created with ID " + id, Toast.LENGTH_SHORT);
        message.show();
      }
    } catch (ConstraintViolationException e) {
      message = Toast.makeText(appContext, "Input error!", Toast.LENGTH_SHORT);
      message.show();
    } catch (DatabaseInsertException e) {
      message = Toast.makeText(appContext, "Insert error!", Toast.LENGTH_SHORT);
      message.show();
    } catch (SQLException e) {
      message = Toast.makeText(appContext, "SQL Exception Occurred", Toast.LENGTH_SHORT);
      message.show();
    }
  }

}
