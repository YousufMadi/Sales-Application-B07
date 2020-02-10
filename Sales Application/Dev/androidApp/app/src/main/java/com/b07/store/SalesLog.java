package com.b07.store;

import java.util.HashMap;

/**
 * The Interface SalesLog.
 */
public interface SalesLog {

  /**
   * Sets the sales log.
   *
   * @param salesLog the sales log
   */
  public void setSalesLog(HashMap<Integer, Sale> salesLog);

  /**
   * Gets the sales log.
   *
   * @return the sales log
   */
  public HashMap<Integer, Sale> getSalesLog();

  /**
   * Update sales.
   *
   * @param sale the sale
   */
  public void updateSales(Sale sale);
}
