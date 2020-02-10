package com.b07.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.b07.R;
import com.b07.store.AdminInterface;
import com.b07.ui.admin.controllers.AccountsButtonControllers;

public class CustomerAccountsView extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_customer_accounts_view);

    Intent intent = getIntent();
    AdminInterface adminInterface = (AdminInterface) intent.getSerializableExtra("ADMIN_INTERFACE");

    EditText editCustomerId = (EditText) findViewById(R.id.editActiveAccountsCustomerId);
    Button btnActive = (Button) findViewById(R.id.btnViewActiveAccounts);
    Button btnInactive = (Button) findViewById(R.id.btnViewInactiveAccounts);

    btnActive.setOnClickListener(new AccountsButtonControllers(this, adminInterface, editCustomerId));
    btnInactive.setOnClickListener(new AccountsButtonControllers(this, adminInterface, editCustomerId));
  }
}
