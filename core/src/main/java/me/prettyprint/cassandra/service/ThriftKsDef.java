package me.prettyprint.cassandra.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;

import org.apache.cassandra.thrift.CfDef;
import org.apache.cassandra.thrift.KsDef;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.math.NumberUtils;

public class ThriftKsDef implements KeyspaceDefinition {

  private static final String REPLICATION_FACTOR = "replication_factor";
  public static final String DEF_STRATEGY_CLASS = "org.apache.cassandra.locator.SimpleStrategy";
  private final String name;
  private String strategyClass;
  private Map<String, String> strategyOptions = new HashMap<String, String>();
  private final List<ColumnFamilyDefinition> cfDefs;

  public ThriftKsDef(KsDef k) {
    Assert.notNull(k, "KsDef is null");
    name = k.name;
    strategyClass = k.strategy_class;
    strategyOptions = k.strategy_options != null ? k.strategy_options : new HashMap<String, String>();
    setReplicationFactor(NumberUtils.toInt(strategyOptions.get(REPLICATION_FACTOR), 1));
    cfDefs = ThriftCfDef.fromThriftList(k.cf_defs);
  }

  public ThriftKsDef(String keyspaceName, String strategyClass, int replicationFactor,
      List<ColumnFamilyDefinition> cfDefs) {
    this.name = keyspaceName;
    this.strategyClass = strategyClass;
    setReplicationFactor(replicationFactor);
    this.cfDefs = cfDefs;
  }

  public ThriftKsDef(String keyspaceName) {
    this.name = keyspaceName;
    this.cfDefs = new ArrayList<ColumnFamilyDefinition>();
    setReplicationFactor(1);
    this.strategyClass = DEF_STRATEGY_CLASS;
  }
  
  public ThriftKsDef(KeyspaceDefinition keyspaceDefinition) {
    name = keyspaceDefinition.getName();
    strategyClass = keyspaceDefinition.getStrategyClass();
    strategyOptions = keyspaceDefinition.getStrategyOptions();
    setReplicationFactor(keyspaceDefinition.getReplicationFactor());
    cfDefs = keyspaceDefinition.getCfDefs();
  }

  public static List<KeyspaceDefinition> fromThriftList(List<KsDef> ks) {
    if (ks == null || ks.isEmpty()) {
      return Collections.emptyList();
    }
    List<KeyspaceDefinition> l = new ArrayList<KeyspaceDefinition>(ks.size());
    for (KsDef k: ks) {
      l.add(new ThriftKsDef(k));
    }
    return l;
  }

  public void addCfDef(CfDef cfDef) {
    cfDefs.add(new ThriftCfDef(cfDef));
  }
  
  public void addColumnFamilyDefinition(ColumnFamilyDefinition columnFamilyDefinition) {
    cfDefs.add(new ThriftCfDef(columnFamilyDefinition));
  }
  
  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getStrategyClass() {
    return strategyClass;
  }

  @Override
  public Map<String, String> getStrategyOptions() {
    return Collections.unmodifiableMap(strategyOptions);
  }

  @Override
  public int getReplicationFactor() {
    return NumberUtils.toInt(strategyOptions.get(REPLICATION_FACTOR), 1);
  }

  @Override
  public List<ColumnFamilyDefinition> getCfDefs() {
    return Collections.unmodifiableList(cfDefs);
  }

  public KsDef toThrift() {
    KsDef def =  new KsDef(name, strategyClass, ThriftCfDef.toThriftList(cfDefs));    
    def.setStrategy_options(strategyOptions);
    return def;
  }

  public void setStrategyClass(String strategyClass) {
    this.strategyClass = strategyClass;
  }

  public void setStrategyOptions(Map<String, String> strategyOptions) {
    this.strategyOptions = strategyOptions;
  }

  public void setReplicationFactor(int replicationFactor) {
    // Compensate for CASSANDRA-1263 (wasnt my idea)
    strategyOptions.put(REPLICATION_FACTOR,Integer.toString(replicationFactor));
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }

}
