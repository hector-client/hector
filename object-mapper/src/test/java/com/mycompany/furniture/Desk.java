package com.mycompany.furniture;

import me.prettyprint.hom.annotations.Column;
import me.prettyprint.hom.annotations.DiscriminatorValue;
import me.prettyprint.hom.annotations.Entity;

@Entity
@DiscriminatorValue("table_desk")
public class Desk extends Table {

  @Column("numDrawers")
  private int numDrawers;

  public int getNumDrawers() {
    return numDrawers;
  }

  public void setNumDrawers(int numDrawers) {
    this.numDrawers = numDrawers;
  }
}
