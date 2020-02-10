package com.b07.ui.admin.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.b07.R;
import com.b07.database.DatabaseSelectorAndroid;
import com.b07.database.validator.DatabaseInsertValidator;
import com.b07.exceptions.ConstraintViolationException;
import com.b07.store.AdminInterface;
import com.b07.ui.admin.CustomerAccountsResultView;
import com.b07.users.Roles;

import java.sql.SQLException;

public class AccountsButtonControllers implements View.OnClickListener {
  private Context appContext;
  private AdminInterface adminInterface;
  private EditText editCustomerId;
  Toast message;

  public AccountsButtonControllers(Context context, AdminInterface adminInterface, EditText editCustomerId) {
    this.appContext = context;
    this.adminInterface = adminInterface;
    this.editCustomerId = editCustomerId;
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  @Override
  public void onClick(View view) {
    int customerId = 0;
    Toast message;
    DatabaseInsertValidator dbValidate = new DatabaseInsertValidator(appContext);
    DatabaseSelectorAndroid dbSelect = new DatabaseSelectorAndroid(appContext);

    // Validate user id input
    try {
      customerId = Integer.parseInt(editCustomerId.getText().toString());
      int userRole = dbSelect.getUserRoleId(customerId);
      String roleName = dbSelect.getRoleName(userRole);

      if (!roleName.equalsIgnoreCase(Roles.CUSTOMER.toString())) {
        throw new ConstraintViolationException();
      }
    } catch (ConstraintViolationException | NumberFormatException e) {
      message = Toast.makeText(appContext, "Invalid Customer ID!", Toast.LENGTH_SHORT);
      message.show();
      return;
    } catch (SQLException e) {
      message = Toast.makeText(appContext, "Error Connecting to the Database!", Toast.LENGTH_SHORT);
      message.show();
      return;
    }

    switch (view.getId()) {
      case R.id.btnViewActiveAccounts:
        Intent intentActive = new Intent(this.appContext, CustomerAccountsResultView.class);
        intentActive.putExtra("ADMIN_INTERFACE", adminInterface);
        intentActive.putExtra("ACTIVE", true);
        intentActive.putExtra("CUSTOMER_ID", customerId);
        ((AppCompatActivity) appContext).startActivity(intentActive);
        break;

      case R.id.btnViewInactiveAccounts:
        Intent intentInactive = new Intent(this.appContext, CustomerAccountsResultView.class);
        intentInactive.putExtra("ADMIN_INTERFACE", adminInterface);
        intentInactive.putExtra("ACTIVE", false);
        intentInactive.putExtra("CUSTOMER_ID", customerId);
        ((AppCompatActivity) appContext).startActivity(intentInactive);
        break;
    }
  }
}

