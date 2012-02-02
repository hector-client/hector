package me.prettyprint.cassandra.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.prettyprint.cassandra.connection.HConnectionManager;
import me.prettyprint.hector.api.ClockResolution;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.exceptions.HectorException;
import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.thrift.TException;
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
 * Cluster cluster = getOrCreateCluster("MyCluster", new CassandraHostConfigurator("127.0.0.1:9170"));
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
  private static final Map<String, String> EMPTY_CREDENTIALS = Collections.emptyMap();

  private final Logger log = LoggerFactory.getLogger(AbstractCluster.class);
  
  /**
   * Linked to Cassandra StorageProxy.
   */
  public static final int RING_DELAY = 30 * 1000; // delay after which we assume ring has stablized
  public static final int WAIT_FOR_SCHEMA_AGREEMENT_SLEEP_TIME = 1000;

  protected final HConnectionManager connectionManager;
  private final String name;
  private final CassandraHostConfigurator configurator;
  private final ClockResolution clockResolution;
  private final FailoverPolicy failoverPolicy;
  private final CassandraClientMonitor cassandraClientMonitor;
  private Set<CassandraHost> knownPoolHosts;
  protected final ExceptionsTranslator xtrans;
  private final Map<String, String> credentials;

  public AbstractCluster(String clusterName, CassandraHostConfigurator cassandraHostConfigurator) {
    this(clusterName, cassandraHostConfigurator, EMPTY_CREDENTIALS);
  }

  public AbstractCluster(String clusterName, CassandraHostConfigurator cassandraHostConfigurator, Map<String, String> credentials) {
    connectionManager = new HConnectionManager(clusterName, cassandraHostConfigurator);
    name = clusterName;
    configurator = cassandraHostConfigurator;
    failoverPolicy = FailoverPolicy.ON_FAIL_TRY_ALL_AVAILABLE;
    cassandraClientMonitor = JmxMonitor.getInstance().getCassandraMonitor(connectionManager);
    xtrans = new ExceptionsTranslatorImpl();
    clockResolution = cassandraHostConfigurator.getClockResolution();
    this.credentials = Collections.unmodifiableMap(credentials == null? EMPTY_CREDENTIALS : credentials);
  }

  @Override
  public HConnectionManager getConnectionManager() {
    return connectionManager;
  }

  /* (non-Javadoc)
   * @see me.prettyprint.cassandra.service.Cluster#getKnownPoolHosts(boolean)
   */
  @Override
  public Set<CassandraHost> getKnownPoolHosts(boolean refresh) {
    if (refresh || knownPoolHosts == null) {
      knownPoolHosts = connectionManager.getHosts();
      if ( log.isInfoEnabled() ) {
        log.info("found knownPoolHosts: {}", knownPoolHosts);
      }
    }
    return knownPoolHosts;
  }


  /* (non-Javadoc)
   * @see me.prettyprint.cassandra.service.Cluster#addHost(me.prettyprint.cassandra.service.CassandraHost, boolean)
   */
  @Override
  public void addHost(CassandraHost cassandraHost, boolean skipApplyConfig) {
    if (!skipApplyConfig && configurator != null) {
      configurator.applyConfig(cassandraHost);
    }
    connectionManager.addCassandraHost(cassandraHost);
  }


  /* (non-Javadoc)
   * @see me.prettyprint.cassandra.service.Cluster#getName()
   */
  @Override
  public String getName() {
    return name;
  }





  /* (non-Javadoc)
   * @see me.prettyprint.cassandra.service.Cluster#describeKeyspaces()
   */
  @Override
  public List<KeyspaceDefinition> describeKeyspaces() throws HectorException {
    Operation<List<KeyspaceDefinition>> op = new Operation<List<KeyspaceDefinition>>(OperationType.META_READ, getCredentials()) {
      @Override
      public List<KeyspaceDefinition> execute(Cassandra.Client cassandra) throws HectorException {
        try {
          return ThriftKsDef.fromThriftList(cassandra.describe_keyspaces());
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    connectionManager.operateWithFailover(op);
    return op.getResult();
  }

  /* (non-Javadoc)
   * @see me.prettyprint.cassandra.service.Cluster#describeClusterName()
   */
  @Override
  public String describeClusterName() throws HectorException {
    Operation<String> op = new Operation<String>(OperationType.META_READ, getCredentials()) {
      @Override
      public String execute(Cassandra.Client cassandra) throws HectorException {
        try {
          return cassandra.describe_cluster_name();
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    connectionManager.operateWithFailover(op);
    return op.getResult();
  }

  /* (non-Javadoc)
   * @see me.prettyprint.cassandra.service.Cluster#describeThriftVersion()
   */
  @Override
  public String describeThriftVersion() throws HectorException {
    Operation<String> op = new Operation<String>(OperationType.META_READ, getCredentials()) {
      @Override
      public String execute(Cassandra.Client cassandra) throws HectorException {
        try {
          return cassandra.describe_version();
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    connectionManager.operateWithFailover(op);
    return op.getResult();
  }

  /* (non-Javadoc)
   * @see me.prettyprint.cassandra.service.Cluster#describeKeyspace(java.lang.String)
   */
  @Override
  public KeyspaceDefinition describeKeyspace(final String keyspace)
  throws HectorException {
    Operation<KeyspaceDefinition> op = new Operation<KeyspaceDefinition>(
        OperationType.META_READ, getCredentials()) {
      @Override
      public KeyspaceDefinition execute(Cassandra.Client cassandra)
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
    connectionManager.operateWithFailover(op);
    return op.getResult();
  }

  /* (non-Javadoc)
   * @see me.prettyprint.cassandra.service.Cluster#getClusterName()
   */
  @Override
  public String getClusterName() throws HectorException {
   return describeClusterName();
  }

  @Override
  public String dropKeyspace(final String keyspace) throws HectorException {
    return dropKeyspace(keyspace, false);
  }
  
  @Override
  public String dropKeyspace(final String keyspace, final boolean waitForSchemaAgreement) throws HectorException {
    Operation<String> op = new Operation<String>(OperationType.META_WRITE, FailoverPolicy.FAIL_FAST, getCredentials()) {
      @Override
      public String execute(Cassandra.Client cassandra) throws HectorException {
        try {
          if (waitForSchemaAgreement) {
            waitForSchemaAgreement(cassandra);
          }
          String schemaId = cassandra.system_drop_keyspace(keyspace);
          if (waitForSchemaAgreement) {
            waitForSchemaAgreement(cassandra);
          }
          return schemaId;
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    connectionManager.operateWithFailover(op);
    return op.getResult();
  }

  @Override
  public String describePartitioner() throws HectorException {
    Operation<String> op = new Operation<String>(OperationType.META_READ, getCredentials()) {
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
    connectionManager.operateWithFailover(op);
    return op.getResult();
  }

  @Override
  public String dropColumnFamily(final String keyspaceName, final String columnFamily) throws HectorException {
    return dropColumnFamily(keyspaceName, columnFamily, false);
  }

  @Override
  public String dropColumnFamily(final String keyspaceName, final String columnFamily,  final boolean waitForSchemaAgreement) throws HectorException {
    Operation<String> op = new Operation<String>(OperationType.META_WRITE, FailoverPolicy.FAIL_FAST, keyspaceName, getCredentials()) {
      @Override
      public String execute(Cassandra.Client cassandra) throws HectorException {
        try {
          if (waitForSchemaAgreement) {
            waitForSchemaAgreement(cassandra);
          }
          String schemaId = cassandra.system_drop_column_family(columnFamily);
          if (waitForSchemaAgreement) {
            waitForSchemaAgreement(cassandra);
          }
          return schemaId;
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    connectionManager.operateWithFailover(op);
    return op.getResult();
  }


  @Override
  public Map<String, String> getCredentials() {
    return credentials;
  }

  @Override
  public void truncate(final String keyspaceName, final String columnFamily) throws HectorException {
    Operation<Void> op = new Operation<Void>(OperationType.META_WRITE, FailoverPolicy.FAIL_FAST, keyspaceName, getCredentials()) {
      @Override
      public Void execute(Cassandra.Client cassandra) throws HectorException {
        try {
          cassandra.truncate(columnFamily);
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
        return null;
      }      
    };
    connectionManager.operateWithFailover(op);
  }

  @Override
  public void onStartup() {
    // default implementation does nothing
  }

  public CassandraHostConfigurator getConfigurator() {
    return configurator;
  }

  protected static void waitForSchemaAgreement(Cassandra.Client cassandra) throws InvalidRequestException, TException, InterruptedException {
    int waited = 0;
    int versions = 0;
    while (versions != 1) {
      versions = 0;
      Map<String, List<String>> schema = cassandra.describe_schema_versions();
      for (Map.Entry<String, List<String>> entry : schema.entrySet()) {
        if (!entry.getKey().equals("UNREACHABLE"))
          versions++;
      }

      if (versions != 1) {
        Thread.sleep(WAIT_FOR_SCHEMA_AGREEMENT_SLEEP_TIME);
        waited += WAIT_FOR_SCHEMA_AGREEMENT_SLEEP_TIME;
        if (waited > RING_DELAY)
          throw new RuntimeException("Could not reach schema agreement in " + RING_DELAY + "ms");
      }
    }
  }

}
