package com.b07.ui.customer.controllers;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.b07.database.DatabaseSelectorAndroid;
import com.b07.database.DatabaseUpdaterAndroid;
import com.b07.exceptions.ConstraintViolationException;
import com.b07.store.ShoppingCart;
import com.b07.ui.customer.MainCustomerView;
import com.b07.users.Account;
import com.b07.users.AccountSummary;
import com.b07.users.Customer;

import java.sql.SQLException;
import java.util.List;

public class CheckoutButtonController implements View.OnClickListener {
  private Context appContext;
  private Customer customer;
  private ShoppingCart cart;
  private boolean restoreButtonEnabled;
  private TextView inventory;
  private DatabaseSelectorAndroid dbSelect;
  private DatabaseUpdaterAndroid dbUpdate;
  Toast message;

  /**
   * Constructor for button controller.
   *
   * @param context              context for app
   * @param customer             customer user
   * @param cart                 customer's shopping cart
   * @param restoreButtonEnabled true if restore button is clicked false otherwise
   * @param inventory            textview for output
   */
  public CheckoutButtonController(Context context, Customer customer, ShoppingCart cart,
                                  boolean restoreButtonEnabled, TextView inventory) {
    this.appContext = context;
    this.customer = customer;
    this.cart = cart;
    this.restoreButtonEnabled = restoreButtonEnabled;
    this.inventory = inventory;
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
      boolean checkout = cart.checkOutCustomer();
      if (cart.getItems().size() == 0) {
        message = Toast.makeText(appContext, "You cannot checkout with an empty cart",
            Toast.LENGTH_SHORT);
        message.show();
      } else if (checkout && MainCustomerView.restoreButtonClicked) {
        List<Account> accounts = dbSelect.getUserActiveAccounts(cart.getCustomer().getId());
        AccountSummary accSummary = dbSelect.getAccountDetails(accounts.get(0).getId());
        int accountID = accSummary.getId();
        dbUpdate.updateAccountStatus(accountID, false);
        cart.clearCart();
        message = Toast.makeText(appContext, "You have successfully checked out, " +
                "cart has been cleared",
            Toast.LENGTH_SHORT);
        message.show();
        MainCustomerView.printCart(cart, inventory);
      } else if (checkout && !MainCustomerView.restoreButtonClicked) {
        cart.clearCart();
        message = Toast.makeText(appContext, "You have successfully checked out, " +
                "cart has been cleared",
            Toast.LENGTH_SHORT);
        message.show();
        MainCustomerView.printCart(cart, inventory);
      } else {
        message = Toast.makeText(appContext, "Some item is out of stock!",
            Toast.LENGTH_SHORT);
        message.show();
      }
    } catch (SQLException e) {
      message = Toast.makeText(appContext, "Error connecting to database",
          Toast.LENGTH_SHORT);
      message.show();
    } catch (ConstraintViolationException e) {
      message = Toast.makeText(appContext, "Constraint Violation",
          Toast.LENGTH_SHORT);
      message.show();
    }
  }
}
