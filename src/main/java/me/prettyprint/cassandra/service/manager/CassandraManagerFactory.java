package me.prettyprint.cassandra.service.manager;

import me.prettyprint.cassandra.model.ConfigurableConsistencyLevel;
import me.prettyprint.cassandra.model.ConsistencyLevelPolicy;
import me.prettyprint.cassandra.model.HFactory;
import me.prettyprint.cassandra.model.KeyspaceOperator;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.Cluster;

public class CassandraManagerFactory {

    private ThreadLocal<CassandraManager> currentManager = new ThreadLocal<CassandraManager>();

    private String clusterName;
    private String keyspace;
    private CassandraHostConfigurator hostConfigurator;
    private ConfigurableConsistencyLevel configurableConsistencyLevelPolicy;

    public CassandraManager createManager() {
        CassandraManagerImpl manager = new CassandraManagerImpl();
        Cluster cluster = HFactory.getOrCreateCluster(clusterName, hostConfigurator);

        ConsistencyLevelPolicy clPolicy;
        if (configurableConsistencyLevelPolicy == null) {
            clPolicy = HFactory.createDefaultConsistencyLevelPolicy();
        } else {
            clPolicy = configurableConsistencyLevelPolicy;
        }
        KeyspaceOperator keyspaceOperator = HFactory.createKeyspaceOperator(keyspace, cluster, clPolicy);

        manager.setCluser(cluster);
        manager.setKeyspaceOperator(keyspaceOperator);

        return manager;
    }

    public CassandraManager currentManager() {
        CassandraManager current = currentManager.get();
        if (current == null) {
            current = createManager();
            currentManager.set(current);
        }

        return current;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public CassandraHostConfigurator getHostConfigurator() {
        return hostConfigurator;
    }

    public void setHostConfigurator(CassandraHostConfigurator hostConfigurator) {
        this.hostConfigurator = hostConfigurator;
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
}
