package com.b07.inventory;

import java.io.Serializable;
import java.math.BigDecimal;

public class ItemImpl implements Item, Serializable, Comparable<ItemImpl> {
  /**
   * The Serial Id for ItemImpl Objects
   */
  private static final long serialVersionUID = 7415711683708976536L;
  private int id;
  private String name;
  private BigDecimal price;

  public ItemImpl(int id, String name, BigDecimal price) {
    setId(id);
    setName(name);
    setPrice(price);
  }

  /**
   * Gets the id.
   *
   * @return the id
   */
  @Override
  public int getId() {
    return id;
  }

  /**
   * Sets the id.
   *
   * @param id the new id
   */
  @Override
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Sets the name.
   *
   * @param name the new name
   */
  @Override
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the price.
   *
   * @return the price
   */
  @Override
  public BigDecimal getPrice() {
    return price;
  }

  /**
   * Sets the price.
   *
   * @param price the new price
   */
  @Override
  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  /**
   * Override Hash code to Hash by Id.
   *
   * @return the int
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + id;
    return result;
  }

  /**
   * Override Equals to compare by Id.
   *
   * @param obj the obj
   * @return true, if successful
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ItemImpl other = (ItemImpl) obj;
    if (id != other.id)
      return false;
    return true;
  }

  /**
   * Compare to.
   *
   * @param o the ItemImpl
   * @return the int code indicating greater/lesser
   */
  @Override
  public int compareTo(ItemImpl o) {
    return Integer.compare(this.getId(), o.getId());
  }
}
