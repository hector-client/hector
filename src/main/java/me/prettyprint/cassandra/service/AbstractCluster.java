package me.prettyprint.cassandra.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.prettyprint.cassandra.service.CassandraClient.FailoverPolicy;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.ddl.HKsDef;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.exceptions.HectorPoolException;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Cassandra.Client;
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
public abstract class AbstractCluster implements Cluster {

  private final Logger log = LoggerFactory.getLogger(AbstractCluster.class);

  protected static final String KEYSPACE_SYSTEM = "system";

  private final CassandraClientPool pool;
  private final String name;
  private final CassandraHostConfigurator configurator;
  private ClockResolution clockResolution = CassandraHost.DEFAULT_TIMESTAMP_RESOLUTION;
  private final FailoverPolicy failoverPolicy;
  private final CassandraClientMonitor cassandraClientMonitor;
  private Set<String> knownClusterHosts;
  private Set<CassandraHost> knownPoolHosts;
  protected final ExceptionsTranslator xtrans;

  public AbstractCluster(String clusterName, CassandraHostConfigurator cassandraHostConfigurator) {
    pool = CassandraClientPoolFactory.INSTANCE.createNew(cassandraHostConfigurator);
    name = clusterName;
    configurator = cassandraHostConfigurator;
    failoverPolicy = FailoverPolicy.ON_FAIL_TRY_ALL_AVAILABLE;
    cassandraClientMonitor = JmxMonitor.getInstance().getCassandraMonitor();
    xtrans = new ExceptionsTranslatorImpl();
  }

  public AbstractCluster(String clusterName, CassandraClientPool pool) {
    this.pool = pool;
    name = clusterName;
    configurator = null;
    failoverPolicy = FailoverPolicy.ON_FAIL_TRY_ALL_AVAILABLE;
    cassandraClientMonitor = JmxMonitor.getInstance().getCassandraMonitor();
    xtrans = new ExceptionsTranslatorImpl();
  }

  /* (non-Javadoc)
   * @see me.prettyprint.cassandra.service.Cluster#getKnownPoolHosts(boolean)
   */
  @Override
  public Set<CassandraHost> getKnownPoolHosts(boolean refresh) {
    if (refresh || knownPoolHosts == null) {
      knownPoolHosts = pool.getKnownHosts();
      log.info("found knownPoolHosts: {}", knownPoolHosts);
    }
    return knownPoolHosts;
  }

  @Override
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

  protected abstract Set<String> buildHostNames(Client cassandra);

  /* (non-Javadoc)
   * @see me.prettyprint.cassandra.service.Cluster#addHost(me.prettyprint.cassandra.service.CassandraHost, boolean)
   */
  @Override
  public void addHost(CassandraHost cassandraHost, boolean skipApplyConfig) {
    if (!skipApplyConfig && configurator != null) {
      configurator.applyConfig(cassandraHost);
    }
    pool.addCassandraHost(cassandraHost);
    pool.updateKnownHosts();
  }


  /* (non-Javadoc)
   * @see me.prettyprint.cassandra.service.Cluster#getName()
   */
  @Override
  public String getName() {
    return name;
  }

  @Override
  public ClockResolution getClockResolution() {
    return clockResolution;
  }

  @Override
  public Cluster setClockResolution(ClockResolution clockResolution) {
    this.clockResolution = clockResolution;
    return this;
  }


  /* (non-Javadoc)
   * @see me.prettyprint.cassandra.service.Cluster#borrowClient()
   */
  @Override
  public CassandraClient borrowClient() throws HectorPoolException {
    return pool.borrowClient();
  }

  /* (non-Javadoc)
   * @see me.prettyprint.cassandra.service.Cluster#releaseClient(me.prettyprint.cassandra.service.CassandraClient)
   */
  @Override
  public void releaseClient(CassandraClient client) {
    pool.releaseClient(client);
  }


  /* (non-Javadoc)
   * @see me.prettyprint.cassandra.service.Cluster#createTimestamp()
   */
  @Override
  public long createClock() {
    return clockResolution.createClock();
  }



  /* (non-Javadoc)
   * @see me.prettyprint.cassandra.service.Cluster#describeKeyspaces()
   */
  @Override
  public List<HKsDef> describeKeyspaces() throws HectorException {
    Operation<List<HKsDef>> op = new Operation<List<HKsDef>>(OperationType.META_READ) {
      @Override
      public List<HKsDef> execute(Cassandra.Client cassandra) throws HectorException {
        try {
          return ThriftKsDef.fromThriftList(cassandra.describe_keyspaces());
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    operateWithFailover(null, op);
    return op.getResult();
  }

  /* (non-Javadoc)
   * @see me.prettyprint.cassandra.service.Cluster#describeClusterName()
   */
  @Override
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
    operateWithFailover(null, op);
    return op.getResult();
  }

  /* (non-Javadoc)
   * @see me.prettyprint.cassandra.service.Cluster#describeThriftVersion()
   */
  @Override
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
    operateWithFailover(null, op);
    return op.getResult();
  }

  /* (non-Javadoc)
   * @see me.prettyprint.cassandra.service.Cluster#describeKeyspace(java.lang.String)
   */
  @Override
  public HKsDef describeKeyspace(final String keyspace)
  throws HectorException {
    Operation<HKsDef> op = new Operation<HKsDef>(
        OperationType.META_READ) {
      @Override
      public HKsDef execute(Cassandra.Client cassandra)
      throws HectorException {
        try {
          return new ThriftKsDef(cassandra.describe_keyspace(keyspace));
        } catch (org.apache.cassandra.thrift.NotFoundException nfe) {
          setException(xtrans.translate(nfe));
          return null;
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    operateWithFailover(null, op);
    return op.getResult();
  }

  /* (non-Javadoc)
   * @see me.prettyprint.cassandra.service.Cluster#getClusterName()
   */
  @Override
  public String getClusterName() throws HectorException {
    log.info("in execute with client");
    Operation<String> op = new Operation<String>(OperationType.META_READ) {
      @Override
      public String execute(Cassandra.Client cassandra) throws HectorException {
        try {
          log.info("in execute with client {}", cassandra);
          return cassandra.describe_cluster_name();
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    operateWithFailover(null, op);
    return op.getResult();
  }

  @Override
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

  @Override
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

  @Override
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

  @Override
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

  @Override
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
  protected void operateWithFailover(final String keyspaceName, Operation<?> op) throws HectorException {
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

}
