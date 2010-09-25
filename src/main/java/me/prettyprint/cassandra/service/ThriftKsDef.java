package me.prettyprint.cassandra.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.ddl.HCfDef;
import me.prettyprint.hector.api.ddl.HKsDef;

import org.apache.cassandra.thrift.KsDef;

public class ThriftKsDef implements HKsDef {

  private final String name;
  private String strategyClass;
  private Map<String, String> strategyOptions;
  private int replicationFactor;
  private final List<HCfDef> cfDefs;

  public ThriftKsDef(KsDef k) {
    Assert.notNull(k, "KsDef is null");
    name = k.name;
    strategyClass = k.strategy_class;
    strategyOptions = k.strategy_options;
    replicationFactor = k.replication_factor;
    cfDefs = ThriftCfDef.fromThriftList(k.cf_defs);
  }

  public ThriftKsDef(String keyspaceName, String strategyClass, int replicationFactor,
      List<HCfDef> cfDefs) {
    this.name = keyspaceName;
    this.strategyClass = strategyClass;
    this.replicationFactor = replicationFactor;
    this.cfDefs = cfDefs;
  }

  public static List<HKsDef> fromThriftList(List<KsDef> ks) {
    if (ks == null || ks.isEmpty()) {
      return Collections.emptyList();
    }
    List<HKsDef> l = new ArrayList<HKsDef>(ks.size());
    for (KsDef k: ks) {
      l.add(new ThriftKsDef(k));
    }
    return l;
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
    return replicationFactor;
  }

  @Override
  public List<HCfDef> getCfDefs() {
    return Collections.unmodifiableList(cfDefs);
  }

  public KsDef toThrift() {
    return new KsDef(name, strategyClass, replicationFactor, ThriftCfDef.toThriftList(cfDefs));
  }

  public void setStrategyClass(String strategyClass) {
    this.strategyClass = strategyClass;
  }

  public void setStrategyOptions(Map<String, String> strategyOptions) {
    this.strategyOptions = strategyOptions;
  }

  public void setReplicationFactor(int replicationFactor) {
    this.replicationFactor = replicationFactor;
  }

}
