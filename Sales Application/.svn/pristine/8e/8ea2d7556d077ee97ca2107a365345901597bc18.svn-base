package com.b07.ui.employee;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.b07.R;
import com.b07.store.EmployeeInterface;
import com.b07.ui.employee.controllers.RestockInventoryController;

public class RestockInventoryView extends AppCompatActivity {

  /**
   * On create method to change view to restock inventory view
   *
   * @param savedInstanceState is a saved Instance.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_restock_inventory);

    Intent intent = getIntent();
    EmployeeInterface employeeInterface = (EmployeeInterface) intent.getSerializableExtra("EMPLOYEE_INTERFACE");

    Button restock = (Button) findViewById(R.id.btnRestock);
    restock.setOnClickListener(new RestockInventoryController(this, employeeInterface));
  }
}
