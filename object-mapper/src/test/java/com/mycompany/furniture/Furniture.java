package com.mycompany.furniture;

import me.prettyprint.hom.annotations.Column;
import me.prettyprint.hom.annotations.DiscriminatorColumn;
import me.prettyprint.hom.annotations.DiscriminatorType;
import me.prettyprint.hom.annotations.DiscriminatorValue;
import me.prettyprint.hom.annotations.Entity;
import me.prettyprint.hom.annotations.Id;
import me.prettyprint.hom.annotations.Inheritance;
import me.prettyprint.hom.annotations.Table;

@Entity
@Table("Furniture")
@Inheritance
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("") // means catch all derivatives that don't specify a 'type'
public class Furniture {

  @Id
  private int id;

  @Column("material")
  private String material;

  @Column("color")
  private String color;

  public String getMaterial() {
    return material;
  }

  public void setMaterial(String material) {
    this.material = material;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

}
