package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.service.Keyspace;

// like a simple get operation
// may return a Column or a SuperColumn
public class ColumnQuery extends AbstractQuery<Column> implements Query<Column> {
  private final KeyspaceOperator keyspaceOperator;
  private String key;
  private String name;
  
  ColumnQuery(KeyspaceOperator keyspaceOperator) {
    this.keyspaceOperator = keyspaceOperator; 
  }
  
  public ColumnQuery setKey(String key) {
    this.key = key;
    return this;
  }

  public ColumnQuery setName(String name) {
    this.name = name;
    return this;
  }

  public Result<Column> execute() {    
    Result<Column> result = keyspaceOperator.doExecute(new KeyspaceOperationCallback<Result<Column>>() {
      @Override
      public Result<Column> doInKeyspace(Keyspace ks) throws HectorException {
        org.apache.cassandra.thrift.Column thriftColumn = 
          ks.getColumn(key, ModelUtils.createColumnPath(columnFamilyName, name));
        Column column = new Column(thriftColumn);
        return new Result<Column>(column);
      }
      
    });
    return result;
  }
}
