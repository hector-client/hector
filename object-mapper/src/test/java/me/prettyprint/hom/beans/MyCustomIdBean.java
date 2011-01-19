package me.prettyprint.hom.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import me.prettyprint.hom.Colors;


@Entity
@Table(name="CustomIdColumnFamily")
@IdClass(ColorEmbedded.class)
public class MyCustomIdBean {
  @Id
  private Colors id;

  @Column(name="lp1")
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
