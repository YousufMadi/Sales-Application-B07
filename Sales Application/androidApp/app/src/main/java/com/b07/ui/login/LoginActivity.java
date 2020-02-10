package com.b07.ui.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.b07.R;
import com.b07.database.DatabaseInserterAndroid;
import com.b07.database.DatabaseSelectorAndroid;
import com.b07.inventory.ItemTypes;
import com.b07.users.Roles;

import java.math.BigDecimal;
import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity {
  private DatabaseSelectorAndroid dbSelect = new DatabaseSelectorAndroid(this);
  private DatabaseInserterAndroid dbInsert = new DatabaseInserterAndroid(this);
  private Toast message;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    // Initiate Database
    firstTimeSetUp();

    // Button Controller
    EditText editId = (EditText) findViewById(R.id.editLoginId);
    EditText editPassword = (EditText) findViewById(R.id.editLoginPassword);
    Button btnLogin = (Button) findViewById(R.id.btnLogin);
    btnLogin.setOnClickListener(new LoginButtonController(this, editId, editPassword));
  }

  private void firstTimeSetUp() {
    int result = 0;

    // attempt to read database structure, should fail if not initialized
    try {
      result = dbSelect.getRoleId(Roles.ADMIN);
    } catch (SQLException e) {
      Toast toast = Toast.makeText(this, "Database not found", Toast.LENGTH_SHORT);
      toast.show();
    }

    if (result == 0) {
      try {
        int adminId = dbInsert.insertRole("ADMIN");
        int employeeId = dbInsert.insertRole("EMPLOYEE");
        int customerId = dbInsert.insertRole("CUSTOMER");
        int newUserId =
            dbInsert.insertNewUser("HeadAdmin", 1, "defaultLocation", "default123");
        int newUserId2 =
            dbInsert.insertNewUser("Employee", 2, "defaultLocation", "default123");
        int newUserId3 =
            dbInsert.insertNewUser("Customer", 3, "defaultLocation", "default123");
        dbInsert.insertUserRole(newUserId, adminId);
        dbInsert.insertUserRole(newUserId2, employeeId);
        dbInsert.insertUserRole(newUserId3, customerId);
        System.out.println("AdminId: " + newUserId + " Pass: " + "default123");
        System.out.println("EmployeeId: " + newUserId2 + " Pass: " + "default123");
        System.out.println("CustomerId: " + newUserId3 + " Pass: " + "default123");
        ItemTypes[] items = ItemTypes.class.getEnumConstants();
        for (ItemTypes i : items) {
          int itemId = dbInsert.insertItem(i.toString(), new BigDecimal(15));
          dbInsert.insertInventory(itemId, 10);
        }
        Toast toast = Toast.makeText(this, "Database initiated successfully", Toast.LENGTH_SHORT);
        toast.show();
      } catch (Exception e) {
        Toast toast = Toast.makeText(this, "Cannot connect  to database!", Toast.LENGTH_SHORT);
        e.printStackTrace();
        toast.show();
      }
    }
  }
}