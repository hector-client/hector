package me.prettyprint.hom.badbeans;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import me.prettyprint.hom.beans.MyNonEntityTestBean;


@Entity
@Table(name="PurpleColumnFamily")
public class MyBadTestBean extends MyNonEntityTestBean {
  @Id
  private String id;

  @Column(name="lp1")
  private long longProp1;

  @Column(name="lp2")
  private long longProp2;

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public long getLongProp1() {
    return longProp1;
  }

  public void setLongProp1(long longProp1) {
    this.longProp1 = longProp1;
  }

  public long getLongProp3() {
    return longProp2;
  }

  public void setLongProp3(long longProp2) {
    this.longProp2 = longProp2;
  }

}
