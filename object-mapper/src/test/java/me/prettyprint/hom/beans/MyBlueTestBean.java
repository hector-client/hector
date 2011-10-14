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

}
