package com.b07.ui.employee;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.b07.R;
import com.b07.store.EmployeeInterface;
import com.b07.ui.employee.controllers.MakeNewUserButtonController;

public class MakeNewUserView extends AppCompatActivity {

  /**
   * On create method to change screen to new user activity.
   *
   * @param savedInstanceState is a saved instance.
   */

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_new_user);

    Intent intent = getIntent();
    EmployeeInterface employeeInterface = (EmployeeInterface) intent.getSerializableExtra("EMPLOYEE_INTERFACE");

    Button createUser = (Button) findViewById(R.id.btnNewUserCreate);
    createUser.setOnClickListener(new MakeNewUserButtonController(this, employeeInterface));


  }
}
