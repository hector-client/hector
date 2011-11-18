package com.mycompany;

import java.io.Serializable;

public class MySerial implements Serializable {

  private int prop1;
  private long prop2;
  
  public MySerial(int prop1, long prop2) {
    super();
    this.prop1 = prop1;
    this.prop2 = prop2;
  }
  
  public int getProp1() {
    return prop1;
  }
  public void setProp1(int prop1) {
    this.prop1 = prop1;
  }
  public long getProp2() {
    return prop2;
  }
  public void setProp2(long prop2) {
    this.prop2 = prop2;
  }

  @Override
  public String toString() {
    return "MySerial [prop1=" + prop1 + ", prop2=" + prop2 + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + prop1;
    result = prime * result + (int) (prop2 ^ (prop2 >>> 32));
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
    MySerial other = (MySerial) obj;
    if (prop1 != other.prop1)
      return false;
    if (prop2 != other.prop2)
      return false;
    return true;
  }

}
