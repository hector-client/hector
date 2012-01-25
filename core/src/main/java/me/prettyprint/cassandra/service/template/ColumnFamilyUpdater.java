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
        columnName, template.getTopSerializer());
  }

  public void setString(N columnName, String value) {
    HColumn<N, String> column = columnFactory.createColumn(columnName, value, clock,
        template.getTopSerializer(), StringSerializer.get());
    mutator.addInsertion(getCurrentKey(), template.getColumnFamily(), column);
  }

  public void setUUID(N columnName, UUID value) {
    HColumn<N, UUID> column = columnFactory.createColumn(columnName, value, clock,
        template.getTopSerializer(), UUIDSerializer.get());
    mutator.addInsertion(getCurrentKey(), template.getColumnFamily(), column);
  }

  public void setLong(N columnName, Long value) {
    HColumn<N, Long> column = columnFactory.createColumn(columnName, value, clock,
        template.getTopSerializer(), LongSerializer.get());
    mutator.addInsertion(getCurrentKey(), template.getColumnFamily(), column);
  }

  public void setInteger(N columnName, Integer value) {
    HColumn<N, Integer> column = columnFactory.createColumn(columnName, value, clock,
        template.getTopSerializer(), IntegerSerializer.get());
    mutator.addInsertion(getCurrentKey(), template.getColumnFamily(), column);
  }
  
  public void setDouble(N columnName, Double value) {
    HColumn<N, Double> column = columnFactory.createColumn(columnName, value, clock,
        template.getTopSerializer(), DoubleSerializer.get());
    mutator.addInsertion(getCurrentKey(), template.getColumnFamily(), column);
  }  
  
  public void setBoolean(N columnName, Boolean value) {
    HColumn<N, Boolean> column = columnFactory.createColumn(columnName, value, clock,
        template.getTopSerializer(), BooleanSerializer.get());
    mutator.addInsertion(getCurrentKey(), template.getColumnFamily(), column);
  }

  public void setByteArray(N columnName, byte[] value) {
    HColumn<N, byte[]> column = columnFactory.createColumn(columnName, value, clock,
        template.getTopSerializer(), BytesArraySerializer.get());
    mutator.addInsertion(getCurrentKey(), template.getColumnFamily(), column);
  }

  public void setByteBuffer(N columnName, ByteBuffer value) {
    HColumn<N, ByteBuffer> column = columnFactory.createColumn(columnName, value, clock,
        template.getTopSerializer(), ByteBufferSerializer.get());
    mutator.addInsertion(getCurrentKey(), template.getColumnFamily(), column);
  }
  
  public void setDate(N columnName, Date value) {
    HColumn<N, Date> column = columnFactory.createColumn(columnName, value, clock,
        template.getTopSerializer(), DateSerializer.get());
    mutator.addInsertion(getCurrentKey(), template.getColumnFamily(), column);
  }

  public <V> void setFloat(N columnName, float value) {
    HColumn<N, Float> column = columnFactory.createColumn(columnName, value, clock,
        template.getTopSerializer(), FloatSerializer.get());
  }

  public <V> void setComposite(N columnName, Composite composite) {
    HColumn<N, Composite> column = columnFactory.createColumn(columnName, composite, clock,
        template.getTopSerializer(), CompositeSerializer.get());
  }

  public <V> void setDynamicComposite(N columnName, DynamicComposite composite) {
    HColumn<N, DynamicComposite> column = columnFactory.createColumn(columnName, composite, clock,
        template.getTopSerializer(), DynamicCompositeSerializer.get());
  }

  public <V> void setValue(N columnName, V value, Serializer<V> serializer) {
    HColumn<N, V> column = columnFactory.createColumn(columnName, value, clock,
        template.getTopSerializer(), serializer);
    mutator.addInsertion(getCurrentKey(), template.getColumnFamily(), column);
  }

  public <V> void setColumn(HColumn<N, V> column) {
    mutator.addInsertion(getCurrentKey(), template.getColumnFamily(), column);
  }
}
