package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.service.Keyspace;

public class ColumnQueryImpl implements ColumnQuery {

  private final KeyspaceOperator keyspaceOperator;
  private String key;
  private String name;
  private String columnFamilyName;
  
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
  public Result execute() {
    
    Result result = keyspaceOperator.doExecute(new KeyspaceOperationCallback<Result>() {

      @Override
      public Result doInKeyspace(Keyspace ks) throws HectorException {
        org.apache.cassandra.thrift.Column thriftColumn = ks.getColumn(key, ModelUtils.createColumnPath(columnFamilyName, name));
        Column column = new ColumnImpl(thriftColumn);
        return new ResultImpl(column);
      }
      
    });
    return result;
  }

  @Override
  public ColumnQuery setColumnFamily(String cf) {
    this.columnFamilyName = cf;
    return this;
  }

}
