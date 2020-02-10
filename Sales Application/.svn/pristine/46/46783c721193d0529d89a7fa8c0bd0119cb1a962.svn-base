package com.b07.ui.employee.controllers;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.b07.exceptions.ConstraintViolationException;
import com.b07.exceptions.ValueExistsException;
import com.b07.store.EmployeeInterface;

import java.sql.SQLException;

public class MakeNewAccountButtonController implements View.OnClickListener {
  private Context appContext;
  private EditText editId;
  private EmployeeInterface employeeInterface;

  /**
   * constructor for the button controller
   *
   * @param context           the context of the class the calls this
   * @param employeeInterface employee interface
   * @param editId            the edit id control
   */
  public MakeNewAccountButtonController(Context context, EmployeeInterface employeeInterface, EditText editId) {
    appContext = context;
    this.editId = editId;
    this.employeeInterface = employeeInterface;
  }

  /**
   * the on click function for the make new account button
   *
   * @param view
   */
  @Override
  public void onClick(View view) {
    Toast message;
    try {
      int userId = Integer.parseInt(editId.getText().toString());
      int accountId = employeeInterface.createAccount(appContext, userId, true);
      message = Toast.makeText(appContext, "Account with ID " + accountId + " created successfully!", Toast.LENGTH_SHORT);
      message.show();
    } catch (NumberFormatException e) {
      message = Toast.makeText(appContext, "User ID must be in digits!", Toast.LENGTH_SHORT);
      message.show();
    } catch (SQLException e) {
      message = Toast.makeText(appContext, "Database Error!", Toast.LENGTH_SHORT);
      message.show();
    } catch (ValueExistsException e) {
      message = Toast.makeText(appContext, "User already has an account!", Toast.LENGTH_SHORT);
      message.show();
    } catch (ConstraintViolationException e) {
      message = Toast.makeText(appContext, "Invalid Customer ID!", Toast.LENGTH_SHORT);
      message.show();
    } catch (Exception e) {
      message = Toast.makeText(appContext, "An error has occurred!", Toast.LENGTH_SHORT);
      message.show();
    }
  }
}
