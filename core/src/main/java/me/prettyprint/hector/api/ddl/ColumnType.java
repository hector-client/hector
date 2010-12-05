package me.prettyprint.hector.api.ddl;

/**
 * @author: peter
 */
public enum ColumnType {

  STANDARD("Standard"), SUPER("Super");

  private String value;

  private ColumnType(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }

  public static ColumnType getFromValue(String value) {

    if (value == null) {
      return ColumnType.STANDARD;
    }

    ColumnType[] types = ColumnType.values();
    for (int a = 0; a < types.length; a++) {
      ColumnType type = types[a];
      if (type.getValue().equals(value)) {
        return type;
      }
    }
    return ColumnType.STANDARD;
  }

}
