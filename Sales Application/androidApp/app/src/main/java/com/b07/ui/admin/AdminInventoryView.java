package com.b07.ui.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.b07.R;
import com.b07.store.AdminInterface;

import java.sql.SQLException;

public class AdminInventoryView extends AppCompatActivity {
  Toast message;

  /**
   * On create method to change screen to new user activity.
   *
   * @param savedInstanceState is a saved instance.
   */

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_admin_inventory_view);
    Intent intent = getIntent();
    AdminInterface adminInterface = (AdminInterface) intent.getSerializableExtra("ADMIN_INTERFACE");
    TextView inventory = (TextView) findViewById(R.id.placeQuantityHere);
    inventory.setText(inventoryPrinter(this, adminInterface));
  }

  /**
   * Method to print inventory.
   *
   * @param context        is the current context of the app.
   * @param adminInterface is the admin's interface.
   */

  protected String inventoryPrinter(Context context, AdminInterface adminInterface) {
    String result = "";
    try {
      result = adminInterface.viewInventory(this);
    } catch (SQLException e) {
      message = Toast.makeText(this, "Database Error!", Toast.LENGTH_SHORT);
      message.show();
    }
    return result;
  }
}
