package me.prettyprint.cassandra.model;

import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: peter
 * Date: 20/10/2010
 * Time: 4:11:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class BasicKeyspaceDefinition implements KeyspaceDefinition {

  private String name;
  private String strategyClass;
  private int replicationFactor;
  private Map<String, String> strategyOptions;
  private List<ColumnFamilyDefinition> columnFamilyList;

  public BasicKeyspaceDefinition(){
    this.columnFamilyList = new ArrayList<ColumnFamilyDefinition>();
    this.strategyOptions = new HashMap<String, String>();
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public String getStrategyClass() {
    return this.strategyClass;
  }

  @Override
  public Map<String, String> getStrategyOptions() {
    return this.strategyOptions;
  }

  @Override
  public int getReplicationFactor() {
    return this.replicationFactor;
  }

  @Override
  public List<ColumnFamilyDefinition> getCfDefs() {
    return this.columnFamilyList;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setStrategyClass(String strategyClass) {
    this.strategyClass = strategyClass;
  }

  public void setReplicationFactor(int replicationFactor) {
    this.replicationFactor = replicationFactor;
  }

  public void addColumnFamily( ColumnFamilyDefinition columnFamilyDefinition ){
    this.columnFamilyList.add(columnFamilyDefinition);
  }

  public void removeColumnFamily( ColumnFamilyDefinition columnFamilyDefinition ){
    this.columnFamilyList.remove(columnFamilyDefinition);
  }

  public void setStrategyOption( String field, String value ){
    this.strategyOptions.put( field, value);
  }

  public void removeStrategyOption( String field ){
    this.strategyOptions.remove( field );
  }

}
