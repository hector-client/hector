package me.prettyprint.hom.beans;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import me.prettyprint.hom.annotations.Column;

@Entity
@DiscriminatorValue("Blue")
public class MyBlueTestBean extends MyTestBean {

  @Column(name="myList")
  private List<Integer> myList = new LinkedList<Integer>();

  public MyBlueTestBean addToList(Integer i) {
    myList.add(i);
    return this;
  }

  public List<Integer> getMyList() {
    return myList;
  }

  public void setMyList(List<Integer> myList) {
    this.myList = myList;
  }

}
