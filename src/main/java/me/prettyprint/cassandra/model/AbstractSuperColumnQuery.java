package me.prettyprint.cassandra.model;

import static me.prettyprint.cassandra.utils.Assert.noneNull;
import me.prettyprint.hector.api.query.SuperColumnQuery;

/**
 * Defines the commonalities b/w avro and thrift SuperColumnQuery implementations
 *
 * @author Ran Tavory
 *
 * @param <SN>
 * @param <N>
 * @param <V>
 */
/*package*/ abstract class AbstractSuperColumnQuery<SN, N, V> extends
    AbstractQuery<N, V, HSuperColumn<SN, N, V>> implements SuperColumnQuery<SN, N, V>{

  protected final Serializer<SN> sNameSerializer;
  protected String key;
  protected SN superName;

  public AbstractSuperColumnQuery(KeyspaceOperator ko, Serializer<SN> sNameSerializer,
      Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    super(ko, nameSerializer, valueSerializer);
    noneNull(sNameSerializer, nameSerializer, valueSerializer);
    this.sNameSerializer = sNameSerializer;
  }

  @Override
  public SuperColumnQuery<SN, N, V> setKey(String key) {
    this.key = key;
    return this;
  }

  @Override
  public SuperColumnQuery<SN, N, V> setSuperName(SN superName) {
    this.superName = superName;
    return this;
  }

  @SuppressWarnings("unchecked")
  @Override
  public SuperColumnQuery<SN, N, V> setColumnFamily(String cf) {
    return (SuperColumnQuery<SN, N, V>) super.setColumnFamily(cf);
  }

  @Override
  public String toString() {
    return "SuperColumnQuery(" + key + "," + superName + ")";
  }
}