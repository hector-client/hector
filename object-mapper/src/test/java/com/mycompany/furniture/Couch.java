package com.mycompany.furniture;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import me.prettyprint.hom.annotations.Column;
import me.prettyprint.hom.converters.VariableIntegerConverter;

@Entity
@DiscriminatorValue("couch")
public class Couch extends Furniture {

  @Column(name="foldOutBed")
  private boolean foldOutBed;

  @Column(name="numCushions", converter=VariableIntegerConverter.class)
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
