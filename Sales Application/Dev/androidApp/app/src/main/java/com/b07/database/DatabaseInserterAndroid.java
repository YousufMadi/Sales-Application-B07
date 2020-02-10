package com.b07.database;

import android.content.Context;

import com.b07.database.validator.DatabaseInsertValidator;
import com.b07.exceptions.ConstraintViolationException;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.exceptions.ValueExistsException;

import java.math.BigDecimal;
import java.sql.SQLException;

public class DatabaseInserterAndroid {
  private DatabaseDriverAndroid db;
  private DatabaseInsertValidator dbValidator;
  private DatabaseDeserializeHelper dbDeserialize;

  public DatabaseInserterAndroid(Context context) {
    db = new DatabaseDriverAndroid(context);
    dbValidator = new DatabaseInsertValidator(context);
    dbDeserialize = new DatabaseDeserializeHelper(context);
  }

  public int insertRole(String name) throws ConstraintViolationException, SQLException, ValueExistsException {
    dbValidator.validateRoleInsert(name);

    return Math.toIntExact(db.insertRole(name));
  }

  public int insertNewUser(String name, int age, String address, String password) throws ConstraintViolationException {
    dbValidator.validateNewUserInsert(name, age, address, password);

    return Math.toIntExact(db.insertNewUser(name, age, address, password));
  }

  public int insertUserRole(int userId, int roleId) throws ConstraintViolationException, SQLException {
    dbValidator.validateUserRoleInsert(userId, roleId);

    return Math.toIntExact(db.insertUserRole(userId, roleId));
  }

  public int insertItem(String name, BigDecimal price) throws ConstraintViolationException, SQLException, ValueExistsException {
    dbValidator.validateItemInsert(name, price);

    return Math.toIntExact(db.insertItem(name, price));
  }

  public int insertInventory(int itemId, int quantity) throws ConstraintViolationException, SQLException {
    dbValidator.validateInventoryInsert(itemId, quantity);

    return Math.toIntExact(db.insertInventory(itemId, quantity));
  }

  public int insertSale(int userId, BigDecimal totalPrice) throws ConstraintViolationException, SQLException {
    dbValidator.validateSaleInsert(userId, totalPrice);

    return Math.toIntExact(db.insertSale(userId, totalPrice));
  }

  public int insertItemizedSale(int saleId, int itemId, int quantity) throws ConstraintViolationException, ValueExistsException, SQLException {
    dbValidator.validateItemizedSaleInsert(saleId, itemId, quantity);

    return Math.toIntExact(db.insertItemizedSale(saleId, itemId, quantity));
  }

  public int insertAccount(int userId, boolean active) throws ConstraintViolationException, SQLException, ValueExistsException {
    dbValidator.validateAccountInsert(userId);

    return Math.toIntExact(db.insertAccount(userId, active));
  }

  public int insertAccountLine(int accountId, int itemId, int quantity) throws ConstraintViolationException, SQLException {
    dbValidator.validateAccountLineInsert(accountId, itemId, quantity);

    return Math.toIntExact(db.insertAccountLine(accountId, itemId, quantity));
  }

  protected int restoreUser(String name, int age, String address, String password)
      throws DatabaseInsertException, SQLException {
    int userId = 0;
    try {
      userId = Math.toIntExact(dbDeserialize.restoreUser(name, age, address, password));
    } finally {
      db.close();
    }
    return userId;
  }
}