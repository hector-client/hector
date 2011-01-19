package me.prettyprint.hom.beans;

import me.prettyprint.hom.Colors;

public class ColorEmbedded {

  private Colors color;

  public ColorEmbedded(Colors color) {
    this.color = color;
  }
  
  public String getId() {
    return color.getName();
  }
  
}
