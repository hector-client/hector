package me.prettyprint.hom.beans;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hom.ColorConverter;
import me.prettyprint.hom.Colors;
import me.prettyprint.hom.annotations.AnonymousPropertyHandling;

import com.mycompany.MySerial;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// no type other than SINGLE_TABLE supported
@DiscriminatorColumn(name = "myType", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("NoColor")
@Table(name = "TestBeanColumnFamily")
@AnonymousPropertyHandling(type=String.class, serializer=StringSerializer.class, adder="addAnonymousProp", getter="getAnonymousProps")
public class MyTestBean {
  @Id
  private UUID baseId;

  @Column(name = "myType")
  private String myType;

  @Column(name = "lp1")
  private long longProp1;

  @Column(name = "lp2")
  private Long longProp2;

  @Column(name = "ip1")
  private int intProp1;

  @Column(name = "ip2")
  private Integer intProp2;

  @Column(name = "bp1")
  private boolean boolProp1;

  @Column(name = "bp2")
  private Boolean boolProp2;

  @Column(name = "sp")
  private String strProp;

  @Column(name = "up")
  private UUID uuidProp;

  @Column(name = "dp")
  private Date dateProp;

  @Column(name = "bytes")
  private byte[] bytesProp;

  @Column(name = "serialProp")
  private MySerial serialProp;

  @me.prettyprint.hom.annotations.Column(name = "color", converter = ColorConverter.class)
  private Colors color;

  private Map<String, String> anonymousProps = new HashMap<String, String>();

  public void addAnonymousProp(String name, String value) {
    anonymousProps.put(name, value);
  }

  public Collection<Entry<String, String>> getAnonymousProps() {
    return anonymousProps.entrySet();
  }

  public String getAnonymousProp(String name) {
    return anonymousProps.get(name);
  }

  public UUID getBaseId() {
    return baseId;
  }

  public void setBaseId(UUID id) {
    this.baseId = id;
  }

  public long getLongProp1() {
    return longProp1;
  }

  public void setLongProp1(long longProp1) {
    this.longProp1 = longProp1;
  }

  public Long getLongProp2() {
    return longProp2;
  }

  public void setLongProp2(Long longProp2) {
    this.longProp2 = longProp2;
  }

  public int getIntProp1() {
    return intProp1;
  }

  public void setIntProp1(int intProp1) {
    this.intProp1 = intProp1;
  }

  public Integer getIntProp2() {
    return intProp2;
  }

  public void setIntProp2(Integer intProp2) {
    this.intProp2 = intProp2;
  }

  public boolean isBoolProp1() {
    return boolProp1;
  }

  public void setBoolProp1(boolean boolProp1) {
    this.boolProp1 = boolProp1;
  }

  public Boolean getBoolProp2() {
    return boolProp2;
  }

  public void setBoolProp2(Boolean boolProp2) {
    this.boolProp2 = boolProp2;
  }

  public String getStrProp() {
    return strProp;
  }

  public void setStrProp(String strProp) {
    this.strProp = strProp;
  }

  public UUID getUuidProp() {
    return uuidProp;
  }

  public void setUuidProp(UUID uuidProp) {
    this.uuidProp = uuidProp;
  }

  public Date getDateProp() {
    return dateProp;
  }

  public void setDateProp(Date dateProp) {
    this.dateProp = dateProp;
  }

  public byte[] getBytesProp() {
    return bytesProp;
  }

  public void setBytesProp(byte[] bytesProp) {
    this.bytesProp = bytesProp;
  }

  public Colors getColor() {
    return color;
  }

  public void setColor(Colors color) {
    this.color = color;
  }

  public String getMyType() {
    return myType;
  }

  public void setMyType(String myType) {
    this.myType = myType;
  }

  public MySerial getSerialProp() {
    return serialProp;
  }

  public void setSerialProp(MySerial serialProp) {
    this.serialProp = serialProp;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((anonymousProps == null) ? 0 : anonymousProps.hashCode());
    result = prime * result + ((baseId == null) ? 0 : baseId.hashCode());
    result = prime * result + (boolProp1 ? 1231 : 1237);
    result = prime * result + ((boolProp2 == null) ? 0 : boolProp2.hashCode());
    result = prime * result + Arrays.hashCode(bytesProp);
    result = prime * result + ((color == null) ? 0 : color.hashCode());
    result = prime * result + ((dateProp == null) ? 0 : dateProp.hashCode());
    result = prime * result + intProp1;
    result = prime * result + ((intProp2 == null) ? 0 : intProp2.hashCode());
    result = prime * result + (int) (longProp1 ^ (longProp1 >>> 32));
    result = prime * result + ((longProp2 == null) ? 0 : longProp2.hashCode());
    result = prime * result + ((serialProp == null) ? 0 : serialProp.hashCode());
    result = prime * result + ((strProp == null) ? 0 : strProp.hashCode());
    result = prime * result + ((uuidProp == null) ? 0 : uuidProp.hashCode());
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
    MyTestBean other = (MyTestBean) obj;
    if (anonymousProps == null) {
      if (other.anonymousProps != null)
        return false;
    } else if (!anonymousProps.equals(other.anonymousProps))
      return false;
    if (baseId == null) {
      if (other.baseId != null)
        return false;
    } else if (!baseId.equals(other.baseId))
      return false;
    if (boolProp1 != other.boolProp1)
      return false;
    if (boolProp2 == null) {
      if (other.boolProp2 != null)
        return false;
    } else if (!boolProp2.equals(other.boolProp2))
      return false;
    if (!Arrays.equals(bytesProp, other.bytesProp))
      return false;
    if (color != other.color)
      return false;
    if (dateProp == null) {
      if (other.dateProp != null)
        return false;
    } else if (!dateProp.equals(other.dateProp))
      return false;
    if (intProp1 != other.intProp1)
      return false;
    if (intProp2 == null) {
      if (other.intProp2 != null)
        return false;
    } else if (!intProp2.equals(other.intProp2))
      return false;
    if (longProp1 != other.longProp1)
      return false;
    if (longProp2 == null) {
      if (other.longProp2 != null)
        return false;
    } else if (!longProp2.equals(other.longProp2))
      return false;
    if (serialProp == null) {
      if (other.serialProp != null)
        return false;
    } else if (!serialProp.equals(other.serialProp))
      return false;
    if (strProp == null) {
      if (other.strProp != null)
        return false;
    } else if (!strProp.equals(other.strProp))
      return false;
    if (uuidProp == null) {
      if (other.uuidProp != null)
        return false;
    } else if (!uuidProp.equals(other.uuidProp))
      return false;
    return true;
  }

}
