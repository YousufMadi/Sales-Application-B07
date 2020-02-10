package com.b07.ui.customer.controllers;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.b07.database.DatabaseSelectorAndroid;
import com.b07.database.DatabaseUpdaterAndroid;
import com.b07.inventory.Item;
import com.b07.store.ShoppingCart;
import com.b07.ui.customer.MainCustomerView;
import com.b07.users.Account;
import com.b07.users.AccountSummary;
import com.b07.users.Customer;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class RestoreButtonController implements View.OnClickListener {
  private Context appContext;
  private Customer customer;
  private ShoppingCart cart;
  private DatabaseSelectorAndroid dbSelect;
  private DatabaseUpdaterAndroid dbUpdate;
  private TextView inventory;
  private Button restoreButton;
  Toast message;

  /**
   * Constructor for button controller
   *
   * @param context       context for app
   * @param customer      customer user
   * @param cart          customer's shoppiing cart
   * @param inventory     textview for output
   * @param restoreButton button for restore cart
   */
  public RestoreButtonController(Context context, Customer customer, ShoppingCart cart,
                                 TextView inventory, Button restoreButton) {
    this.appContext = context;
    this.customer = customer;
    this.cart = cart;
    this.inventory = inventory;
    this.restoreButton = restoreButton;
    dbSelect = new DatabaseSelectorAndroid(context);
    dbUpdate = new DatabaseUpdaterAndroid(context);
  }

  /**
   * Perform functionality of button on click
   *
   * @param v unused
   */
  @Override
  public void onClick(View v) {
    try {

      List<Account> accounts = dbSelect.getUserActiveAccounts(cart.getCustomer().getId());
      if (accounts.size() != 0) {
        AccountSummary accSummary = dbSelect.getAccountDetails(accounts.get(0).getId());
        HashMap<Item, Integer> summary = accSummary.getSummary();

        if (summary.size() != 0) {
          for (Item i : summary.keySet()) {
            cart.addItem(i, summary.get(i));
          }
          message = Toast.makeText(appContext, "Cart Restored", Toast.LENGTH_SHORT);
          message.show();
          MainCustomerView.printCart(cart, inventory);
          restoreButton.setEnabled(false);
          MainCustomerView.restoreButtonClicked = true;
        } else {
          message = Toast.makeText(appContext, "Cart Restore Failed: No stored cart was found",
              Toast.LENGTH_SHORT);
          message.show();
        }
      } else {
        message = Toast.makeText(appContext, "Customer does not have an active account",
            Toast.LENGTH_SHORT);
        message.show();
      }
    } catch (SQLException e) {
      message = Toast.makeText(appContext, "Error connecting to database",
          Toast.LENGTH_SHORT);
      message.show();
    }
  }
}
