package com.b07.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.b07.exceptions.DatabaseInsertException;

/**
 * The Class DatabaseDeserializeHelper.
 * A DatabaseDeserialize Helper that extends DatabaseDriverHelper and
 * some misc Database Operations required for deserializing
 */
public class DatabaseDeserializeHelper extends DatabaseDriverAndroid {

  public DatabaseDeserializeHelper(Context context) {
    super(context);
  }

  /**
   * Use this to Restore an user.
   *
   * @param name     the user's name.
   * @param age      the user's age.
   * @param address  the user's address.
   * @param password the user's password (not hashsed).
   * @return the user id
   * @throws DatabaseInsertException if there is a failure on the insert
   */
  protected long restoreUser(String name, int age, String address,
                             String password) throws DatabaseInsertException {
    long id = insertUser(name, age, address);
    if (id != -1) {
      restorePassword(password, id);
      return id;
    }
    throw new DatabaseInsertException();
  }

  /**
   * Insert user.
   *
   * @param name    the name
   * @param age     the age
   * @param address the address
   * @return the int
   */
  private long insertUser(String name, int age, String address) {
    SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put("NAME", name);
    contentValues.put("AGE", age);
    contentValues.put("ADDRESS", address);

    long id = sqLiteDatabase.insert("USERS", null, contentValues);
    sqLiteDatabase.close();

    return id;
  }

  /**
   * Restore password.
   *
   * @param password the password
   * @param userId   the user id
   * @return true, if successful
   */
  private void restorePassword(String password, long userId) {
    SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();

    contentValues.put("USERID", userId);
    contentValues.put("PASSWORD", password);
    sqLiteDatabase.insert("USERPW", null, contentValues);
    sqLiteDatabase.close();
  }
}
