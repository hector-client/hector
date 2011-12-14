package me.prettyprint.cassandra.model.thrift;

import me.prettyprint.cassandra.model.AbstractBasicQuery;
import me.prettyprint.cassandra.model.HCounterColumnImpl;
import me.prettyprint.cassandra.model.KeyspaceOperationCallback;
import me.prettyprint.cassandra.model.QueryResultImpl;
import me.prettyprint.cassandra.serializers.TypeInferringSerializer;
import me.prettyprint.cassandra.service.KeyspaceService;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HCounterColumn;
import me.prettyprint.hector.api.exceptions.HNotFoundException;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.CounterQuery;
import me.prettyprint.hector.api.query.QueryResult;

import org.apache.cassandra.thrift.CounterColumn;

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
public class ThriftCounterColumnQuery<K, N> extends AbstractBasicQuery<K, N, HCounterColumn<N>> 
    implements CounterQuery<K, N> {
	
  protected K key;
  protected N name;

  public ThriftCounterColumnQuery(Keyspace keyspace, Serializer<K> keySerializer,
      Serializer<N> nameSerializer) {
    super(keyspace, keySerializer, nameSerializer);
  }

  public ThriftCounterColumnQuery(Keyspace keyspace) {
    super(keyspace, TypeInferringSerializer.<K> get(), TypeInferringSerializer.<N> get());
  }
  
  public CounterQuery<K, N> setKey(K key) {
    this.key = key;
    return this;
  }

  public CounterQuery<K, N> setName(N name) {
    this.name = name;
    return this;
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public CounterQuery<K, N> setColumnFamily(String cf) {
    return (CounterQuery<K, N>) super.setColumnFamily(cf);
  }

  @Override
  public QueryResult<HCounterColumn<N>> execute() {
    return new QueryResultImpl<HCounterColumn<N>>(
        keyspace.doExecute(new KeyspaceOperationCallback<HCounterColumn<N>>() {

          @Override
          public HCounterColumn<N> doInKeyspace(KeyspaceService ks) throws HectorException {
            try {
              CounterColumn thriftCounter = ks.getCounter(keySerializer.toByteBuffer(key),
                  ThriftFactory.createColumnPath(columnFamilyName, name, columnNameSerializer));
              return new HCounterColumnImpl<N>(thriftCounter, columnNameSerializer);
            } catch (HNotFoundException e) {
              return null;
            }
          }
        }), this);
  }

}
