package me.prettyprint.hom.beans;

import me.prettyprint.hom.ColorConverter;
import me.prettyprint.hom.Colors;
import me.prettyprint.hom.annotations.*;

import java.util.*;
import java.util.Map.Entry;


/**
 * This entity almost like the MyTestBean except is has
 * fewer properties but maps to the same ColumnFamily.
 * Is it easy to have few POJOs which have different sets of "columns"?! 
 */
@Entity
@Inheritance( strategy = InheritanceType.SINGLE_TABLE) // no other type supported
@DiscriminatorColumn(
    name="myType",
    discriminatorType=DiscriminatorType.STRING
)
@DiscriminatorValue("NoColor")
@Table("TestBeanColumnFamily")
public class MyTestBeaEasy {
  @Id
  private UUID baseId;

  @Column("myType")
  private String myType;

  @Column("lp1")
  private long longProp1;


  @Column(value = "color", converter = ColorConverter.class)
  private Colors color;

  private Map<String, String> anonymousProps = new HashMap<String, String>();



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

}
