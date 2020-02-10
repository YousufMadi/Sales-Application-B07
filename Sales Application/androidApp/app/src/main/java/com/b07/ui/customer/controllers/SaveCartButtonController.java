package com.b07.ui.customer.controllers;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.b07.database.DatabaseInserterAndroid;
import com.b07.database.DatabaseSelectorAndroid;
import com.b07.exceptions.ConstraintViolationException;
import com.b07.inventory.Item;
import com.b07.store.ShoppingCart;
import com.b07.users.Account;
import com.b07.users.AccountSummary;
import com.b07.users.Customer;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class SaveCartButtonController implements View.OnClickListener {
  private Context appContext;
  private Customer customer;
  private ShoppingCart cart;
  DatabaseInserterAndroid dbInsert;
  DatabaseSelectorAndroid dbSelect;
  Toast message;

  /**
   * Constructor for button controller
   *
   * @param context  context for app
   * @param customer customer user
   * @param cart     customer's shopping cart
   */
  public SaveCartButtonController(Context context, Customer customer, ShoppingCart cart) {
    this.appContext = context;
    this.customer = customer;
    this.cart = cart;
    dbInsert = new DatabaseInserterAndroid(context);
    dbSelect = new DatabaseSelectorAndroid(context);
  }

  /**
   * Perform functionality of button on click
   *
   * @param v unused
   */
  @Override
  public void onClick(View v) {
    try {
      HashMap<Item, Integer> sCart = cart.getCart();
      List<Account> account = dbSelect.getUserActiveAccounts(customer.getId());
      if (account.size() != 0 && sCart.keySet().size() != 0) {
        AccountSummary summary = dbSelect.getAccountDetails(account.get(0).getId());
        if (summary.getSummary().size() == 0) {
          for (Item i : sCart.keySet()) {
            dbInsert.insertAccountLine(account.get(0).getId(), i.getId(), sCart.get(i));
          }
          message = Toast.makeText(appContext, "Cart Saved To Account ID: " +
              account.get(0).getId(), Toast.LENGTH_SHORT);
          message.show();
        } else {
          message = Toast.makeText(appContext, "Customer active account already " +
              "in use", Toast.LENGTH_SHORT);
          message.show();
        }
      } else {
        message = Toast.makeText(appContext, "Customer doesn't have an active account" +
            " to save items on", Toast.LENGTH_SHORT);
        message.show();
      }
    } catch (SQLException e) {
      message = Toast.makeText(appContext, "Error connecting to database",
          Toast.LENGTH_SHORT);
      message.show();
    } catch (ConstraintViolationException e) {
      message = Toast.makeText(appContext, "Constraint violation", Toast.LENGTH_SHORT);
      message.show();
    }

  }
}
