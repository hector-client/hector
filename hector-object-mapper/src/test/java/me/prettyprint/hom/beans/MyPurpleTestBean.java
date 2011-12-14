package me.prettyprint.hom.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="PurpleColumnFamily")
public class MyPurpleTestBean extends MyNonEntityTestBean {
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

  public long getLongProp2() {
    return longProp2;
  }

  public void setLongProp2(long longProp2) {
    this.longProp2 = longProp2;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + (int) (longProp1 ^ (longProp1 >>> 32));
    result = prime * result + (int) (longProp2 ^ (longProp2 >>> 32));
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    MyPurpleTestBean other = (MyPurpleTestBean) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (longProp1 != other.longProp1)
      return false;
    if (longProp2 != other.longProp2)
      return false;
    return true;
  }

}
