package com.b07.inventory;

import java.math.BigDecimal;

/**
 * The Interface Item.
 *
 * @author Junchao Chen
 */
public interface Item {

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
   */
  public void setId(int id);

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName();

  /**
   * Sets the name.
   *
   * @param name the new name
   */
  public void setName(String name);

  /**
   * Gets the price.
   *
   * @return the price
   */
  public BigDecimal getPrice();

  /**
   * Sets the price.
   *
   * @param price the new price
   */
  public void setPrice(BigDecimal price);
}
