package me.prettyprint.hom.dupebean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity for testing duplication class <-> column family mapping.
 * 
 * @author Todd Burruss
 */
@Entity
@Table(name = "TestDupeColumnFamily")
public class MyDupeCF1 {
  @Id
  private String baseId;

  @Column(name = "myType")
  private String myType;

  public String getBaseId() {
    return baseId;
  }

  public void setBaseId(String baseId) {
    this.baseId = baseId;
  }

  public String getMyType() {
    return myType;
  }

  public void setMyType(String myType) {
    this.myType = myType;
  }
}
