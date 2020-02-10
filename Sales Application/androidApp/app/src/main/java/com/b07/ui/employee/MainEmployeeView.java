package com.b07.ui.employee;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.b07.R;
import com.b07.database.DatabaseSelectorAndroid;
import com.b07.store.EmployeeInterface;
import com.b07.ui.employee.controllers.EmployeeMenuButtonController;
import com.b07.users.Employee;


public class MainEmployeeView extends AppCompatActivity {
  /**
   * On create method to change view to main employee menu
   *
   * @param savedInstanceState is a saved instance
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_employee_view);

    Intent intent = getIntent();
    Employee employee = (Employee) intent.getSerializableExtra("USER");
    EmployeeInterface employeeInterface = getEmployeeInterface(employee);

    Button btnAuthenticate = (Button) findViewById(R.id.btnEmployeeMenuAuthenticate);
    Button btnMakeNewAccount = (Button) findViewById(R.id.btnEmployeeMenuCreateAccount);
    Button btnLogout = (Button) findViewById(R.id.btnEmployeeMenuLogOut);
    Button btnRestock = (Button) findViewById(R.id.btnEmployeeMenuRestock);
    Button btnNewUser = (Button) findViewById(R.id.btnEmployeeMenuCreateUser);

    btnAuthenticate.setOnClickListener(new EmployeeMenuButtonController(this, employeeInterface));
    btnMakeNewAccount.setOnClickListener(new EmployeeMenuButtonController(this, employeeInterface));
    btnLogout.setOnClickListener(new EmployeeMenuButtonController(this, employeeInterface));
    btnRestock.setOnClickListener(new EmployeeMenuButtonController(this, employeeInterface));
    btnNewUser.setOnClickListener(new EmployeeMenuButtonController(this, employeeInterface));
  }

  /**
   * gets employee interface
   *
   * @param employee the employee
   * @return an employee interface
   */
  private EmployeeInterface getEmployeeInterface(Employee employee) {
    EmployeeInterface employeeInterface = null;
    try {
      DatabaseSelectorAndroid dbSelect = new DatabaseSelectorAndroid(this);
      employeeInterface = new EmployeeInterface(this, employee, dbSelect.getInventory());
    } catch (Exception e) {
      Toast message = Toast.makeText(this, "An error has occurred!", Toast.LENGTH_SHORT);
      message.show();
    } finally {
      return employeeInterface;
    }
  }
}