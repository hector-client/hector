package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.HConsistencyLevel;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.query.Query;

/**
 * 
 * @author patricioe (Patricio Echague - patricio@datastax.com)
 *
 * @param <K> Key type
 * @param <N> column name type
 * @param <T> return type
 */
public abstract class AbstractBasicQuery<K, N, T> implements Query<T> {

  protected final ExecutingKeyspace keyspace;
  protected String columnFamilyName;
  protected Serializer<K> keySerializer;
  protected Serializer<N> columnNameSerializer;
  // For now keep it simple and use the Thrift consistency level directly
  protected HConsistencyLevel consistency;
  // add: FailoverPolicy, Credentials?
  protected String cqlVersion;
  // default is set to false. Subclasses should check the cqlVersion and
  // set the flag as needed. Cassandra.Client class has CQL3 specific
  // method that differs from CQL1 and 2.
  protected boolean cql3 = false;

  protected AbstractBasicQuery(Keyspace k, Serializer<K> keySerializer,
      Serializer<N> nameSerializer) {
    Assert.noneNull(k, keySerializer, nameSerializer);
    keyspace = (ExecutingKeyspace) k;
    this.keySerializer = keySerializer;
    this.columnNameSerializer = nameSerializer;
  }

  public Query<T> setColumnFamily(String cf) {
    this.columnFamilyName = cf;
    return this;
  }

  public Serializer<K> getKeySerializer() {
    return keySerializer;
  }

  public AbstractBasicQuery<K, N, T> setKeySerializer(Serializer<K> keySerializer) {
    this.keySerializer = keySerializer;
    return this;
  }

  public Serializer<N> getColumnNameSerializer() {
    return columnNameSerializer;
  }

  public AbstractBasicQuery<K, N, T> setColumnNameSerializer(Serializer<N> columnNameSerializer) {
    this.columnNameSerializer = columnNameSerializer;
    return this;
  }

  public String getCqlVersion() {
    return cqlVersion;
  }

  public AbstractBasicQuery setCqlVersion(String cqlVersion) {
    this.cqlVersion = cqlVersion;
    return this;
  }
  
  public HConsistencyLevel getConsistencyLevel() {
	  return this.consistency;
  }

  public void setConsistencyLevel(HConsistencyLevel level) {
	  this.consistency = level;
  }
}
