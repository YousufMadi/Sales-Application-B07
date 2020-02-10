package com.b07.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.b07.database.DatabaseSelectorAndroid;
import com.b07.exceptions.ConstraintViolationException;
import com.b07.ui.admin.MainAdminView;
import com.b07.ui.customer.MainCustomerView;
import com.b07.ui.employee.MainEmployeeView;
import com.b07.users.User;

import java.sql.SQLException;

public class LoginButtonController implements View.OnClickListener {
  private Context appContext;
  private EditText editId;
  private EditText editPassword;
  Toast message;

  public LoginButtonController(Context context, EditText editId, EditText editPassword) {
    appContext = context;
    this.editId = editId;
    this.editPassword = editPassword;
  }

  @Override
  public void onClick(View v) {
    loginUser(editId, editPassword);
  }

  private void loginUser(EditText editId, EditText editPassword) {
    try {
      DatabaseSelectorAndroid dbSelect = new DatabaseSelectorAndroid(appContext);
      int userId = Integer.parseInt(editId.getText().toString());
      String userPassword = editPassword.getText().toString();
      User user = dbSelect.getUserDetails(userId);

      if (user == null) {
        message = Toast.makeText(appContext, "User Does Not Exist!", Toast.LENGTH_SHORT);
        message.show();
      } else {
        int userRole = dbSelect.getUserRoleId(userId);
        String roleName = dbSelect.getRoleName(userRole);
        if (user.authenticated(appContext, userPassword) && roleName.equals("ADMIN")) {
          openMainAdminView(user);
        } else if (user.authenticated(appContext, userPassword) && roleName.equals("EMPLOYEE")) {
          openMainEmployeeView(user);
        } else if (user.authenticated(appContext, userPassword)) {
          openMainCustomerView(user);
        } else {
          message = Toast.makeText(appContext, "Invalid Username/Password!!", Toast.LENGTH_SHORT);
          message.show();
        }
      }
    } catch (SQLException e) {
      message = Toast.makeText(appContext, "Database Error Occurred!", Toast.LENGTH_SHORT);
      message.show();
    } catch (ConstraintViolationException | NumberFormatException e) {
      message = Toast.makeText(appContext, "Invalid Username/Password!!", Toast.LENGTH_SHORT);
      message.show();
    } catch (Exception e) {
      message = Toast.makeText(appContext, "An Unexpected Error has Occurred!", Toast.LENGTH_SHORT);
      message.show();
    }
  }

  private void openMainCustomerView(User user) {
    Intent intent = new Intent(appContext, MainCustomerView.class);
    intent.putExtra("USER", user);
    Activity activity = (Activity) appContext;
    activity.finish();
    appContext.startActivity(intent);
  }

  private void openMainEmployeeView(User user) {
    Intent intent = new Intent(appContext, MainEmployeeView.class);
    intent.putExtra("USER", user);
    Activity activity = (Activity) appContext;
    activity.finish();
    appContext.startActivity(intent);
  }

  private void openMainAdminView(User user) {
    Intent intent = new Intent(appContext, MainAdminView.class);
    intent.putExtra("USER", user);
    Activity activity = (Activity) appContext;
    activity.finish();
    appContext.startActivity(intent);
  }
}
