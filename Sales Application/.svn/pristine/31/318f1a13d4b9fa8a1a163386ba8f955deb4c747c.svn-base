package com.b07.users;

import com.b07.inventory.Item;

import java.io.Serializable;
import java.util.HashMap;

public class AccountSummaryImpl implements AccountSummary, Serializable {

  /**
   * Serial Id for AccountSummaryImpl objects
   */
  private static final long serialVersionUID = -1893465048883074171L;
  private int accountId;
  private HashMap<Item, Integer> summary = new HashMap<Item, Integer>();

  /**
   * Instantiate an account summary.
   *
   * @param accountId is id of account.
   * @param summary   is summary of the account.
   */
  public AccountSummaryImpl(int accountId, HashMap<Item, Integer> summary) {
    this.accountId = accountId;
    this.summary = summary;
  }

  @Override
  public int getId() {
    return this.accountId;
  }

  @Override
  public void addItem(Item item, int quantity) {
    if (item != null && quantity > 0) {
      summary.put(item, quantity);
    }
  }

  /**
   * Return the summary of the account.
   *
   * @return the hashmap containing the item and its quantity.
   */
  @Override
  public HashMap<Item, Integer> getSummary() {
    return this.summary;
  }


}
