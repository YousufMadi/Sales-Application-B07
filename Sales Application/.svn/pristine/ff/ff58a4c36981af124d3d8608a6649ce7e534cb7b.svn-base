package com.b07.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.b07.R;
import com.b07.store.AdminInterface;
import com.b07.ui.admin.controllers.PromoteButtonController;

public class PromoteEmployeeView extends AppCompatActivity {

  /**
   * On create method to change screen to new user activity.
   *
   * @param savedInstanceState is a saved instance.
   */

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_promote_employee);
    Intent intent = getIntent();
    AdminInterface adminInterface = (AdminInterface) intent.getSerializableExtra("ADMIN_INTERFACE");
    Button promoteEmployee = (Button) findViewById(R.id.btnPromote);
    EditText employeeId = (EditText) findViewById(R.id.editPromoteEmployeeId);

    promoteEmployee.setOnClickListener(new PromoteButtonController(this, employeeId, adminInterface));

  }
}
