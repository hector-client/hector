package me.prettyprint.hom.beans;

import me.prettyprint.hom.ColorConverter;
import me.prettyprint.hom.Colors;
import me.prettyprint.hom.annotations.Column;
import me.prettyprint.hom.annotations.Entity;
import me.prettyprint.hom.annotations.Id;
import me.prettyprint.hom.annotations.Table;


@Entity
@Table("CustomIdColumnFamily")
public class MyCustomIdBean {
  @Id( converter=ColorConverter.class)
  private Colors id;

  @Column("lp1")
  private long longProp1;

  public Colors getId() {
    return id;
  }

  public void setId(Colors id) {
    this.id = id;
  }

  public long getLongProp1() {
    return longProp1;
  }

  public void setLongProp1(long longProp1) {
    this.longProp1 = longProp1;
  }

}
