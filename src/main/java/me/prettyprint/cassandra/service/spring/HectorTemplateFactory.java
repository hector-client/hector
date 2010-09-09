package me.prettyprint.cassandra.service.spring;

import me.prettyprint.cassandra.model.ConfigurableConsistencyLevel;
import me.prettyprint.cassandra.model.ConsistencyLevelPolicy;
import me.prettyprint.cassandra.model.KeyspaceOperator;
import me.prettyprint.cassandra.service.Cluster;
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
  private String keyspace;
  private ConfigurableConsistencyLevel configurableConsistencyLevelPolicy;
  private String replicationStrategyClass;
  private int replicationFactor;

  private volatile KeyspaceOperator keyspaceOperator;

  public HectorTemplate createTemplate() {
    HectorTemplateImpl template = new HectorTemplateImpl(this);
    initKeyspaceOperator();
    template.setCluser(cluster);
    if (keyspaceOperator == null) {
      template.setKeyspaceOperator(keyspaceOperator);
    }

    return template;
  }

  public synchronized void initKeyspaceOperator() {
    if (keyspaceOperator == null) {
      // not setting the KeyspaceOperator externally,
      // because the keyspace name should be accessible from this factory
      ConsistencyLevelPolicy clPolicy;
      if (configurableConsistencyLevelPolicy == null) {
        clPolicy = HFactory.createDefaultConsistencyLevelPolicy();
      } else {
        clPolicy = configurableConsistencyLevelPolicy;
      }
      keyspaceOperator = HFactory.createKeyspaceOperator(keyspace, cluster, clPolicy);
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

  public String getKeyspace() {
    return keyspace;
  }

  public void setKeyspace(String keyspace) {
    this.keyspace = keyspace;
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
