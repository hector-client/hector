package me.prettyprint.hom.beans;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="SimpleRelationshipBeanColumnFamily")
public class SimpleRelationshipBean {
  
  private UUID baseId;
  
  private String myType;
    
  private Set<SimpleTestBean> simpleTestBeans;

  @Id
  public UUID getBaseId() {
    return baseId;
  }

  public void setBaseId(UUID baseId) {
    this.baseId = baseId;
  }

  @Column(name = "myType")
  public String getMyType() {
    return myType;
  }

  public void setMyType(String myType) {
    this.myType = myType;
  }

  @OneToMany
  public Set<SimpleTestBean> getSimpleTestBeans() {
    return simpleTestBeans;
  }

  public void setSimpleTestBeans(Set<SimpleTestBean> simpleTestBeans) {
    this.simpleTestBeans = simpleTestBeans;
  }

  /**
   * Convinience method for adding a single SimpleTestBean
   * @param simpleTestBean
   */
  public void addSimpleTestBean(SimpleTestBean simpleTestBean) {
    if ( simpleTestBeans == null )
      simpleTestBeans = new HashSet<SimpleTestBean>();
    simpleTestBeans.add(simpleTestBean);
  }
}
