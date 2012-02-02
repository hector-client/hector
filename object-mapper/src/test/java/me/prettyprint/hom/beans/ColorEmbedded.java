package me.prettyprint.hom.beans;

import java.io.Serializable;

import me.prettyprint.hom.Colors;

@SuppressWarnings("serial")
public class ColorEmbedded implements Serializable {

  private Colors color;
  
  public Colors getColor() {
    return color;
  }

  public void setColor(Colors color) {
    this.color = color;
  }
  
}
