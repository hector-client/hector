package me.prettyprint.hom.beans;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import me.prettyprint.hom.annotations.Column;

@Entity
@DiscriminatorValue("Blue")
public class MyBlueTestBean extends MyTestBean {

  @Column(name="mySet")
  private Set<Integer> mySet = new HashSet<Integer>();

  public MyBlueTestBean addToList(Integer i) {
    mySet.add(i);
    return this;
  }

  public Set<Integer> getMySet() {
    return mySet;
  }

  public void setMySet(Set<Integer> mySet) {
    this.mySet = mySet;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((mySet == null) ? 0 : mySet.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    MyBlueTestBean other = (MyBlueTestBean) obj;
    if (mySet == null) {
      if (other.mySet != null)
        return false;
    } else if (!mySet.equals(other.mySet))
      return false;
    return true;
  }

}
