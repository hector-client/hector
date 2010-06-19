package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.cassandra.utils.StringUtils;

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
        Result result = new ResultImpl();
        String value = StringUtils.string(ks.getColumn(key, ModelUtils.createColumnPath(columnFamilyName, name)).getValue());
        // set the value on result. this feels too clunky though
        // there has to be a smooth, mostly encapsulated way to populate the Result, but still
        // get to the Keyspace 
        
        return result;
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
