package com.b07.store;

import java.io.Serializable;
import java.util.HashMap;

public class SalesLogImpl implements SalesLog, Serializable {
  /**
   * Serial Id for SalesLogImpl
   */
  private static final long serialVersionUID = -3637561777883611732L;
  private HashMap<Integer, Sale> salesLog;

  public SalesLogImpl(HashMap<Integer, Sale> salesLog) {
    setSalesLog(salesLog);
  }

  @Override
  public void setSalesLog(HashMap<Integer, Sale> salesLog) {
    this.salesLog = salesLog;
  }

  @Override
  public HashMap<Integer, Sale> getSalesLog() {
    return this.salesLog;
  }

  @Override
  public void updateSales(Sale sale) {
    salesLog.put(sale.getId(), sale);
  }
}
