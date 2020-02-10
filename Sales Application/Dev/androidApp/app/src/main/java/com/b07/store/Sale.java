package com.b07.store;

import com.b07.inventory.Item;
import com.b07.users.User;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * The Interface Sale.
 */
public interface Sale {

  /**
   * Gets the id.
   *
   * @return the id
   */
  public int getId();

  /**
   * Sets the id.
   *
   * @param id the new id
   * @return
   */
  public SaleImpl setId(int id);

  /**
   * Gets the user.
   *
   * @return the user
   */
  public User getUser();

  /**
   * Sets the user.
   *
   * @param user the new user
   * @return
   */
  public SaleImpl setUser(User user);

  /**
   * Gets the total price.
   *
   * @return the total price
   */
  public BigDecimal getTotalPrice();

  /**
   * Sets the total price.
   *
   * @param price the new total price
   * @return
   */
  public SaleImpl setTotalPrice(BigDecimal price);

  /**
   * Gets the item map.
   *
   * @return the item map
   */
  public HashMap<Item, Integer> getItemMap();

  /**
   * Sets the hash map.
   *
   * @param itemMap the item map
   * @return
   */
  public SaleImpl setItemMap(HashMap<Item, Integer> itemMap);
}
