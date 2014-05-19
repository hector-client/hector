package me.prettyprint.cassandra.service.template;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.UUID;

import me.prettyprint.cassandra.serializers.*;
import me.prettyprint.hector.api.ColumnFactory;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.Composite;
import me.prettyprint.hector.api.beans.DynamicComposite;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.mutation.Mutator;

/**
 * This provides an interface of updating a specified row, most likely with the
 * contents of an object. This would likely by implemented as an anonymous inner
 * class with access to a final object in scope. It would update the given row
 * with the object's data.
 * 
 * For more complex behaviour, subclasses should implementat update() to simply make
 * consecutive calls to various set****() methods which already have the
 * contextual information they need to update the correct row.
 * 
 * The downside of this approach is that the updater is essentially stateful and
 * cannot be used concurrently. The alternative is to pass an object in to
 * update() as a parameter with the setter methods, leaving the updater to be
 * stateless.
 * 
 * @author david
 * @author zznate
 * 
 * @param <K>
 *          the key's data type
 * @param <N>
 *          the standard or super column's data type
 */
public class ColumnFamilyUpdater<K, N> extends AbstractTemplateUpdater<K,N> {
  
  /**
   * Constructs a ColumnFamilyUpdater with the provided {@link ColumnFamilyTemplate}
   * and {@link ColumnFactory}. A {@link Mutator} is created internally for this updater.
   * @param template
   * @param columnFactory
   */
  public ColumnFamilyUpdater(ColumnFamilyTemplate<K, N> template, ColumnFactory columnFactory) {
    super(template, columnFactory, template.createMutator());
  }
   
  /**
   * Same as 2-arg version, except we use the provided {@link Mutator} 
   * @param template
   * @param columnFactory
   * @param mutator
   */
  public ColumnFamilyUpdater(ColumnFamilyTemplate<K, N> template, ColumnFactory columnFactory, Mutator<K> mutator) {
    super(template, columnFactory, mutator);
  }
  
  public void deleteColumn(N columnName) {
    mutator.addDeletion(getCurrentKey(), template.getColumnFamily(),
        columnName, template.getTopSerializer(), clock);
  }
 
  public void setString(N columnName, String value) {
    addInsertion(columnName,value,StringSerializer.get(), globalTtl); 
  }
  
  public void setString(N columnName, String value, int ttl) { 
    addInsertion(columnName,value,StringSerializer.get(),ttl); 
  }
  
  public void setUUID(N columnName, UUID value) {
    addInsertion(columnName, value, UUIDSerializer.get(), globalTtl); 
  }
  
  public void setUUID(N columnName, UUID value, int ttl) { 
    addInsertion(columnName, value, UUIDSerializer.get(), ttl); 
  }

  public void setLong(N columnName, Long value) {
    addInsertion(columnName, value, LongSerializer.get(), globalTtl); 
  }
  
  public void setLong(N columnName, Long value, int ttl) { 
    addInsertion(columnName, value, LongSerializer.get(), ttl);
  }

  public void setInteger(N columnName, Integer value) {
    addInsertion(columnName, value, IntegerSerializer.get(), globalTtl); 
  }
  
  public void setInteger(N columnName, Integer value, int ttl) {
    addInsertion(columnName, value, IntegerSerializer.get(), ttl); 
  }
  
  public void setFloat(N columnName, Float value) {
    addInsertion(columnName, value, FloatSerializer.get(), globalTtl); 
  }

  public void setFloat(N columnName, Float value, int ttl) {
    addInsertion(columnName, value, FloatSerializer.get(), ttl); 
  }
  
  public void setDouble(N columnName, Double value) {
    addInsertion(columnName, value, DoubleSerializer.get(), globalTtl);
  }
  
  public void setDouble(N columnName, Double value, int ttl) {
    addInsertion(columnName, value, DoubleSerializer.get(), ttl);
  }  
  
  public void setBoolean(N columnName, Boolean value) {
    addInsertion(columnName, value, BooleanSerializer.get(), globalTtl);
  }
  
  public void setBoolean(N columnName, Boolean value, int ttl) {
    addInsertion(columnName, value, BooleanSerializer.get(), ttl);
  }
  
  public void setByteArray(N columnName, byte[] value) {
    addInsertion(columnName, value, BytesArraySerializer.get(), globalTtl);
  }

  public void setByteArray(N columnName, byte[] value, int ttl) {
    addInsertion(columnName, value, BytesArraySerializer.get(), ttl);
  }
  
  public void setByteBuffer(N columnName, ByteBuffer value) {
    addInsertion(columnName, value, ByteBufferSerializer.get(), globalTtl);
  }
  
  public void setByteBuffer(N columnName, ByteBuffer value, int ttl) {
    addInsertion(columnName, value, ByteBufferSerializer.get(), globalTtl);
  }
  
  public void setDate(N columnName, Date value) {
    addInsertion(columnName, value, DateSerializer.get(), globalTtl);
  }

  public <V> void setFloat(N columnName, float value) {
    addInsertion(columnName, value, FloatSerializer.get(), globalTtl);
  }

  public <V> void setComposite(N columnName, Composite composite) {
    addInsertion(columnName, composite, CompositeSerializer.get(), globalTtl);
  }

  public <V> void setComposite(N columnName, Composite composite, int ttl) {
    addInsertion(columnName, composite, CompositeSerializer.get(), ttl);
  }
  
  public <V> void setDynamicComposite(N columnName, DynamicComposite composite) {
    addInsertion(columnName, composite, DynamicCompositeSerializer.get(), globalTtl);
  }
  
  public <V> void setDynamicComposite(N columnName, DynamicComposite composite, int ttl) {
    addInsertion(columnName, composite, DynamicCompositeSerializer.get(), ttl);
  }

  public <V> void setValue(N columnName, V value, Serializer<V> serializer) {
    addInsertion(columnName, value, serializer, globalTtl);
  }
  
  public <V> void setValue(N columnName, V value, Serializer<V> serializer, int ttl) {
    addInsertion(columnName, value, serializer, ttl);
  }
  
  private <V> void addInsertion(N columnName, V value, Serializer<V> valueSerializer, int ttl) { 
    HColumn<N,V> column = columnFactory.createColumn(columnName, value, clock, template.getTopSerializer(), valueSerializer);
    
    if(ttl > DEF_TTL) { 
      column.setTtl(ttl);
    } 
    
    mutator.addInsertion(getCurrentKey(), template.getColumnFamily(), column);
  }
  
  public <V> void setColumn(HColumn<N, V> column) {
    mutator.addInsertion(getCurrentKey(), template.getColumnFamily(), column);
  }
}
