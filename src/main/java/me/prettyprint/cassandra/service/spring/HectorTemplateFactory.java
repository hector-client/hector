package me.prettyprint.cassandra.service.spring;

import me.prettyprint.cassandra.model.ConfigurableConsistencyLevel;
import me.prettyprint.cassandra.service.Cluster;
import me.prettyprint.hector.api.ConsistencyLevelPolicy;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;

/**
 * Factory for creating a template or obtaining the current one
 *
 * @author Bozhidar Bozhanov
 *
 */
public class HectorTemplateFactory {

  private final ThreadLocal<HectorTemplate> currentManager = new ThreadLocal<HectorTemplate>();

  private Cluster cluster;
  private String keyspaceName;
  private ConfigurableConsistencyLevel configurableConsistencyLevelPolicy;
  private String replicationStrategyClass;
  private int replicationFactor;

  private volatile Keyspace keyspace;

  public HectorTemplate createTemplate() {
    HectorTemplateImpl template = new HectorTemplateImpl(cluster, keyspaceName, replicationFactor,
        replicationStrategyClass, configurableConsistencyLevelPolicy);
    initKeyspaceOperator();
    if (keyspace == null) {
       initKeyspaceOperator();
    }
    template.setKeyspaceName(keyspaceName);

    return template;
  }

  public synchronized void initKeyspaceOperator() {
    if (keyspace == null) {
      // not setting the KeyspaceOperator externally,
      // because the keyspace name should be accessible from this factory
      ConsistencyLevelPolicy clPolicy;
      if (configurableConsistencyLevelPolicy == null) {
        clPolicy = HFactory.createDefaultConsistencyLevelPolicy();
      } else {
        clPolicy = configurableConsistencyLevelPolicy;
      }
      keyspace = HFactory.createKeyspace(keyspaceName, cluster, clPolicy);
    }
  }

  public HectorTemplate currentTemplate() {
    HectorTemplate current = currentManager.get();
    if (current == null) {
      current = createTemplate();
      currentManager.set(current);
    }

    return current;
  }

  public String getKeyspaceName() {
    return keyspaceName;
  }

  public void setKeyspace(String keyspace) {
    this.keyspaceName = keyspace;
  }

  public ConfigurableConsistencyLevel getConfigurableConsistencyLevelPolicy() {
    return configurableConsistencyLevelPolicy;
  }

  public void setConfigurableConsistencyLevelPolicy(
      ConfigurableConsistencyLevel configurableConsistencyLevelPolicy) {
    this.configurableConsistencyLevelPolicy = configurableConsistencyLevelPolicy;
  }

  public String getReplicationStrategyClass() {
    return replicationStrategyClass;
  }

  public void setReplicationStrategyClass(String replicationStretegyClass) {
    this.replicationStrategyClass = replicationStretegyClass;
  }

  public int getReplicationFactor() {
    return replicationFactor;
  }

  public void setReplicationFactor(int replicationFactor) {
    this.replicationFactor = replicationFactor;
  }

  public Cluster getCluster() {
    return cluster;
  }

  public void setCluster(Cluster cluster) {
    this.cluster = cluster;
  }
}
