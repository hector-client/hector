package me.prettyprint.cassandra.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.prettyprint.cassandra.service.CassandraClient.FailoverPolicy;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.exceptions.HectorPoolException;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.CfDef;
import org.apache.cassandra.thrift.KsDef;
import org.apache.cassandra.thrift.TokenRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A cluster instance the client side representation of a cassandra server cluster.
 *
 * The cluster is usually the main entry point for programs using hector. To start operating on
 * cassandra cluster you first get or create a cluster, then a keyspace operator for the keyspace
 * you're interested in and then create mutations of queries
 * <code>
 * //get a cluster:
 * Cluster cluster = getOrCreateCluster("MyCluster", "127.0.0.1:9170");
 * //get a keyspace from this cluster:
 * Keyspace ko = createKeyspace("Keyspace1", cluster);
 * //Create a mutator:
 * Mutator m = createMutator(ko);
 * // Make a mutation:
 * MutationResult mr = m.insert("key", cf, createColumn("name", "value", serializer, serializer));
 * </code>
 *
 * THREAD SAFETY: This class is thread safe.
 *
 * @author Ran Tavory
 * @author zznate
 */
public final class Cluster {

  private final Logger log = LoggerFactory.getLogger(Cluster.class);

  private static final String KEYSPACE_SYSTEM = "system";

  private final CassandraClientPool pool;
  private final String name;
  private final CassandraHostConfigurator configurator;
  private ClockResolution clockResolution = CassandraHost.DEFAULT_TIMESTAMP_RESOLUTION;
  private final FailoverPolicy failoverPolicy;
  private final CassandraClientMonitor cassandraClientMonitor;
  private Set<String> knownClusterHosts;
  private Set<CassandraHost> knownPoolHosts;
  private final ExceptionsTranslator xtrans;

  public Cluster(String clusterName, CassandraHostConfigurator cassandraHostConfigurator) {
    pool = CassandraClientPoolFactory.INSTANCE.createNew(cassandraHostConfigurator);
    name = clusterName;
    configurator = cassandraHostConfigurator;
    failoverPolicy = FailoverPolicy.ON_FAIL_TRY_ALL_AVAILABLE;
    cassandraClientMonitor = JmxMonitor.getInstance().getCassandraMonitor();
    xtrans = new ExceptionsTranslatorImpl();
  }

  public Cluster(String clusterName, CassandraClientPool pool) {
    this.pool = pool;
    name = clusterName;
    configurator = null;
    failoverPolicy = FailoverPolicy.ON_FAIL_TRY_ALL_AVAILABLE;
    cassandraClientMonitor = JmxMonitor.getInstance().getCassandraMonitor();
    xtrans = new ExceptionsTranslatorImpl();
  }

  public Set<CassandraHost> getKnownPoolHosts(boolean refresh) {
    if (refresh || knownPoolHosts == null) {
      knownPoolHosts = pool.getKnownHosts();
      if ( log.isInfoEnabled() ) {
        log.info("found knownPoolHosts: {}", knownPoolHosts);
      }
    }
    return knownPoolHosts;
  }

  /**
   * These are all the hosts known to the cluster
   * @param refresh
   * @return
   */
  public Set<String> getClusterHosts(boolean refresh) {
    if (refresh || knownClusterHosts == null) {
      CassandraClient client = borrowClient();
      try {
        knownClusterHosts = new HashSet<String>(buildHostNames(client.getCassandra()));
      } finally {
        releaseClient(client);
      }
    }
    return knownClusterHosts;
  }

  /**
   * Adds the host to this Cluster. Unless skipApplyConfig is set to true, the settings in
   * the CassandraHostConfigurator will be applied to the provided CassandraHost
   * @param cassandraHost
   * @param skipApplyConfig
   */
  public void addHost(CassandraHost cassandraHost, boolean skipApplyConfig) {
    if (!skipApplyConfig && configurator != null) {
      configurator.applyConfig(cassandraHost);
    }
    pool.addCassandraHost(cassandraHost);
    pool.updateKnownHosts();
  }


  /**
   * Descriptive name of the cluster.
   * This name is used to identify the cluster.
   * @return
   */
  public String getName() {
    return name;
  }

  // rest of the methods from the current CassandraCluster

  public CassandraClient borrowClient() throws HectorPoolException {
    return pool.borrowClient();
  }

  public void releaseClient(CassandraClient client) {
    pool.releaseClient(client);
  }

  @Override
  public String toString() {
    return String.format("Cluster(%s,%s)", name, pool.toString());
  }

  public ClockResolution getClockResolution() {
    return clockResolution;
  }

  public Cluster setClockResolution(ClockResolution clockResolution) {
    this.clockResolution = clockResolution;
    return this;
  }

  public long createClock() {
    return clockResolution.createClock();
  }


  public List<KsDef> describeKeyspaces() throws HectorException {
    Operation<List<KsDef>> op = new Operation<List<KsDef>>(OperationType.META_READ) {
      @Override
      public List<KsDef> execute(Cassandra.Client cassandra) throws HectorException {
        try {
          return cassandra.describe_keyspaces();
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    operateWithFailover(null,op);
    return op.getResult();
  }

  public String describeClusterName() throws HectorException {
    Operation<String> op = new Operation<String>(OperationType.META_READ) {
      @Override
      public String execute(Cassandra.Client cassandra) throws HectorException {
        try {
          return cassandra.describe_cluster_name();
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    operateWithFailover(null,op);
    return op.getResult();
  }

  public String describeThriftVersion() throws HectorException {
    Operation<String> op = new Operation<String>(OperationType.META_READ) {
      @Override
      public String execute(Cassandra.Client cassandra) throws HectorException {
        try {
          return cassandra.describe_version();
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    operateWithFailover(null,op);
    return op.getResult();
  }

  public KsDef describeKeyspace(final String keyspace)
  throws HectorException {
    Operation<KsDef> op = new Operation<KsDef>(
        OperationType.META_READ) {
      @Override
      public KsDef execute(Cassandra.Client cassandra)
      throws HectorException {
        try {
          return cassandra.describe_keyspace(keyspace);
        } catch (org.apache.cassandra.thrift.NotFoundException nfe) {
          setException(xtrans.translate(nfe));
          return null;
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    operateWithFailover(null,op);
    return op.getResult();
  }


  public String getClusterName() throws HectorException {
    log.info("in execute with client");
    Operation<String> op = new Operation<String>(OperationType.META_READ) {
      @Override
      public String execute(Cassandra.Client cassandra) throws HectorException {
        try {
          if ( log.isInfoEnabled() ) {
            log.info("in execute with client {}", cassandra);
          }
          return cassandra.describe_cluster_name();
        } catch (Exception e) {
          throw xtrans.translate(e);
        }

      }
    };
    operateWithFailover(null,op);
    return op.getResult();
  }

  public List<TokenRange> describeRing(final String keyspace) throws HectorException {
    Operation<List<TokenRange>> op = new Operation<List<TokenRange>>(OperationType.META_READ) {
      @Override
      public List<TokenRange> execute(Cassandra.Client cassandra) throws HectorException {
        try {
          return cassandra.describe_ring(keyspace);
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    operateWithFailover(null,op);
    return op.getResult();
  }

  public String describePartitioner() throws HectorException {
    Operation<String> op = new Operation<String>(OperationType.META_READ) {
      @Override
      public String execute(Cassandra.Client cassandra) throws HectorException {
        try {
          if ( log.isInfoEnabled() ) {
            log.info("in execute with client {}", cassandra);
          }
          return cassandra.describe_partitioner();
        } catch (Exception e) {
          throw xtrans.translate(e);
        }

      }
    };
    operateWithFailover(null,op);
    return op.getResult();
  }

  /**
   * Renames the Keyspace from oldName to newName
   *
   * @return the new SchemaId
   * @throws HectorException
   */
  public String renameKeyspace(final String oldName, final String newName) throws HectorException {
    Operation<String> op = new Operation<String>(OperationType.META_WRITE) {
      @Override
      public String execute(Cassandra.Client cassandra) throws HectorException {
        try {
          return cassandra.system_rename_keyspace(oldName, newName);
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    operateWithFailover(oldName,op);
    return op.getResult();
  }

  /**
   * Renames the ColumnFamily from oldName to newName
   *
   * @return the new SchemaId
   * @throws HectorException
   */
  public String renameColumnFamily(final String oldName, final String newName) throws HectorException {
    Operation<String> op = new Operation<String>(OperationType.META_WRITE) {
      @Override
      public String execute(Cassandra.Client cassandra) throws HectorException {
        try {
          return cassandra.system_rename_column_family(oldName, newName);
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    operateWithFailover(null,op);
    return op.getResult();
  }

  /**
   * Drops the Keyspace from the cluster. Equivalent of 'drop database' in SQL terms
   *
   */
  public String dropKeyspace(final String keyspace) throws HectorException {
    Operation<String> op = new Operation<String>(OperationType.META_WRITE) {
      @Override
      public String execute(Cassandra.Client cassandra) throws HectorException {
        try {
          return cassandra.system_drop_keyspace(keyspace);
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    operateWithFailover(null,op);
    return op.getResult();
  }

  /**
   * Drops the Keyspace from the cluster. Equivalent of 'drop database' in SQL terms
   *
   */
  public String addKeyspace(final KsDef ksdef) throws HectorException {

    Operation<String> op = new Operation<String>(OperationType.META_WRITE) {
      @Override
      public String execute(Cassandra.Client cassandra) throws HectorException {
        try {
          return cassandra.system_add_keyspace(ksdef);
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    operateWithFailover(null,op);
    return op.getResult();
  }

  /**
   * Updates the Keyspace from the cluster.
   */
  public String updateKeyspace(final KsDef ksdef) throws HectorException {

    Operation<String> op = new Operation<String>(OperationType.META_WRITE) {
      @Override
      public String execute(Cassandra.Client cassandra) throws HectorException {
        try {
          return cassandra.system_update_keyspace(ksdef);
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    operateWithFailover(null,op);
    return op.getResult();
  }

  /**
   * Drops the ColumnFamily from the Keyspace. Equivalent of 'drop table' in SQL terms
   *
   */
  public String dropColumnFamily(final String keyspaceName, final String columnFamily) throws HectorException {
    Operation<String> op = new Operation<String>(OperationType.META_WRITE) {
      @Override
      public String execute(Cassandra.Client cassandra) throws HectorException {
        try {
          return cassandra.system_drop_column_family(columnFamily);
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    operateWithFailover(keyspaceName,op);
    return op.getResult();
  }

  public String addColumnFamily(final CfDef cfdef) throws HectorException {

    Operation<String> op = new Operation<String>(OperationType.META_WRITE) {
      @Override
      public String execute(Cassandra.Client cassandra) throws HectorException {
        try {
          return cassandra.system_add_column_family(cfdef);
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    operateWithFailover(cfdef.keyspace, op);
    return op.getResult();
  }

  public String updateColumnFamily(final CfDef cfdef) throws HectorException {

    Operation<String> op = new Operation<String>(OperationType.META_WRITE) {
      @Override
      public String execute(Cassandra.Client cassandra) throws HectorException {
        try {
          return cassandra.system_update_column_family(cfdef);
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    operateWithFailover(cfdef.keyspace, op);
    return op.getResult();
  }


  private void operateWithFailover(final String keyspaceName, Operation<?> op) throws HectorException {
    CassandraClient client = null;
    try {
      client = borrowClient();
      KeyspaceService keyspace = keyspaceName != null ? client.getKeyspace(keyspaceName) : null;
      FailoverOperator operator = new FailoverOperator(failoverPolicy,
          cassandraClientMonitor, client, pool, keyspace);
      client = operator.operate(op);
    } finally {
      try {
        releaseClient(client);
      } catch (Exception e) {
        log.error("Unable to release a client", e);
      }
    }
  }

  private Set<String> buildHostNames(Cassandra.Client cassandra) throws HectorException {
    try {
      Set<String> hostnames = new HashSet<String>();
      for (KsDef keyspace : cassandra.describe_keyspaces()) {
        if (!keyspace.getName().equals(KEYSPACE_SYSTEM)) {
          List<TokenRange> tokenRanges = cassandra.describe_ring(keyspace.getName());
          for (TokenRange tokenRange : tokenRanges) {
            for (String host : tokenRange.getEndpoints()) {
              hostnames.add(host);
            }
          }
          break;
        }
      }
      return hostnames;
    } catch (Exception e) {
      throw xtrans.translate(e);
    }
  }

}
