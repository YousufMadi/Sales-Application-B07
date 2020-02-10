package com.b07.ui.customer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.b07.R;
import com.b07.exceptions.AuthenticationFailedException;
import com.b07.inventory.Item;
import com.b07.store.ShoppingCart;
import com.b07.ui.customer.controllers.AddItemButtonController;
import com.b07.ui.customer.controllers.CheckoutButtonController;
import com.b07.ui.customer.controllers.LogoutButtonController;
import com.b07.ui.customer.controllers.RemoveItemButtonController;
import com.b07.ui.customer.controllers.RestoreButtonController;
import com.b07.ui.customer.controllers.SaveCartButtonController;
import com.b07.users.Customer;

import java.math.BigDecimal;
import java.sql.SQLException;

public class MainCustomerView extends AppCompatActivity {
  //checks whether restore button has been clicked
  public static boolean restoreButtonClicked = false;

  /**
   * On create method to change screen to new user activity.
   *
   * @param savedInstanceState is a saved instance.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.user_view);
    Intent intent = getIntent();
    Customer customer = (Customer) intent.getSerializableExtra("USER");

    ShoppingCart cart = null;
    Toast message;
    try {
      cart = new ShoppingCart(this, customer);
      Button addItemButton = (Button) findViewById(R.id.btnShoppingAddItem);
      Button removeItemButton = (Button) findViewById(R.id.btnShoppingRemoveItem);
      Button checkoutButton = (Button) findViewById(R.id.btnCheckout);
      Button restoreButton = (Button) findViewById(R.id.btnRestoreCart);
      Button saveCartButton = (Button) findViewById(R.id.btnSaveCart);
      Button logoutButton = (Button) findViewById(R.id.btnCustomerLogout);

      TextView inventory = (TextView) findViewById(R.id.inventory);
      EditText itemQuantity = (EditText) findViewById(R.id.editShoppingQuantity);
      Spinner spinner = (Spinner) findViewById(R.id.spinner);

      addItemButton.setOnClickListener(new AddItemButtonController(this, itemQuantity,
          customer, cart, spinner, inventory));
      removeItemButton.setOnClickListener(new RemoveItemButtonController(this,
          itemQuantity, customer, cart, spinner, inventory));
      saveCartButton.setOnClickListener(new SaveCartButtonController(this, customer,
          cart));
      restoreButton.setOnClickListener(new RestoreButtonController(this, customer, cart,
          inventory, restoreButton));
      checkoutButton.setOnClickListener(new CheckoutButtonController(this, customer,
          cart, restoreButton.isEnabled(), inventory));
      logoutButton.setOnClickListener(new LogoutButtonController(this));
    } catch (AuthenticationFailedException e) {
      message = Toast.makeText(this, "Could not authenticate customer",
          Toast.LENGTH_SHORT);
      message.show();
    } catch (SQLException e) {
      message = Toast.makeText(this, "Error connecting to database",
          Toast.LENGTH_SHORT);
      message.show();
    }
  }

  /**
   * Print the current contents of the cart
   *
   * @param cart      the current User's shopping cart
   * @param inventory The text view where contents are displayed
   */
  public static void printCart(ShoppingCart cart, TextView inventory) {
    String cartItems = "";
    BigDecimal total = new BigDecimal(0.00);
    for (Item i : cart.getCart().keySet()) {
      cartItems += i.getName() + ": " + cart.getCart().get(i) + "\n";
      BigDecimal itemCost = i.getPrice().multiply(new BigDecimal(cart.getCart().get(i)));
      total = total.add(itemCost);
    }
    cartItems += "Total Price: $" + total;
    inventory.setText(cartItems);
  }
}
