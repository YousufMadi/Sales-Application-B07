package com.b07.database.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.b07.database.DatabaseDriver;
import com.b07.exceptions.ConnectionFailedException;
import com.b07.exceptions.DatabaseInsertException;

/**
 * The Class DatabaseDeserializeHelper. A DatabaseDeserialize Helper that extends
 * DatabaseDriverHelper and some misc Database Operations required for deserializing
 */
public class DatabaseDeserializeHelper extends DatabaseDriverHelper {

  /**
   * Use this to Restore an user.
   * 
   * @param name the user's name.
   * @param age the user's age.
   * @param address the user's address.
   * @param password the user's password (not hashsed).
   * @param connection the database connection.
   * @return the user id
   * @throws DatabaseInsertException if there is a failure on the insert
   */
  protected static int restoreUser(String name, int age, String address, String password,
      Connection connection) throws DatabaseInsertException {
    int id = insertUser(name, age, address, connection);
    if (id != -1) {
      restorePassword(password, id, connection);
      return id;
    }
    throw new DatabaseInsertException();
  }

  /**
   * Insert user.
   *
   * @param name the name
   * @param age the age
   * @param address the address
   * @param connection the connection
   * @return the int
   */
  private static int insertUser(String name, int age, String address, Connection connection) {
    String sql = "INSERT INTO USERS(NAME, AGE, ADDRESS) VALUES(?,?,?);";
    try {
      PreparedStatement preparedStatement =
          connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, name);
      preparedStatement.setInt(2, age);
      preparedStatement.setString(3, address);
      int id = preparedStatement.executeUpdate();
      if (id > 0) {
        ResultSet uniqueKey = preparedStatement.getGeneratedKeys();
        if (uniqueKey.next()) {
          int returnValue = uniqueKey.getInt(1);
          uniqueKey.close();
          preparedStatement.close();
          return returnValue;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return -1;
  }

  /**
   * Restore password.
   *
   * @param password the password
   * @param userId the user id
   * @param connection the connection
   * @return true, if successful
   */
  private static boolean restorePassword(String password, int userId, Connection connection) {
    String sql = "INSERT INTO USERPW(USERID, PASSWORD) VALUES(?,?);";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1, userId);
      preparedStatement.setString(2, password);
      preparedStatement.executeUpdate();
      preparedStatement.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Connect or create data base.
   *
   * @return the connection
   */
  protected static Connection connectOrCreateDataBase() {
    Connection connection = DatabaseDriver.connectOrCreateDataBase();
    return connection;
  }

  /**
   * Initialize database.
   *
   * @throws ConnectionFailedException the connection failed exception
   * @throws SQLException the SQL exception
   */
  protected static void initializeDatabase() throws ConnectionFailedException, SQLException {
    Connection connection = DatabaseDriver.connectOrCreateDataBase();

    try {
      DatabaseDriver.initialize(connection);
    } finally {
      connection.close();
    }
  }
}
