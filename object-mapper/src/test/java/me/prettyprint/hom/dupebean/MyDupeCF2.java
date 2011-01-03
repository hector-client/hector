package me.prettyprint.hom.dupebean;

import java.util.UUID;

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
public class MyDupeCF2 {
  @Id
  private UUID baseId;

  @Column(name = "myType")
  private String myType;

  public UUID getBaseId() {
    return baseId;
  }

  public void setBaseId(UUID id) {
    this.baseId = id;
  }

  public String getMyType() {
    return myType;
  }

  public void setMyType(String myType) {
    this.myType = myType;
  }
}
