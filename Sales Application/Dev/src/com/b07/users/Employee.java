package com.b07.users;

public class Employee extends User {
  /**
   * Serial Id for Employee object
   */
  private static final long serialVersionUID = -408982874696648639L;

  /**
   * Instantiates a new Employee.
   *
   * @param id the id
   * @param name the name
   * @param age the age
   * @param address the address
   */
  public Employee(int id, String name, int age, String address) {
    super(id, name, age, address);
  }

  /**
   * Instantiates a new Employee.
   *
   * @param id the id
   * @param name the name
   * @param age the age
   * @param address the address
   * @param authenticated the authenticated
   */
  public Employee(int id, String name, int age, String address, boolean authenticated) {
    super(id, name, age, address, authenticated);
  }
}
