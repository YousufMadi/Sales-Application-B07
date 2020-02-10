package com.b07.users;

public class Customer extends User {
  /**
   * Instantiates a new Customer.
   *
   * @param id      the id
   * @param name    the name
   * @param age     the age
   * @param address the address
   */
  public Customer(int id, String name, int age, String address) {
    super(id, name, age, address);
  }

  /**
   * Instantiates a new Customer.
   *
   * @param id            the id
   * @param name          the name
   * @param age           the age
   * @param address       the address
   * @param authenticated the authenticated
   */
  public Customer(int id, String name, int age, String address, boolean authenticated) {
    super(id, name, age, address, authenticated);
  }

  ;
}
