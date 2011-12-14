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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + myGreenStuff;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    MyAbstractGreenTestBean other = (MyAbstractGreenTestBean) obj;
    if (myGreenStuff != other.myGreenStuff)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "MyAbstractGreenTestBean [myGreenStuff=" + myGreenStuff + "]";
  }
  
}