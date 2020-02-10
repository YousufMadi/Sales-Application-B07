package com.b07.store;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import com.b07.inventory.Item;
import com.b07.users.User;

public class SaleImpl implements Sale, Serializable {
  /**
   * Serial Id for SaleImpl objects
   */
  private static final long serialVersionUID = -1954462887681554679L;
  private int id;
  private User user;
  private BigDecimal price;
  private HashMap<Item, Integer> sale;

  public SaleImpl(int id) {
    setId(id);
  }

  @Override
  public int getId() {
    return this.id;
  }

  @Override
  public SaleImpl setId(int id) {
    this.id = id;
    return this;
  }

  @Override
  public User getUser() {
    return this.user;
  }

  @Override
  public SaleImpl setUser(User user) {
    this.user = user;
    return this;
  }

  @Override
  public BigDecimal getTotalPrice() {
    return this.price;
  }

  @Override
  public SaleImpl setTotalPrice(BigDecimal price) {
    this.price = price;
    return this;
  }

  @Override
  public HashMap<Item, Integer> getItemMap() {
    return this.sale;
  }

  @Override
  public SaleImpl setItemMap(HashMap<Item, Integer> itemMap) {
    this.sale = itemMap;
    return this;
  }
}
