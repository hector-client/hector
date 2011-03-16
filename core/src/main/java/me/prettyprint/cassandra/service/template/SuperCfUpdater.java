package me.prettyprint.cassandra.service.template;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import me.prettyprint.cassandra.model.HColumnImpl;
import me.prettyprint.cassandra.serializers.BooleanSerializer;
import me.prettyprint.cassandra.serializers.ByteBufferSerializer;
import me.prettyprint.cassandra.serializers.BytesArraySerializer;
import me.prettyprint.cassandra.serializers.DateSerializer;
import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.HSuperColumn;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;

/**
 * This provides an interface of updating a specified row, most likely with the
 * contents of an object. This would likely by implemented as an anonymous inner
 * class with access to a final object in scope. It would update the given row
 * with the object's data.
 * 
 * This is currently implemented as an abstract base class instead of an
 * interface. This could change in the future. Being an abstract base class
 * allows CassandraTemplate to initialize this instance through package scope
 * field access. This means that implementation of update() simply makes
 * consecutive calls to various set****() methods which already have the
 * contextual information they need to update the correct row.
 * 
 * The downside of this approach is that the updater is essentially stateful and
 * cannot be used concurrently. The alternative is to pass an object in to
 * update() as a parameter with the setter methods, leaving the updater to be
 * stateless.
 * 
 * @author david
 * @since Mar 10, 2011
 * @param <K>
 *          the key's data type
 * @param <SN>
 *          the standard or super column's data type
 * @param <N>
 *          the child column name type in a super column
 * @param <V>
 *          the object instance to persist
 */
public abstract class SuperCfUpdater<K,SN,N,V> {
  // Values have package access and are assigned by CassandraTemplate
  K key;
  Serializer<N> subSerializer;
  List<HColumn<N, ByteBuffer>> columns;
  List<HColumn<N, ByteBuffer>> columnsToDelete;

  /**
   * The CassandraTemplate update methods for super columns take a list of
   * objects to persist. The update() method of this updater is call once for
   * every object in the collection. The appropriate internally variables are
   * initialized between calls so that the correct is saved.
   * 
   * Since the updater has intimate knowledge of the object being persisted, it
   * should also know which super column this is being saved into. It conveys
   * this to the CassandraTemplate by returning the value which will be the
   * super column name these updates will go into.
   * 
   * @param obj
   * @return the super column name where the object contents are saved
   */
  public abstract SN update(V obj);

  /**
   * Give the updater access to the current key being updated
   * 
   * @return
   */
  public K getKey() {
    return key;
  }

  public void deleteColumn(N columnName) {
    if (columnsToDelete == null) {
      columnsToDelete = new ArrayList<HColumn<N, ByteBuffer>>();
    }
    HColumn<N, ByteBuffer> col = new HColumnImpl<N, ByteBuffer>(
        subSerializer, ByteBufferSerializer.get());
    col.setName(columnName);
    columnsToDelete.add(col);
  }

  public void setString(N columnName, String value) {
    columns.add(HFactory.createColumn(columnName, StringSerializer.get()
        .toByteBuffer(value), subSerializer, ByteBufferSerializer.get()));
  }

  public void setUUID(N columnName, UUID value) {
    columns.add(HFactory.createColumn(columnName, UUIDSerializer.get()
        .toByteBuffer(value), subSerializer, ByteBufferSerializer.get()));
  }

  public void setLong(N columnName, Long value) {
    columns.add(HFactory.createColumn(columnName, LongSerializer.get()
        .toByteBuffer(value), subSerializer, ByteBufferSerializer.get()));
  }

  public void setInteger(N columnName, Integer value) {
    columns.add(HFactory.createColumn(columnName, IntegerSerializer.get()
        .toByteBuffer(value), subSerializer, ByteBufferSerializer.get()));
  }

  public void setBoolean(N columnName, Boolean value) {
    columns.add(HFactory.createColumn(columnName, BooleanSerializer.get()
        .toByteBuffer(value), subSerializer, ByteBufferSerializer.get()));
  }

  public void setByteArray(N columnName, byte[] value) {
    columns.add(HFactory.createColumn(columnName, BytesArraySerializer.get()
        .toByteBuffer(value), subSerializer, ByteBufferSerializer.get()));
  }

  public void setDate(N columnName, Date value) {
    columns.add(HFactory.createColumn(columnName, DateSerializer.get()
        .toByteBuffer(value), subSerializer, ByteBufferSerializer.get()));
  }
}
