package com.b07.users;

import com.b07.inventory.Item;

import java.util.HashMap;

public interface AccountSummary {

  /**
   * Gets the id.
   *
   * @return the id.
   */
  public int getId();

  /**
   * Add an item and quantity to account summary.
   *
   * @param item     is the item to be added.
   * @param quantity is the quantity to be added.
   */
  public void addItem(Item item, int quantity);

  /**
   * Return the summary of the account.
   *
   * @return the hashmap containing the item and its quantity.
   */
  public HashMap<Item, Integer> getSummary();
}
