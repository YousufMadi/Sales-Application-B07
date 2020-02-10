package com.b07.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.b07.R;
import com.b07.store.AdminInterface;

public class CustomerAccountsResultView extends AppCompatActivity {
  private TextView accounts;
  private int customerId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_customer_accs);

    Intent intent = getIntent();
    AdminInterface adminInterface = (AdminInterface) intent.getSerializableExtra("ADMIN_INTERFACE");
    boolean active = (boolean) intent.getBooleanExtra("ACTIVE", true);
    customerId = (int) intent.getIntExtra("CUSTOMER_ID", 0);

    accounts = (TextView) findViewById(R.id.txtPlaceAccounts);

    String output = adminInterface.viewAccounts(this, customerId, active);
    accounts.setText(output);

  }
}
