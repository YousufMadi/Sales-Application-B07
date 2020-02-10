package com.b07.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.b07.R;
import com.b07.store.AdminInterface;
import com.b07.ui.admin.controllers.AdminViewButtonController;
import com.b07.ui.admin.controllers.DeserializeButtonController;
import com.b07.ui.admin.controllers.SerializeButtonController;
import com.b07.users.Admin;

public class MainAdminView extends AppCompatActivity {

  /**
   * On create method to change screen to new user activity.
   *
   * @param savedInstanceState is a saved instance.
   */

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_admin_view);

    Intent intent = getIntent();
    Admin admin = (Admin) intent.getSerializableExtra("USER");
    AdminInterface adminInterface = null;
    try {
      adminInterface = new AdminInterface(this, admin);
    } catch (Exception e) {
      Toast.makeText(this, "Error Occurred while creating an AdminInterface!", Toast.LENGTH_SHORT).show();
    }

    Button promoteButton = (Button) findViewById(R.id.btnAdminPromoteEmployee);
    Button booksButton = (Button) findViewById(R.id.btnAdminViewBooks);
    Button inventoryButton = (Button) findViewById(R.id.btnAdminViewInventory);
    Button logoutButton = (Button) findViewById(R.id.btnAdminLogout);
    Button serializeButton = (Button) findViewById(R.id.btnAdminSerialize);
    Button deserializeButton = (Button) findViewById(R.id.btnViewDeserialize);
    Button accountsButton = (Button) findViewById(R.id.btnAdminViewAccounts);

    promoteButton.setOnClickListener(new AdminViewButtonController(this, adminInterface));
    booksButton.setOnClickListener(new AdminViewButtonController(this, adminInterface));
    inventoryButton.setOnClickListener(new AdminViewButtonController(this, adminInterface));
    logoutButton.setOnClickListener(new AdminViewButtonController(this, adminInterface));
    serializeButton.setOnClickListener(new SerializeButtonController(this, adminInterface));
    deserializeButton.setOnClickListener(new DeserializeButtonController(this, adminInterface));
    accountsButton.setOnClickListener(new AdminViewButtonController(this, adminInterface));

  }

}
