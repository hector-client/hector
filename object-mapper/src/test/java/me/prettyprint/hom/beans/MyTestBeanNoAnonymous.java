package me.prettyprint.hom.beans;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="NoAnonymousColumnFamily")
public class MyTestBeanNoAnonymous {
  @Id
  private UUID baseId;

  @Column(name = "lp1")
  private long longProp1;

  public UUID getBaseId() {
    return baseId;
  }

  public void setBaseId(UUID baseId) {
    this.baseId = baseId;
  }

  public long getLongProp1() {
    return longProp1;
  }

  public void setLongProp1(long longProp1) {
    this.longProp1 = longProp1;
  }

}
