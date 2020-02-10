package com.b07.users;

/**
 * The Class Admin.
 */
public class Admin extends User {

  /**
   * Serial Id for Admin object
   */
  private static final long serialVersionUID = 9048995631488605180L;

  /**
   * Instantiates a new admin.
   *
   * @param id the id
   * @param name the name
   * @param age the age
   * @param address the address
   */
  public Admin(int id, String name, int age, String address) {
    super(id, name, age, address);
  }

  /**
   * Instantiates a new admin.
   *
   * @param id the id
   * @param name the name
   * @param age the age
   * @param address the address
   * @param authenticated the authenticated
   */
  public Admin(int id, String name, int age, String address, boolean authenticated) {
    super(id, name, age, address, authenticated);
  }
}
