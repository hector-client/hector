package me.prettyprint.hom.beans;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import me.prettyprint.hom.annotations.Column;

@Entity
public abstract class MyAbstractGreenTestBean extends MyTestBean {
  
  @Column(name="myShit")
  private int myShit;

  public int getMyShit() {
    return myShit;
  }

  public void setMyShit(int myShit) {
    this.myShit = myShit;
  }
  
}