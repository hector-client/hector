package me.prettyprint.hom.beans;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Red")
public class MyRedTestBean extends MyTestBean {

  @Column(name="myCol1")
  private int c1;

  public int getC1() {
    return c1;
  }

  public void setC1(int c1) {
    this.c1 = c1;
  }



}
