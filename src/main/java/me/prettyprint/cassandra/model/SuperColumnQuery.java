package me.prettyprint.cassandra.model;

import static me.prettyprint.cassandra.utils.Assert.noneNull;
import static me.prettyprint.cassandra.utils.Assert.notNull;
import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.hector.api.query.Query;

import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.SuperColumn;

public final class SuperColumnQuery<SN,N,V> extends AbstractQuery<N,V,HSuperColumn <SN,N,V>>
    implements Query<HSuperColumn<SN,N,V>> {

  private final Serializer<SN> sNameSerializer;
  private String key;
  private SN superName;

  /*package*/ public SuperColumnQuery(KeyspaceOperator keyspaceOperator,
      Serializer<SN> sNameSerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    super(keyspaceOperator, nameSerializer, valueSerializer);
    noneNull(sNameSerializer, nameSerializer, valueSerializer);
    this.sNameSerializer = sNameSerializer;
  }

  public SuperColumnQuery<SN,N,V> setKey(String key) {
    this.key = key;
    return this;
  }

  public SuperColumnQuery<SN,N,V> setSuperName(SN superName) {
    this.superName = superName;
    return this;
  }

  @Override
  public Result<HSuperColumn<SN, N, V>> execute() {
    notNull(columnFamilyName, "columnFamilyName is null");
    notNull(superName, "superName is null");
    return new Result<HSuperColumn<SN, N, V>>(keyspaceOperator.doExecute(
        new KeyspaceOperationCallback<HSuperColumn<SN, N, V>>() {
          @Override
          public HSuperColumn<SN, N, V> doInKeyspace(Keyspace ks) throws HectorException {
            try {
              ColumnPath cpath = ThriftFactory.createSuperColumnPath(columnFamilyName, superName, (N) null,
                  sNameSerializer, columnNameSerializer);
              SuperColumn thriftSuperColumn = ks.getSuperColumn(key, cpath);
              if (thriftSuperColumn == null) {
                return null;
              }
              return new HSuperColumn<SN, N, V>(thriftSuperColumn, sNameSerializer, columnNameSerializer,
                  valueSerializer);
            } catch (NotFoundException e) {
              return null;
            }
          }
        }), this);
  }

  @Override
  public String toString() {
    return "SuperColumnQuery(" + key + "," + superName + ")";
  }
}
