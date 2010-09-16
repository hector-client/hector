package me.prettyprint.cassandra.model;

import java.util.List;

import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.exceptions.HectorException;

import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.SuperColumn;


/**
 * A query for the thrift call get_slice.
 * <p>
 * Get a slice of super columns from a super column family.
 *
 * @author Ran Tavory
 *
 * @param <N>
 * @param <V>
 */
public final class SuperSliceQuery<SN,N,V> extends AbstractSliceQuery<N,V,SuperSlice<SN,N,V>> {

  private String key;
  private final Serializer<SN> sNameSerializer;

  public SuperSliceQuery(KeyspaceOperator ko, Serializer<SN> sNameSerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    super(ko, nameSerializer, valueSerializer);
    Assert.notNull(sNameSerializer, "sNameSerializer cannot be null");
    this.sNameSerializer = sNameSerializer;
  }

  public SuperSliceQuery<SN,N,V> setKey(String key) {
    this.key = key;
    return this;
  }

  @Override
  public Result<SuperSlice<SN,N,V>> execute() {
    return new Result<SuperSlice<SN,N,V>>(keyspaceOperator.doExecute(
        new KeyspaceOperationCallback<SuperSlice<SN,N,V>>() {
          @Override
          public SuperSlice<SN,N,V> doInKeyspace(Keyspace ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            List<SuperColumn> thriftRet = ks.getSuperSlice(key, columnParent, getPredicate());
            return new SuperSlice<SN,N,V>(thriftRet, sNameSerializer, columnNameSerializer, valueSerializer);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "SuperSliceQuery(" + key + "," + toStringInternal() + ")";
  }
}
