package me.prettyprint.hom.badbeans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="MyMissingIdSetterBean")
public class MyMissingIdSetterBean {
  @Id
  private String id;

  @Column(name="lp1")
  private long longProp1;

  public long getLongProp1() {
    return longProp1;
  }

  public void setLongProp1(long longProp1) {
    this.longProp1 = longProp1;
  }

}
