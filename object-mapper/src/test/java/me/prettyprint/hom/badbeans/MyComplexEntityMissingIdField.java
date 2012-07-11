package me.prettyprint.hom.badbeans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import me.prettyprint.hom.beans.MyCompositePK;

@Entity
@IdClass( MyCompositePK.class )
@Table(name="ComplexColumnFamily")
public class MyComplexEntityMissingIdField {
  
  @Id
  private String strProp1;
  
  @Column( name ="strProp2")
  private String strProp2;

  public String getStrProp1() {
    return strProp1;
  }

  public void setStrProp1(String strProp1) {
    this.strProp1 = strProp1;
  }

  public String getStrProp2() {
    return strProp2;
  }

  public void setStrProp2(String strProp2) {
    this.strProp2 = strProp2;
  }

}
