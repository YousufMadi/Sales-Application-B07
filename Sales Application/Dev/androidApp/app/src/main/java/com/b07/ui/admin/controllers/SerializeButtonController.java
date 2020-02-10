package com.b07.ui.admin.controllers;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.b07.store.AdminInterface;

public class SerializeButtonController implements View.OnClickListener {
  private Context appContext;
  private AdminInterface adminInterface;
  Toast message;

  public SerializeButtonController(Context context, AdminInterface adminInterface) {
    this.appContext = context;
    this.adminInterface = adminInterface;
  }

  @Override
  public void onClick(View view) {
    try {
      adminInterface.serializeDatabase(appContext, appContext.getFilesDir() + "/database_copy.ser");
      message = Toast.makeText(appContext, "Serialized Successfully!", Toast.LENGTH_SHORT);
      message.show();
    } catch (Exception e) {
      message = Toast.makeText(appContext, "An Unexpected Error has Occurred!", Toast.LENGTH_SHORT);
      message.show();
    }
  }
}
