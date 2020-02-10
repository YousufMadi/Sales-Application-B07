package com.b07.ui.employee.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.b07.R;
import com.b07.store.EmployeeInterface;
import com.b07.ui.employee.MakeNewAccountView;
import com.b07.ui.employee.MakeNewUserView;
import com.b07.ui.employee.RestockInventoryView;
import com.b07.ui.login.LoginActivity;

public class EmployeeMenuButtonController implements View.OnClickListener {
  private Context appContext;
  private EmployeeInterface employeeInterface;

  /**
   * constructor for the employee menu button controller
   *
   * @param context           the context of the class calling this
   * @param employeeInterface the employee interface
   */
  public EmployeeMenuButtonController(Context context, EmployeeInterface employeeInterface) {
    appContext = context;
    this.employeeInterface = employeeInterface;
  }

  /**
   * the functionality of the employee main menu buttons
   *
   * @param view view
   */
  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.btnEmployeeMenuAuthenticate: {
        openAuthenticateEmployeeView(employeeInterface);
        break;
      }
      case R.id.btnEmployeeMenuCreateAccount: {
        openMakeNewAccountView(employeeInterface);
        break;
      }
      case R.id.btnEmployeeMenuLogOut: {
        openLoginView();
        break;
      }
      case R.id.btnEmployeeMenuRestock: {
        openRestock(employeeInterface);
        break;
      }
      case R.id.btnEmployeeMenuCreateUser: {
        openMakeNewUser(employeeInterface);
        break;
      }
    }
  }

  /**
   * opens authenticate a user view
   *
   * @param employeeInterface current employee's interface
   */
  public void openAuthenticateEmployeeView(EmployeeInterface employeeInterface) {
    Intent intent = new Intent(appContext, MakeNewUserView.class);
    intent.putExtra("EMPLOYEE_INTERFACE", employeeInterface);
    appContext.startActivity(intent);
  }

  /**
   * opens make a new account view
   *
   * @param employeeInterface current employee's interface
   */
  public void openMakeNewAccountView(EmployeeInterface employeeInterface) {
    Intent intent = new Intent(appContext, MakeNewAccountView.class);
    intent.putExtra("EMPLOYEE_INTERFACE", employeeInterface);
    appContext.startActivity(intent);
  }

  /**
   * opens the login view
   */
  public void openLoginView() {
    Intent intent = new Intent(appContext, LoginActivity.class);
    Activity activity = (Activity) appContext;
    activity.finish();
    appContext.startActivity(intent);
  }

  /**
   * opens the restock inventory view
   *
   * @param employeeInterface current employee's interface
   */
  public void openRestock(EmployeeInterface employeeInterface) {
    Intent intent = new Intent(appContext, RestockInventoryView.class);
    intent.putExtra("EMPLOYEE_INTERFACE", employeeInterface);
    appContext.startActivity(intent);
  }

  /**
   * opens the make new user view
   *
   * @param employeeInterface current employee's interface
   */
  public void openMakeNewUser(EmployeeInterface employeeInterface) {
    Intent intent = new Intent(appContext, MakeNewUserView.class);
    intent.putExtra("EMPLOYEE_INTERFACE", employeeInterface);
    appContext.startActivity(intent);
  }

}