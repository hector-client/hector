package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.service.Keyspace;

public class ColumnQueryImpl extends AbstractQuery<Column> implements ColumnQuery {

  private final KeyspaceOperator keyspaceOperator;
  private String key;
  private String name;
  
  ColumnQueryImpl(KeyspaceOperator keyspaceOperator) {
    this.keyspaceOperator = keyspaceOperator; 
  }
  
  @Override
  public ColumnQuery setKey(String key) {
    this.key = key;
    return this;
  }

  @Override
  public ColumnQuery setName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public Result<Column> execute() {
    
    Result<Column> result = keyspaceOperator.doExecute(new KeyspaceOperationCallback<Result<Column>>() {

      @Override
      public Result<Column> doInKeyspace(Keyspace ks) throws HectorException {
        org.apache.cassandra.thrift.Column thriftColumn = 
          ks.getColumn(key, ModelUtils.createColumnPath(columnFamilyName, name));
        Column column = new ColumnImpl(thriftColumn);
        return new ResultImpl<Column>(column);
      }
      
    });
    return result;
  }
}
