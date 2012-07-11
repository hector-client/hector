package me.prettyprint.hom.beans;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import me.prettyprint.hom.ObjectConverter;
import me.prettyprint.hom.annotations.Column;

@Entity
@Table(name="MyConvertedCollectionFamily")
public class MyConvertedCollectionBean {
  
  @Id
  private String id;
  
  @Column(name="mySet", converter=ObjectConverter.class)
  private Collection<Integer> myCollection = new HashSet<Integer>();

  public String getId() {
    return id;
  }

  public void setId(String name) {
    this.id = name;
  }

  public MyConvertedCollectionBean addToList(Integer i) {
    myCollection.add(i);
    return this;
  }

  public Collection<Integer> getMyCollection() {
    return myCollection;
  }

  public void setMyCollection(Collection<Integer> mySet) {
    this.myCollection = mySet;
  }
}
