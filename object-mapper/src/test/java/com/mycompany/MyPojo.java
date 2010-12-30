package com.mycompany;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import me.prettyprint.hom.ColorConverter;
import me.prettyprint.hom.Colors;
import me.prettyprint.hom.annotations.AnonymousPropertyAddHandler;
import me.prettyprint.hom.annotations.AnonymousPropertyCollectionGetter;


@Entity
@Table(name="TestColumnFamily")
class MyPojo {
  @Id
  private UUID id;

  @Column(name="lp1")
  private long longProp1;

  //@Column(value = "color", converter = ColorConverter.class)
  @Column(name="color")
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
