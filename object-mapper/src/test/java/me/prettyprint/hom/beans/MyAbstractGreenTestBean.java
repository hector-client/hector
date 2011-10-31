package me.prettyprint.hom.beans;

import javax.persistence.Entity;

import me.prettyprint.hom.annotations.Column;

@Entity
public abstract class MyAbstractGreenTestBean extends MyTestBean {
  
  @Column(name="myGreenStuff")
  private int myGreenStuff;

  public int getMyGreenStuff() {
    return myGreenStuff;
  }

  public void setMyGreenStuff(int myGreenStuff) {
    this.myGreenStuff = myGreenStuff;
  }
  
}