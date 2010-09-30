package me.prettyprint.cassandra.model;

import java.util.Map;

import me.prettyprint.cassandra.service.KeyspaceService;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.QueryResult;

import org.apache.cassandra.thrift.ColumnParent;

/**
 * Does a multi_get_count within a given superColumn
 *
 * @author zznate
 */
public class MultigetSubCountQuery<K,SN,N> extends MultigetCountQuery<K, N> {

  private final Serializer<SN> superNameSerializer;
  private SN superColumnName;

  public MultigetSubCountQuery(Keyspace ko,
      Serializer<SN> superNameSerializer,
      Serializer<K> keySerializer,
      Serializer<N> nameSerializer) {
    super(ko, keySerializer, nameSerializer);
    Assert.notNull(superNameSerializer, "superNameSerializer is null");
    this.superNameSerializer = superNameSerializer;
  }

  public MultigetSubCountQuery<K,SN,N> setSuperColumn(SN sc) {
    superColumnName = sc;
    return this;
  }

  @Override
  public QueryResult<Map<K, Integer>> execute() {
    Assert.notNull(keys, "keys list is null");
    Assert.notNull(columnFamily, "columnFamily is null");
    Assert.notNull(superColumnName, "superColumnName is null");
    return new QueryResultImpl<Map<K,Integer>>(keyspace.doExecute(
        new KeyspaceOperationCallback<Map<K,Integer>>() {
          @Override
          public Map<K,Integer> doInKeyspace(KeyspaceService ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamily);
            columnParent.setSuper_column(superNameSerializer.toBytes(superColumnName));
            Map<K,Integer> counts = keySerializer.fromBytesMap(
                ks.multigetCount(keySerializer.toBytesList(keys), columnParent, slicePredicate.toThrift()));
            return counts;
          }
        }), this);
  }
}
