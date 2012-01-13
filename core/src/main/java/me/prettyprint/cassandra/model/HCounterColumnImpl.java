package me.prettyprint.cassandra.model;

import static me.prettyprint.cassandra.utils.Assert.notNull;

import java.nio.ByteBuffer;

import me.prettyprint.cassandra.serializers.SerializerTypeInferer;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HCounterColumn;

import org.apache.cassandra.thrift.CounterColumn;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Hector Counter Column definition.
 *
 * @param <N> The type of the column name
 *
 * @author patricioe (patricioe@gmail.com)
 */
public final class HCounterColumnImpl<N> implements HCounterColumn<N> {

  private CounterColumn counterColumn;
  private Serializer<N> nameSerializer;


  public HCounterColumnImpl(N name, Long value, Serializer<N> nameSerializer) {
    this(nameSerializer);
    notNull(name, "name is null");
    notNull(value, "value is null");
    this.counterColumn = new CounterColumn(nameSerializer.toByteBuffer(name), value);
  }

  public HCounterColumnImpl(CounterColumn thriftCounterColumn, Serializer<N> nameSerializer) {
    this(nameSerializer);
    notNull(thriftCounterColumn, "thriftColumn is null");
    this.counterColumn = thriftCounterColumn;
  }

  public HCounterColumnImpl(Serializer<N> nameSerializer) {
    notNull(nameSerializer, "nameSerializer is null");
    this.nameSerializer = nameSerializer;
    this.counterColumn = new CounterColumn();
  }

  public HCounterColumnImpl(N name, Long value) {
    this(name, value, SerializerTypeInferer.<N>getSerializer(name));
  }

  @Override
  public HCounterColumn<N> setName(N name) {
    notNull(name, "name is null");
    this.counterColumn.setName(nameSerializer.toByteBuffer(name));
    return this;
  }

  @Override
  public HCounterColumn<N> setValue(Long value) {
    notNull(value, "value is null");
    this.counterColumn.setValue(value);
    return this;
  }

  /**
   * Set the time-to-live value for this column in seconds. 
   * The server will mark this column as deleted once the number of seconds has elapsed.
   */
  @Override
  public HCounterColumn<N> setTtl(int ttl) {
    //this.counterColumn.setTtl(ttl);
    // TODO (patricioe) Pending on Cassandra trunk
    return this;
  }

  @Override
  public int getTtl() {
    //return this.counterColumn.ttl;
    // TODO (patricioe) Pending on Cassandra trunk
    return Integer.MAX_VALUE;
  }

  @Override
  public N getName() {    
    return counterColumn.isSetName() ? nameSerializer.fromByteBuffer(counterColumn.name.duplicate()) : null;
  }

  @Override
  public Long getValue() {       
    return counterColumn.value;
  }  

  public CounterColumn toThrift() {
    return counterColumn;
  }

  public HCounterColumn<N> fromThrift(CounterColumn c) {
    notNull(c, "column is null");
    this.counterColumn = c;
    return this;
  }

  @Override
  public Serializer<N> getNameSerializer() {
    return nameSerializer;
  }

  @Override
  public ByteBuffer getNameBytes() {  
    return counterColumn.isSetName() ? counterColumn.name.duplicate() : null;
  }

  /**
   * Clear value, timestamp and ttl (the latter two set to '0') leaving only the column name
   */
  @Override
  public HCounterColumn<N> clear() {    
    counterColumn.value = 0; // TODO (patricioe) Is this ok?
    //counterColumn.ttl = 0; TODO (patricioe) pending on trunk
    //counterColumn.setTtlIsSet(false);
    counterColumn.setValueIsSet(false);
    return this;
  }

  @Override
  public HCounterColumn<N> apply(Long value, int ttl) {
    setValue(value);
    //counterColumn.setTtl(ttl);
    return this;
  }

  public HCounterColumn<N> apply(CounterColumn c) {
    this.counterColumn = c;
    return this;
  }
  
  @Override
  public String toString() {
    return String.format("HCounterColumn(%s=%s)",getName(), getValue());
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(getName()).append(getValue()).toHashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    if (obj.getClass() != getClass()) {
      return false;
    }
    @SuppressWarnings("unchecked")
    HCounterColumnImpl<N> other = (HCounterColumnImpl<N>) obj;
    return new EqualsBuilder().appendSuper(super.equals(obj)).append(getName(), other.getName()).
        append(getValue(), other.getValue()).isEquals();
  }
}
