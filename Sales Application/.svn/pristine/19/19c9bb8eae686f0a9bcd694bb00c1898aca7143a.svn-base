package com.b07.ui.employee.controllers;


import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.b07.R;
import com.b07.database.DatabaseSelectorAndroid;
import com.b07.database.DatabaseUpdaterAndroid;
import com.b07.database.validator.InputValidator;
import com.b07.exceptions.ConstraintViolationException;
import com.b07.inventory.Item;
import com.b07.store.EmployeeInterface;

import java.sql.SQLException;
import java.util.List;


public class RestockInventoryController extends AppCompatActivity implements View.OnClickListener {
  private Context appContext;
  private EmployeeInterface employeeInterface;
  private DatabaseUpdaterAndroid dbUpdate;
  private DatabaseSelectorAndroid dbSelect;
  Toast message;


  /**
   * Constructor to create the controller.
   *
   * @param context           is the context.
   * @param employeeInterface is the employee interface from the assignment.
   */
  public RestockInventoryController(Context context, EmployeeInterface employeeInterface) {
    this.appContext = context;
    this.employeeInterface = employeeInterface;
  }

  /**
   * Onclick method to restock inventory.
   *
   * @param view is the view.
   */
  @Override
  public void onClick(View view) {
    Spinner itemSpinner = (Spinner) ((AppCompatActivity) appContext).findViewById(R.id.spinnerRestockInventory);
    EditText quantityTextView = (EditText) ((AppCompatActivity) appContext).findViewById(R.id.editRestockQuantity);
    dbUpdate = new DatabaseUpdaterAndroid(this.appContext);
    dbSelect = new DatabaseSelectorAndroid(this.appContext);

    restockItem(itemSpinner, quantityTextView);
  }

  /**
   * method to restock item and upload to database.
   *
   * @param itemSpinner      is the spinner.
   * @param quantityTextView is the textview for quantity.
   */
  private void restockItem(Spinner itemSpinner, EditText quantityTextView) {
    String itemName = itemSpinner.getSelectedItem().toString();

    int itemId = -1;
    try {
      List<Item> items = dbSelect.getAllItems();
      for (Item itemIterator : items) {
        String name = itemIterator.getName() + "";
        if (itemName.equals(name + "")) {
          itemId = itemIterator.getId();
        }
      }
    } catch (SQLException e) {
      message = Toast.makeText(appContext, "Database Error!", Toast.LENGTH_SHORT);
      message.show();
      return;
    }

    int quantity = -1;
    try {
      quantity = Integer.parseInt(quantityTextView.getText().toString());
      InputValidator.validateItemQuantity(quantity);
    } catch (NumberFormatException | ConstraintViolationException e) {
      message = Toast.makeText(appContext, "Invalid Item Quantity!", Toast.LENGTH_SHORT);
      message.show();
      return;
    }

    try {
      boolean result =
          employeeInterface.restockInventory(appContext, dbSelect.getItem(itemId), quantity);
      if (result) {
        message = Toast.makeText(appContext, "Quantity updated successfully! There are " + dbSelect.getInventoryQuantity(itemId) + " " + itemName + "s in the inventory now", Toast.LENGTH_SHORT);
        message.show();
      } else {
        message = Toast.makeText(appContext, "Process Failed!", Toast.LENGTH_SHORT);
        message.show();
      }
    } catch (SQLException e) {
      message = Toast.makeText(appContext, "Database Error!", Toast.LENGTH_SHORT);
      message.show();
    } catch (ConstraintViolationException e) {
      message = Toast.makeText(appContext, "Input Error!", Toast.LENGTH_SHORT);
      message.show();
    }
  }
}
