package me.prettyprint.hom;

public enum Colors {
  BLUE("Blue"),

  RED("Red"),

  GREEN("Green")
  ;

  private final String name;

  Colors(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public static Colors getInstance(String name) {
    Colors[] tidArr = values();
    for (Colors tid : tidArr) {
      if (tid.getName().equals(name)) {
        return tid;
      }
    }

    throw new IllegalArgumentException("No Color with name, " + name);
  }
}
