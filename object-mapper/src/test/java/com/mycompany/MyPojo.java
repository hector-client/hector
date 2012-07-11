package com.mycompany;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import me.prettyprint.hom.ColorConverter;
import me.prettyprint.hom.Colors;
import me.prettyprint.hom.annotations.AnonymousPropertyHandling;


@Entity
@Table(name="TestColumnFamily")
@AnonymousPropertyHandling(adder="addAnonymousProp", getter="getAnonymousProps")
public class MyPojo {
  @Id
  private UUID id;

  @Column(name="lp1")
  private long longProp1;

  @me.prettyprint.hom.annotations.Column(name = "color", converter = ColorConverter.class)
  private Colors color;

  private Map<String, byte[]> anonymousProps = new HashMap<String, byte[]>();

  public void addAnonymousProp(String name, byte[] value) {
    anonymousProps.put(name, value);
  }

  public Collection<Entry<String, byte[]>> getAnonymousProps() {
    return anonymousProps.entrySet();
  }

  public String getAnonymousProp(String name) {
    return new String(anonymousProps.get(name));
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
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
}
