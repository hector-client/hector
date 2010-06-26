package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.service.Keyspace;
import static me.prettyprint.cassandra.model.HFactory.*;

// like a simple get operation
// may return a Column or a SuperColumn
public class ColumnQuery<N, V> extends AbstractQuery<HColumn<N, V>> implements Query<HColumn<N, V>> {
  private final KeyspaceOperator keyspaceOperator;
  private final Extractor<N> nameExtractor;
  private final Extractor<V> valueExtractor;
  private String key;
  private String name;
  
  ColumnQuery(KeyspaceOperator keyspaceOperator, Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    this.keyspaceOperator = keyspaceOperator; 
    this.nameExtractor = nameExtractor;
    this.valueExtractor = valueExtractor;
  }
  
  public ColumnQuery<N, V> setKey(String key) {
    this.key = key;
    return this;
  }

  public ColumnQuery<N, V> setName(String name) {
    this.name = name;
    return this;
  }

  public Result<HColumn<N, V>> execute() {    
    Result<HColumn<N, V>> result = keyspaceOperator.doExecute(
        new KeyspaceOperationCallback<Result<HColumn<N, V>>>() {
      @Override
      public Result<HColumn<N, V>> doInKeyspace(Keyspace ks) throws HectorException {
        org.apache.cassandra.thrift.Column thriftColumn = 
          ks.getColumn(key, createColumnPath(columnFamilyName, name));
        HColumn<N, V> column = new HColumn<N, V>(thriftColumn, nameExtractor, valueExtractor);
        return new Result<HColumn<N, V>>(column);
      }
      
    });
    return result;
  }
}
