package com.b07.ui.customer.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.b07.ui.login.LoginActivity;

public class LogoutButtonController implements View.OnClickListener {
  private Context appContext;

  /**
   * Constructor for button controller.
   *
   * @param context context for app
   */
  public LogoutButtonController(Context context) {
    this.appContext = context;
  }

  /**
   * Perform functionality of button on click
   *
   * @param v unused
   */
  @Override
  public void onClick(View view) {
    Intent intentLogout = new Intent(this.appContext, LoginActivity.class);
    Activity activity = (Activity) appContext;
    activity.finish();
    appContext.startActivity(intentLogout);
  }
}
