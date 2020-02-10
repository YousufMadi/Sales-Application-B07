package com.b07.inventory;

import java.io.Serializable;
import java.util.HashMap;

public class InventoryImpl implements Inventory, Serializable {
  /**
   * Serial Id for inventoryimpl object
   */
  private static final long serialVersionUID = -6272231171444086718L;
  private HashMap<Item, Integer> inventory;
  private int total;

  public InventoryImpl(HashMap<Item, Integer> inventory, int totalItems) {
    setItemMap(inventory);
  }

  /**
   * Gets the item map
   *
   * @return the item map
   */
  @Override
  public HashMap<Item, Integer> getItemMap() {
    return inventory;
  }

  /**
   * Sets the item map.
   *
   * @param itemMap the item map
   */
  @Override
  public void setItemMap(HashMap<Item, Integer> itemMap) {
    this.inventory = itemMap;
  }

  /**
   * Update map.
   *
   * @param item  the item
   * @param value the value
   */
  @Override
  public void updateMap(Item item, int value) {
    this.getItemMap().keySet();
    if (item != null) {
      inventory.put(item, value);
    }
  }

  /**
   * Gets the total items.
   *
   * @return the total items
   */
  @Override
  public int getTotalItems() {
    return this.total;
  }

  /**
   * Sets the total items.
   *
   * @param total the new total items
   */
  @Override
  public void setTotalItems(int total) {
    this.total = total;
  }
}
