package me.prettyprint.cassandra.model;

import static me.prettyprint.cassandra.model.HFactory.createSuperColumnPath;
import static me.prettyprint.cassandra.utils.Assert.noneNull;
import static me.prettyprint.cassandra.utils.Assert.notNull;
import me.prettyprint.cassandra.service.Keyspace;

import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.SuperColumn;

public final class SuperColumnQuery<K,SN,N,V> extends AbstractQuery<K,N,V,HSuperColumn <SN,N,V>>
    implements Query<HSuperColumn<SN,N,V>> {

  private final Extractor<SN> sNameExtractor;
  private K key;
  private SN superName;

  /*package*/ public SuperColumnQuery(KeyspaceOperator keyspaceOperator,
      Extractor<K> keyExtractor, Extractor<SN> sNameExtractor, Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    super(keyspaceOperator, keyExtractor, nameExtractor, valueExtractor);
    noneNull(sNameExtractor, nameExtractor, valueExtractor);
    this.sNameExtractor = sNameExtractor;
  }

  public SuperColumnQuery<K,SN,N,V> setKey(K key, Extractor<K> keyExtractor) {
    this.key = key;
    return this;
  }

  public SuperColumnQuery<K,SN,N,V> setSuperName(SN superName) {
    this.superName = superName;
    return this;
  }

  public Result<HSuperColumn<SN, N, V>> execute() {
    notNull(columnFamilyName, "columnFamilyName is null");
    notNull(superName, "superName is null");
    return new Result<HSuperColumn<SN, N, V>>(keyspaceOperator.doExecute(
        new KeyspaceOperationCallback<HSuperColumn<SN, N, V>>() {
          @Override
          public HSuperColumn<SN, N, V> doInKeyspace(Keyspace ks) throws HectorException {
            try {
              ColumnPath cpath = createSuperColumnPath(columnFamilyName, superName, (N) null,
                  sNameExtractor, columnNameExtractor);
              SuperColumn thriftSuperColumn = ks.getSuperColumn(key, cpath, keyExtractor);
              if (thriftSuperColumn == null) {
                return null;
              }
              return new HSuperColumn<SN, N, V>(thriftSuperColumn, sNameExtractor, columnNameExtractor,
                  valueExtractor);
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
