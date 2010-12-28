package com.mycompany.furniture;

import me.prettyprint.hom.annotations.Column;
import me.prettyprint.hom.annotations.DiscriminatorValue;
import me.prettyprint.hom.annotations.Entity;

@Entity
@DiscriminatorValue("couch")
public class Couch extends Furniture {

  @Column("foldOutBed")
  private boolean foldOutBed;

  @Column("numCushions")
  private int numCushions;

  public boolean isFoldOutBed() {
    return foldOutBed;
  }

  public void setFoldOutBed(boolean foldOutBed) {
    this.foldOutBed = foldOutBed;
  }

  public int getNumCushions() {
    return numCushions;
  }

  public void setNumCushions(int numCushions) {
    this.numCushions = numCushions;
  }
}
