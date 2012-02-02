package me.prettyprint.hom.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="SimpleTestBeanColumnFamily")
public class SimpleTestBean implements Serializable {
  
  private long id;
  private String name;
  
  public SimpleTestBean() {    
  }
  
  public SimpleTestBean(long id, String name) {
    this.id = id;
    this.name = name;
  }
  
  @Id
  public long getId() {
    return id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  @Column(name="name")
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return new StringBuilder(256)
    .append("name:")
    .append(getName())
    .append(",id:")
    .append(getId())
    .toString();
  }
  
  

}
