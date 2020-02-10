package com.b07.inventory;

/**
 * The Enum ItemTypes.
 */
public enum ItemTypes {
  FISHING_ROD("FISHING_ROD", 0),
  HOCKEY_STICK("HOCKEY_STICK", 1),
  SKATES("SKATES", 2),
  RUNNING_SHOES("RUNNING_SHOES", 3),
  PROTEIN_BAR("PROTEIN_BAR", 4);

  /**
   * Instantiates a new roles.
   *
   * @param item    the item
   * @param itemNum the item num
   */
  private ItemTypes(final String item, final int itemNum) {
  }

  ;

  /**
   * Check if this role a valid roles.
   *
   * @param item the item
   * @return true, if given role is a type of Roles
   */
  public static boolean checkItem(String item) {
    if (item != null) {
      for (ItemTypes i : ItemTypes.values()) {
        if (i.name().equalsIgnoreCase(item)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Get the respective Roles of string input.
   *
   * @param item the item
   * @return acording Roles if role is of type role, null otherwise
   */
  public static ItemTypes getItemType(String item) {
    if (item != null) {
      for (ItemTypes i : ItemTypes.values()) {
        if (i.name().equalsIgnoreCase(item)) {
          return i;
        }
      }
    }
    return null;
  }
}
