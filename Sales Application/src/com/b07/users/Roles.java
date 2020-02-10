package com.b07.users;

/**
 * The Enum Roles.
 */
public enum Roles {
  ADMIN, EMPLOYEE, CUSTOMER;

  /**
   * Check if this role a valid roles.
   *
   * @param role the role
   * @return true, if given role is a type of Roles
   */
  public static boolean checkRole(String role) {
    if (role != null) {
      for (Roles r : Roles.values()) {
        if (r.name().equalsIgnoreCase(role)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Get the respective Roles of string input
   *
   * @param role the role
   * @return according Roles if role is of type role, null otherwise
   */
  public static Roles getRole(String role) {
    if (role != null) {
      for (Roles r : Roles.values()) {
        if (r.name().equalsIgnoreCase(role)) {
          return r;
        }
      }
    }
    return null;
  }
}
