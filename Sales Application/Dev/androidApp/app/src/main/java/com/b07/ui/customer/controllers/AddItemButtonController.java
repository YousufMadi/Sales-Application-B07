package com.b07.ui.customer.controllers;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.b07.database.DatabaseSelectorAndroid;
import com.b07.database.validator.InputValidator;
import com.b07.exceptions.ConstraintViolationException;
import com.b07.inventory.Item;
import com.b07.inventory.ItemTypes;
import com.b07.store.ShoppingCart;
import com.b07.ui.customer.MainCustomerView;
import com.b07.users.Customer;

import java.sql.SQLException;

public class AddItemButtonController implements View.OnClickListener {
  private Context appContext;
  private DatabaseSelectorAndroid dbSelect;
  private Customer customer;
  private EditText itemQuantity;
  private ShoppingCart cart;
  private Spinner spinner;
  private TextView inventory;
  Toast message;

  /**
   * Constructor for button controller.
   *
   * @param context      context of the app
   * @param itemQuantity quantity entered by user
   * @param customer     customer user
   * @param cart         customer's shopping cart
   * @param spinner      spinner of items
   * @param inventory    textview for output
   */
  public AddItemButtonController(Context context, EditText itemQuantity, Customer customer,
                                 ShoppingCart cart, Spinner spinner, TextView inventory) {
    this.appContext = context;
    this.itemQuantity = itemQuantity;
    this.customer = customer;
    this.cart = cart;
    this.spinner = spinner;
    this.inventory = inventory;
    dbSelect = new DatabaseSelectorAndroid(context);
  }

  /**
   * Perform functionality of button on click
   *
   * @param v unused
   */
  @Override
  public void onClick(View v) {
    int quantity = 0;
    String itemName = (String) spinner.getSelectedItem();
    int itemId = ItemTypes.valueOf(itemName).ordinal() + 1;

    try {
      quantity = Integer.parseInt(itemQuantity.getText().toString());
      InputValidator.validateItemQuantity(quantity);
    } catch (ConstraintViolationException | NumberFormatException e) {
      message = Toast.makeText(appContext, "Please enter a valid quantity",
          Toast.LENGTH_SHORT);
      message.show();
      return;
    }

    try {
      Item item = dbSelect.getItem(itemId);
      if (item == null) {
        message = Toast.makeText(appContext, "Item does not exist!",
            Toast.LENGTH_SHORT);
        message.show();
        return;
      }

      cart.addItem(item, quantity);
      MainCustomerView.printCart(cart, inventory);
      message = Toast.makeText(appContext, "Added " + quantity + " of " + itemName, Toast.LENGTH_SHORT);
      message.show();
    } catch (SQLException e) {
      message = Toast.makeText(appContext, "Error connecting to database",
          Toast.LENGTH_SHORT);
      message.show();
    }
  }
}
