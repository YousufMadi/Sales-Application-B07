package com.b07.ui.admin.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.b07.store.AdminInterface;
import com.b07.ui.login.LoginActivity;

import java.io.IOException;

public class DeserializeButtonController implements View.OnClickListener {
  private Context appContext;
  private AdminInterface adminInterface;
  Toast message;

  public DeserializeButtonController(Context context, AdminInterface adminInterface) {
    this.appContext = context;
    this.adminInterface = adminInterface;
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  @Override
  public void onClick(View view) {
    try {
      if (adminInterface.deserializeDatabase(appContext, appContext.getFilesDir() + "/database_copy.ser")) {
        message = Toast.makeText(appContext, "Deserialized Successfully! Please log back in!", Toast.LENGTH_SHORT);
        message.show();
        logout();
      } else {
        message = Toast.makeText(appContext, "Deserialization error!", Toast.LENGTH_SHORT);
        message.show();
      }
    } catch (IOException e) {
      message = Toast.makeText(appContext, "Serialized Version not Found!", Toast.LENGTH_SHORT);
      message.show();
      e.printStackTrace();
    } catch (Exception e) {
      message = Toast.makeText(appContext, "An Unexpected Error has Occurred!", Toast.LENGTH_SHORT);
      message.show();
    }
  }

  private void logout() {
    Intent intentLogout = new Intent(this.appContext, LoginActivity.class);
    Activity activity = (Activity) appContext;
    activity.finish();
    appContext.startActivity(intentLogout);
  }
}

