package me.prettyprint.hom.badbeans;

import me.prettyprint.hom.annotations.Column;
import me.prettyprint.hom.annotations.Entity;
import me.prettyprint.hom.annotations.Id;
import me.prettyprint.hom.annotations.Table;
import me.prettyprint.hom.beans.MyNonEntityTestBean;


@Entity
@Table("PurpleColumnFamily")
public class MyBadTestBean extends MyNonEntityTestBean {
  @Id
  private String id;

  @Column("lp1")
  private long longProp1;

  @Column("lp2")
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
