package me.prettyprint.hom.beans;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Entity;
import javax.persistence.Table;

import me.prettyprint.cassandra.serializers.ObjectSerializer;
import me.prettyprint.hom.annotations.AnonymousPropertyHandling;
import me.prettyprint.hom.annotations.Id;

import com.mycompany.furniture.Drawer;

@Entity
@Table(name = "AnonumousColumnFamily")
@AnonymousPropertyHandling(type = Drawer.class, serializer = ObjectSerializer.class, adder = "addAnonymousProp", getter = "getAnonymousProps")
public class AnonymousWithCustomType {
  private Map<String, Drawer> anonymousProps = new HashMap<String, Drawer>();

  @Id
  private long id;

  public void addAnonymousProp(String name, Drawer value) {
    anonymousProps.put(name, value);
  }

  public Collection<Entry<String, Drawer>> getAnonymousProps() {
    return anonymousProps.entrySet();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }
}
