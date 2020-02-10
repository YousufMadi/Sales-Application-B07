package com.b07.ui.admin.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.b07.R;
import com.b07.store.AdminInterface;
import com.b07.ui.admin.AdminInventoryView;
import com.b07.ui.admin.CustomerAccountsView;
import com.b07.ui.admin.PromoteEmployeeView;
import com.b07.ui.admin.ViewBooksView;
import com.b07.ui.login.LoginActivity;

public class AdminViewButtonController implements View.OnClickListener {
  private Context appContext;
  private AdminInterface adminInterface;

  public AdminViewButtonController(Context context, AdminInterface adminInterface) {
    this.appContext = context;
    this.adminInterface = adminInterface;
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.btnAdminPromoteEmployee:
        Intent intentPromote = new Intent(this.appContext, PromoteEmployeeView.class);
        intentPromote.putExtra("ADMIN_INTERFACE", adminInterface);
        ((AppCompatActivity) appContext).startActivity(intentPromote);
        break;

      case R.id.btnAdminViewInventory:
        Intent intentViewInventory = new Intent(this.appContext, AdminInventoryView.class);
        intentViewInventory.putExtra("ADMIN_INTERFACE", adminInterface);
        ((AppCompatActivity) appContext).startActivity(intentViewInventory);
        break;

      case R.id.btnAdminViewBooks:
        Intent intentViewBooks = new Intent(this.appContext, ViewBooksView.class);
        intentViewBooks.putExtra("ADMIN_INTERFACE", adminInterface);
        ((AppCompatActivity) appContext).startActivity(intentViewBooks);
        break;

      case R.id.btnAdminViewAccounts:
        Intent intentAccounts = new Intent(this.appContext, CustomerAccountsView.class);
        intentAccounts.putExtra("ADMIN_INTERFACE", adminInterface);
        ((AppCompatActivity) appContext).startActivity(intentAccounts);
        break;

      case R.id.btnAdminLogout:
        Intent intentLogout = new Intent(this.appContext, LoginActivity.class);
        Activity activity = (Activity) appContext;
        activity.finish();
        appContext.startActivity(intentLogout);
    }
  }
}
