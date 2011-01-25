package me.prettyprint.hom.beans;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SimpleRelationshipBean {
  @Id
  private UUID baseId;

  @Column(name = "myType")
  private String myType;

  public UUID getBaseId() {
    return baseId;
  }

  public void setBaseId(UUID baseId) {
    this.baseId = baseId;
  }

  public String getMyType() {
    return myType;
  }

  public void setMyType(String myType) {
    this.myType = myType;
  }

  
  
}
