package me.prettyprint.hom.beans;

// don't annotate this one
public class MyNonEntityTestBean {

  private long longProp3;

  public MyNonEntityTestBean() {
    super();
  }

  public void setLongProp3(long longProp3) {
    this.longProp3 = longProp3;
  }

  public long getLongProp3() {
    return longProp3;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (longProp3 ^ (longProp3 >>> 32));
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
    MyNonEntityTestBean other = (MyNonEntityTestBean) obj;
    if (longProp3 != other.longProp3)
      return false;
    return true;
  }

}
