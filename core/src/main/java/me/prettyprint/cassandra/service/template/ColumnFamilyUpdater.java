package me.prettyprint.cassandra.service.template;

import java.util.Date;
import java.util.UUID;

import me.prettyprint.cassandra.serializers.BooleanSerializer;
import me.prettyprint.cassandra.serializers.BytesArraySerializer;
import me.prettyprint.cassandra.serializers.DateSerializer;
import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.factory.HFactory;
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
public class ColumnFamilyUpdater<K, N> {
  // Values have package access and are assigned by CassandraTemplate
  ColumnFamilyTemplate<K, N> template;
  K key;

  /**
   * Default no-op update implementation. Sub-classes should override this to provide 
   * for more complex behaviour
   */
  public void update() {
    // TODO think about this contract in general
  }

  /**
   * @return Give the updater access to the current key if it needs it
   */
  public K getKey() {
    return key;
  }

  public void deleteColumn(N columnName) {
    template.getMutator().addDeletion(key, template.getColumnFamily(),
        columnName, template.getTopSerializer());
  }

  public void setString(N columnName, String value) {
    HColumn<N, String> column = HFactory.createColumn(columnName, value,
        template.getTopSerializer(), StringSerializer.get());
    template.getMutator().addInsertion(key, template.getColumnFamily(), column);
  }

  public void setUUID(N columnName, UUID value) {
    HColumn<N, UUID> column = HFactory.createColumn(columnName, value,
        template.getTopSerializer(), UUIDSerializer.get());
    template.getMutator().addInsertion(key, template.getColumnFamily(), column);
  }

  public void setLong(N columnName, Long value) {
    HColumn<N, Long> column = HFactory.createColumn(columnName, value,
        template.getTopSerializer(), LongSerializer.get());
    template.getMutator().addInsertion(key, template.getColumnFamily(), column);
  }

  public void setInteger(N columnName, Integer value) {
    HColumn<N, Integer> column = HFactory.createColumn(columnName, value,
        template.getTopSerializer(), IntegerSerializer.get());
    template.getMutator().addInsertion(key, template.getColumnFamily(), column);
  }

  public void setBoolean(N columnName, Boolean value) {
    HColumn<N, Boolean> column = HFactory.createColumn(columnName, value,
        template.getTopSerializer(), BooleanSerializer.get());
    template.getMutator().addInsertion(key, template.getColumnFamily(), column);
  }

  public void setByteArray(N columnName, byte[] value) {
    HColumn<N, byte[]> column = HFactory.createColumn(columnName, value,
        template.getTopSerializer(), BytesArraySerializer.get());
    template.getMutator().addInsertion(key, template.getColumnFamily(), column);
  }

  public void setDate(N columnName, Date value) {
    HColumn<N, Date> column = HFactory.createColumn(columnName, value,
        template.getTopSerializer(), DateSerializer.get());
    template.getMutator().addInsertion(key, template.getColumnFamily(), column);
  }
}
