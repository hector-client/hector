package me.prettyprint.hom.beans;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.mycompany.MySerial;

import me.prettyprint.hom.ColorConverter;
import me.prettyprint.hom.Colors;
import me.prettyprint.hom.annotations.AnonymousPropertyAddHandler;
import me.prettyprint.hom.annotations.AnonymousPropertyCollectionGetter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// no other type supported
@DiscriminatorColumn(name = "myType", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("NoColor")
@Table(name = "TestBeanColumnFamily")
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

  // @Column(value = "color", converter = ColorConverter.class)
  private Colors color;

  private Map<String, String> anonymousProps = new HashMap<String, String>();

  @AnonymousPropertyAddHandler
  public void addAnonymousProp(String name, String value) {
    anonymousProps.put(name, value);
  }

  @AnonymousPropertyCollectionGetter
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

}
