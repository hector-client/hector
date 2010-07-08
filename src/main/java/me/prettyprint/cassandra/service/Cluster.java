package me.prettyprint.cassandra.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.prettyprint.cassandra.model.HectorException;
import me.prettyprint.cassandra.model.HectorPoolException;
import me.prettyprint.cassandra.service.CassandraClient.FailoverPolicy;

import org.apache.cassandra.thrift.Cassandra;
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
 * KeyspaceOperator ko = createKeyspaceOperator("Keyspace1", cluster);
 * //Create a mutator:
 * Mutator m = createMutator(ko);
 * // Make a mutation:
 * MutationResult mr = m.insert("key", cf, createColumn("name", "value", extractor, extractor));
 * </code>
 * @author Ran Tavory
 * @author zznate
 */
public class Cluster {

  private final Logger log = LoggerFactory.getLogger(Cluster.class);

  private static final String KEYSPACE_SYSTEM = "system";

  private final CassandraClientPool pool;
  private final String name;
  private final CassandraHostConfigurator configurator;
  private TimestampResolution timestampResolution = CassandraHost.DEFAULT_TIMESTAMP_RESOLUTION;
  private final FailoverPolicy failoverPolicy;
  private final CassandraClientMonitor cassandraClientMonitor;
  private Set<String> knownClusterHosts;
  private Set<String> knownPoolHosts;
  private final ExceptionsTranslator xtrans;

  public Cluster(String clusterName, CassandraHostConfigurator cassandraHostConfigurator) {
    pool = CassandraClientPoolFactory.INSTANCE.createNew(cassandraHostConfigurator);
    name = clusterName;
    configurator = cassandraHostConfigurator;
    failoverPolicy = FailoverPolicy.ON_FAIL_TRY_ALL_AVAILABLE;
    cassandraClientMonitor = JmxMonitor.getInstance().getCassandraMonitor();
    xtrans = new ExceptionsTranslatorImpl();
  }

  public Set<String> getKnownPoolHosts(boolean refresh) {
    if (refresh || knownPoolHosts == null) {
      knownPoolHosts = pool.getKnownHosts();
      log.info("found knownPoolHosts: {}", knownPoolHosts);
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
    if (!skipApplyConfig) {
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

  public TimestampResolution getTimestampResolution() {
    return timestampResolution;
  }

  public Cluster setTimestampResolution(TimestampResolution timestampResolution) {
    this.timestampResolution = timestampResolution;
    return this;
  }

  public long createTimestamp() {
    return timestampResolution.createTimestamp();
  }


  public Set<String> describeKeyspaces() throws HectorException {
    Operation<Set<String>> op = new Operation<Set<String>>(OperationType.META_READ) {
      @Override
      public Set<String> execute(Cassandra.Client cassandra) throws HectorException {
        try {
          return cassandra.describe_keyspaces();
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    operateWithFailover(op);
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
    operateWithFailover(op);
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
    operateWithFailover(op);
    return op.getResult();
  }

  public Map<String, Map<String, String>> describeKeyspace(final String keyspace)
  throws HectorException {
    Operation<Map<String, Map<String, String>>> op = new Operation<Map<String, Map<String, String>>>(
        OperationType.META_READ) {
      @Override
      public Map<String, Map<String, String>> execute(Cassandra.Client cassandra)
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
    operateWithFailover(op);
    return op.getResult();
  }


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
    operateWithFailover(op);
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
    operateWithFailover(op);
    return op.getResult();
  }

  private void operateWithFailover(Operation<?> op) throws HectorException {
    CassandraClient client = null;
    try {
      client = borrowClient();
      FailoverOperator operator = new FailoverOperator(failoverPolicy, new ArrayList<String>(getKnownPoolHosts(false)),
          cassandraClientMonitor, client, pool, null);
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
      for (String keyspace : cassandra.describe_keyspaces()) {
        if (!keyspace.equals(KEYSPACE_SYSTEM)) {
          List<TokenRange> tokenRanges = cassandra.describe_ring(keyspace);
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
