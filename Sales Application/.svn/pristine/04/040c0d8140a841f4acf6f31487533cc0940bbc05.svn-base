package com.b07.ui.employee;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.b07.R;
import com.b07.store.EmployeeInterface;
import com.b07.ui.employee.controllers.MakeNewAccountButtonController;

public class MakeNewAccountView extends AppCompatActivity {

  /**
   * On create method to change screen to new account activity.
   *
   * @param savedInstanceState is a saved instance
   */

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_account);

    Intent intent = getIntent();
    EmployeeInterface employeeInterface = (EmployeeInterface) intent.getSerializableExtra("EMPLOYEE_INTERFACE");
    Button btnCreate = findViewById(R.id.btnCreateAccount);
    EditText editId = findViewById(R.id.editCreateAccountId);

    btnCreate.setOnClickListener(new MakeNewAccountButtonController(this, employeeInterface, editId));
  }
}
