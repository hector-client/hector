package me.prettyprint.cassandra.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.prettyprint.cassandra.model.HectorException;
import me.prettyprint.cassandra.service.CassandraClient.FailoverPolicy;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.TokenRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of the {@link CassandraCluster} interface.
 * 
 * @author Nate McCall (nate@vervewireless.com)
 */
/* package */class CassandraClusterImpl implements CassandraCluster {

  private static Logger log = LoggerFactory.getLogger(CassandraClusterImpl.class);

  private static final String KEYSPACE_SYSTEM = "system";

  private final CassandraClientPool cassandraClientPool;
  private final FailoverPolicy failoverPolicy;
  private List<String> knownHosts;
  private final CassandraClientMonitor cassandraClientMonitor;
  private final String preferredClientUrl;
  private final ExceptionsTranslator xtrans;

  /**
   * @param cassandraClientPool
   *          The pool from which to borrow clients for the meta operations.
   * @param preferredClientUrl
   *          a url:port format. If provided, a client by this name will be
   *          borrowed from the pool. If not, a default client, if exists in the
   *          pool, will be borrowed.
   */
  public CassandraClusterImpl(CassandraClientPool cassandraClientPool, String preferredClientUrl)
      throws HectorException {
    this.cassandraClientPool = cassandraClientPool;
    this.failoverPolicy = FailoverPolicy.ON_FAIL_TRY_ALL_AVAILABLE;
    this.cassandraClientMonitor = JmxMonitor.getInstance().getCassandraMonitor();
    this.preferredClientUrl = preferredClientUrl;
    xtrans = new ExceptionsTranslatorImpl();
  }

  @Override
  public List<String> getKnownHosts(boolean fresh) throws HectorException {
    if (fresh || knownHosts == null) {
      CassandraClient client = borrow();
      try {
        knownHosts = new ArrayList<String>(buildHostNames(client.getCassandra()));
      } finally {
        cassandraClientPool.releaseClient(client);
      }
    }
    return knownHosts;
  }

  private void operateWithFailover(Operation<?> op) throws HectorException {
    CassandraClient client = null;
    try {
      client = borrow();
      FailoverOperator operator = new FailoverOperator(failoverPolicy, getKnownHosts(false),
          cassandraClientMonitor, client, cassandraClientPool, null);
      client = operator.operate(op);
    } finally {
      try {
        release(client);
      } catch (Exception e) {
        log.error("Unable to release a client", e);
      }
    }
  }

  private CassandraClient borrow() throws HectorException {
    if (preferredClientUrl == null) {
      return cassandraClientPool.borrowClient();
    } else {
      return cassandraClientPool.borrowClient(preferredClientUrl);

    }
  }

  private void release(CassandraClient c) throws Exception {
    if (c != null) {
      cassandraClientPool.releaseClient(c);
    }
  }

  @Override
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
    operateWithFailover(op);
    return op.getResult();
  }

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
    operateWithFailover(op);
    return op.getResult();
  }

  @Override
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

  @Override
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

  @Override
  public String getClusterName() throws HectorException {
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
}
