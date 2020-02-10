package com.b07.store;

import android.content.Context;

import com.b07.database.DatabaseInserterAndroid;
import com.b07.database.DatabaseSelectorAndroid;
import com.b07.database.DatabaseUpdaterAndroid;
import com.b07.exceptions.AuthenticationFailedException;
import com.b07.exceptions.ConstraintViolationException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.inventory.Item;
import com.b07.users.Customer;
import com.b07.users.Roles;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The Class ShoppingCart.
 */
public class ShoppingCart implements Serializable {
  private HashMap<Item, Integer> cart = new HashMap<Item, Integer>();
  private Customer customer;
  private BigDecimal total = new BigDecimal(0);
  private final BigDecimal TAXRATE = new BigDecimal(1.13);
  private DatabaseSelectorAndroid dbSelect;
  private DatabaseUpdaterAndroid dbUpdate;
  private DatabaseInserterAndroid dbInsert;

  /**
   * Instantiates a new shopping cart.
   *
   * @param customer the customer
   * @throws SQLException                  the SQL exception
   * @throws AuthenticationFailedException when failed to authenticate user
   */
  public ShoppingCart(Context appContext, Customer customer) throws SQLException, AuthenticationFailedException {
    if (customer.authenticated(appContext, null)) {
      this.customer = customer;
      dbSelect = new DatabaseSelectorAndroid(appContext);
      dbUpdate = new DatabaseUpdaterAndroid(appContext);
      dbInsert = new DatabaseInserterAndroid(appContext);
    } else {
      throw new AuthenticationFailedException();
    }
  }

  /**
   * Adds the item.
   *
   * @param item     the item
   * @param quantity the quantity
   */
  public void addItem(Item item, int quantity) {
    Integer existingQuantity = cart.get(item);

    if (existingQuantity == null) {
      cart.put(item, quantity);
    } else {
      cart.put(item, existingQuantity + quantity);
    }
    total = total.add(item.getPrice().multiply(new BigDecimal(quantity)));
  }

  /**
   * Removes the item.
   *
   * @param item     the item
   * @param quantity the quantity
   */
  public void removeItem(Item item, int quantity) {
    Integer existingQuantity = cart.get(item);

    if (existingQuantity != null) {
      if (existingQuantity > quantity) {
        cart.put(item, existingQuantity - quantity);
        total = total.subtract(item.getPrice().multiply(new BigDecimal(quantity)));
      } else if (existingQuantity == quantity) {
        cart.remove(item);
        total = total.subtract(item.getPrice().multiply(new BigDecimal(quantity)));
      }
    }
  }

  /**
   * Gets the items.
   *
   * @return the items
   */
  public List<Item> getItems() {
    List<Item> result = new ArrayList<Item>();

    for (Item i : this.cart.keySet()) {
      result.add(i);
    }

    return result;
  }


  /**
   * Gets the cart.
   *
   * @return the cart
   */
  public HashMap<Item, Integer> getCart() {
    return this.cart;
  }

  /**
   * Gets the customer.
   *
   * @return the customer
   */
  public Customer getCustomer() {
    return this.customer;
  }

  /**
   * Gets the price.
   *
   * @return the price
   */
  public BigDecimal getTaxRate() {
    return this.TAXRATE;
  }

  /**
   * Gets the total.
   *
   * @return the total
   */
  public BigDecimal getTotal() {
    return this.total;
  }

  /**
   * Clear cart.
   */
  public void clearCart() {
    cart = new HashMap<Item, Integer>();
    total = BigDecimal.ZERO;
  }

  /**
   * Check out customer.
   *
   * @return true, if successful
   * @throws SQLException                 when an unexpected sql exception occured
   * @throws ConstraintViolationException when input constraints are not met
   */
  public boolean checkOutCustomer() throws SQLException, ConstraintViolationException {
    int roleId;
    roleId = dbSelect.getUserRoleId(this.customer.getId());
    String role = dbSelect.getRoleName(roleId);

    // validate user is customer
    if (Roles.getRole(role).equals(Roles.CUSTOMER)) {
      // check if inventory holds enough items in inventory
      for (Item i : this.cart.keySet()) {
        int invQuantity = dbSelect.getInventoryQuantity(i.getId());
        if (invQuantity < this.cart.get(i)) {
          return false;
        }
      }

      // Subtract cart items from inventory
      for (Item i : this.cart.keySet()) {
        int currentQuantity = dbSelect.getInventoryQuantity(i.getId());
        int cartQuantity = cart.get(i);
        try {
          dbUpdate.updateInventoryQuantity(currentQuantity - cartQuantity, i.getId());
        } catch (ConstraintViolationException e) {
          return false;
        }
      }

      try {
        // insert sale
        int saleId = dbInsert.insertSale(this.customer.getId(),
            this.total.multiply(this.TAXRATE).setScale(2, RoundingMode.CEILING));

        // insert itemized sales
        for (Item i : this.cart.keySet()) {
          dbInsert.insertItemizedSale(saleId, i.getId(), this.cart.get(i));
        }
      } catch (DatabaseInsertException e) {
        return false;
      }
    } else {
      return false;
    }
    return true;
  }
}
