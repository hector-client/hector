package me.prettyprint.cassandra.model.thrift;

import me.prettyprint.cassandra.model.AbstractColumnQuery;
import me.prettyprint.cassandra.model.HColumnImpl;
import me.prettyprint.cassandra.model.KeyspaceOperationCallback;
import me.prettyprint.cassandra.model.QueryResultImpl;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.TypeInferringSerializer;
import me.prettyprint.cassandra.service.KeyspaceService;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.exceptions.HNotFoundException;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.ColumnQuery;
import me.prettyprint.hector.api.query.QueryResult;

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

  public ThriftColumnQuery(Keyspace keyspace, Serializer<K> keySerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    super(keyspace, keySerializer, nameSerializer, valueSerializer);
  }

  public ThriftColumnQuery(Keyspace keyspace, Serializer<V> valueSerializer) {
    super(keyspace, TypeInferringSerializer.<K> get(), TypeInferringSerializer.<N> get(),
        valueSerializer);
  }

  @SuppressWarnings("unchecked")
  public ThriftColumnQuery(Keyspace keyspaceOperator) {
    super(keyspaceOperator, TypeInferringSerializer.<K> get(), TypeInferringSerializer.<N> get(),
        (Serializer<V>) StringSerializer.get());
  }

  @Override
  public QueryResult<HColumn<N, V>> execute() {
    return new QueryResultImpl<HColumn<N, V>>(
        keyspace.doExecute(new KeyspaceOperationCallback<HColumn<N, V>>() {

          @Override
          public HColumn<N, V> doInKeyspace(KeyspaceService ks) throws HectorException {
            try {
              Column thriftColumn = ks.getColumn(keySerializer.toBytes(key),
                  ThriftFactory.createColumnPath(columnFamilyName, name, columnNameSerializer));
              return new HColumnImpl<N, V>(thriftColumn, columnNameSerializer, valueSerializer);
            } catch (HNotFoundException e) {
              return null;
            }
          }
        }), this);
  }
}
