package com.mycompany.furniture;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("table")
public class Table extends Furniture {

  @Column(name="extendable")
  private boolean extendable;

  @Column(name="shape")
  private String shape;

  public boolean isExtendable() {
    return extendable;
  }

  public void setExtendable(boolean extendable) {
    this.extendable = extendable;
  }

  public String getShape() {
    return shape;
  }

  public void setShape(String shape) {
    this.shape = shape;
  }

}
