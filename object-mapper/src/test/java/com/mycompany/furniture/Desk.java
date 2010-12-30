package com.mycompany.furniture;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("table_desk")
public class Desk extends Table {

  @Column(name="numDrawers")
  private int numDrawers;

  public int getNumDrawers() {
    return numDrawers;
  }

  public void setNumDrawers(int numDrawers) {
    this.numDrawers = numDrawers;
  }
}
