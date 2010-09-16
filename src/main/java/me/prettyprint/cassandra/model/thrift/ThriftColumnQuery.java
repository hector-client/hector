package me.prettyprint.cassandra.model.thrift;

import me.prettyprint.cassandra.model.AbstractColumnQuery;
import me.prettyprint.cassandra.model.HColumn;
import me.prettyprint.cassandra.model.KeyspaceOperationCallback;
import me.prettyprint.cassandra.model.KeyspaceOperator;
import me.prettyprint.cassandra.model.Result;
import me.prettyprint.cassandra.model.Serializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.TypeInferringSerializer;
import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.hector.api.exceptions.HNotFoundException;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.ColumnQuery;

import org.apache.cassandra.thrift.Column;

/**
 * Thrift implementation of the ColumnQuery type.
 *
 * @author Ran Tavory
 *
 * @param <N>
 *          column name type
 * @param <V>
 *          value type
 */
public class ThriftColumnQuery<K, N, V> extends AbstractColumnQuery<K, N, V> implements
    ColumnQuery<K, N, V> {

  public ThriftColumnQuery(KeyspaceOperator keyspaceOperator, Serializer<K> keySerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    super(keyspaceOperator, keySerializer, nameSerializer, valueSerializer);
  }

  public ThriftColumnQuery(KeyspaceOperator keyspaceOperator, Serializer<V> valueSerializer) {
    super(keyspaceOperator, TypeInferringSerializer.<K> get(), TypeInferringSerializer.<N> get(),
        valueSerializer);
  }

  @SuppressWarnings("unchecked")
  public ThriftColumnQuery(KeyspaceOperator keyspaceOperator) {
    super(keyspaceOperator, TypeInferringSerializer.<K> get(), TypeInferringSerializer.<N> get(),
        (Serializer<V>) StringSerializer.get());
  }

  @Override
  public Result<HColumn<N, V>> execute() {
    return new Result<HColumn<N, V>>(
        keyspaceOperator.doExecute(new KeyspaceOperationCallback<HColumn<N, V>>() {

          @Override
          public HColumn<N, V> doInKeyspace(Keyspace ks) throws HectorException {
            try {
              Column thriftColumn = ks.getColumn(keySerializer.toBytes(key),
                  ThriftFactory.createColumnPath(columnFamilyName, name, columnNameSerializer));
              return new HColumn<N, V>(thriftColumn, columnNameSerializer, valueSerializer);
            } catch (HNotFoundException e) {
              return null;
            }
          }
        }), this);
  }
}
