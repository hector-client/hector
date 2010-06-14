package me.prettyprint.cassandra.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.prettyprint.cassandra.service.CassandraClient.FailoverPolicy;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.cassandra.thrift.NotFoundException;
import org.apache.cassandra.thrift.TimedOutException;
import org.apache.cassandra.thrift.TokenRange;
import org.apache.cassandra.thrift.UnavailableException;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of the {@link CassandraCluster} interface.
 *
 * @author Nate McCall (nate@vervewireless.com)
 */
/*package*/ class CassandraClusterImpl implements CassandraCluster {

  private static Logger log = LoggerFactory.getLogger(CassandraClusterImpl.class);

  private static final String KEYSPACE_SYSTEM = "system";

  private final CassandraClientPool cassandraClientPool;
  private final FailoverPolicy failoverPolicy;
  private List<String> knownHosts;
  private final CassandraClientMonitor cassandraClientMonitor;
  private final String preferredClientUrl;

  /**
   * @param cassandraClientPool The pool from which to borrow clients for the meta operations.
   * @param preferredClientUrl a url:port format. If provided, a client by this name will be borrowed
   *    from the pool. If not, a default client, if exists in the pool, will be borrowed. 
   */
  public CassandraClusterImpl(CassandraClientPool cassandraClientPool, String preferredClientUrl) 
      throws PoolExhaustedException, Exception {
    this.cassandraClientPool = cassandraClientPool;
    this.failoverPolicy = FailoverPolicy.ON_FAIL_TRY_ALL_AVAILABLE;
    this.cassandraClientMonitor = JmxMonitor.getInstance().getCassandraMonitor();
    this.preferredClientUrl = preferredClientUrl;
  }

  @Override
  public List<String> getKnownHosts(boolean fresh) throws IllegalStateException, PoolExhaustedException, Exception {
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
  
  private void operateWithFailover(Operation<?> op) throws CassandraClusterException {
    CassandraClient client = null;
    try {
      client = borrow();
      FailoverOperator operator = new FailoverOperator(failoverPolicy, getKnownHosts(false),
          cassandraClientMonitor, client, cassandraClientPool, null);
      client = operator.operate(op);
    } catch (InvalidRequestException ire) {
      throw new CassandraClusterException("Invalid request",ire);
    } catch (UnavailableException e) {
      throw new CassandraClusterException("Endpoint unavailable",e);
    } catch (TException e) {
      throw new CassandraClusterException("Thrift Exception",e);
    } catch (TimedOutException e) {
      throw new CassandraClusterException("Connection timed out", e);
    } catch (PoolExhaustedException e) {
      throw new CassandraClusterException("Pool Was Exhausted", e);
    } catch (Exception e) {
      throw new CassandraClusterException("General Exception", e);
    } finally {
      try {
        release(client);
      } catch (Exception e) {
        log.error("Unable to release a client", e);
      }
    }
  }

  private CassandraClient borrow() throws IllegalStateException, PoolExhaustedException, Exception {
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
  public Set<String> describeKeyspaces() throws CassandraClusterException {
    Operation<Set<String>> op = new Operation<Set<String>>(OperationType.META_READ) {
      @Override
      public Set<String> execute(Cassandra.Client cassandra) throws InvalidRequestException,
          UnavailableException, TException, TimedOutException {
        return cassandra.describe_keyspaces();
      }
    };
    operateWithFailover(op);
    return op.getResult();
  }

  @Override
  public String describeClusterName() throws CassandraClusterException {
    Operation<String> op = new Operation<String>(OperationType.META_READ) {
      @Override
      public String execute(Cassandra.Client cassandra) throws InvalidRequestException,
          UnavailableException, TException, TimedOutException {
        return cassandra.describe_cluster_name();
      }
    };
    operateWithFailover(op);
    return op.getResult();
  }

  @Override
  public String describeThriftVersion() throws CassandraClusterException {
    Operation<String> op = new Operation<String>(OperationType.META_READ) {
      @Override
      public String execute(Cassandra.Client cassandra) throws InvalidRequestException,
          UnavailableException, TException, TimedOutException {
        return cassandra.describe_version();
      }
    };
    operateWithFailover(op);
    return op.getResult();
  }

  @Override
  public List<TokenRange> describeRing(final String keyspace) throws CassandraClusterException {
    Operation<List<TokenRange>> op = new Operation<List<TokenRange>>(OperationType.META_READ) {
      @Override
      public List<TokenRange> execute(Cassandra.Client cassandra) throws InvalidRequestException,
          UnavailableException, TException, TimedOutException {
        return cassandra.describe_ring(keyspace);
      }
    };
    operateWithFailover(op);
    return op.getResult();
  }

  private Set<String> buildHostNames(Cassandra.Client cassandra) throws TException {
    Set<String> hostnames = new HashSet<String>();
    for (String keyspace : cassandra.describe_keyspaces()) {
      if (!keyspace.equals(KEYSPACE_SYSTEM)) {
        List<TokenRange> tokenRanges = cassandra.describe_ring(keyspace);
        for (TokenRange tokenRange : tokenRanges) {
          for (String host : tokenRange.getEndpoints()){
            hostnames.add(host);
          }
        }
        break;
      }
    }
    return hostnames;
  }

  @Override
  public Map<String, Map<String, String>> describeKeyspace(final String keyspace) throws CassandraClusterException {
    Operation<Map<String, Map<String, String>>> op = new Operation<Map<String, Map<String, String>>>(OperationType.META_READ) {
      @Override
      public Map<String, Map<String, String>> execute(Cassandra.Client cassandra) throws InvalidRequestException,
          UnavailableException, TException, TimedOutException {
        try {
          return cassandra.describe_keyspace(keyspace);
        } catch (NotFoundException nfe) {
          setException(nfe);
          return null;
        }
      }
    };
    operateWithFailover(op);
    return op.getResult();
  }

  @Override
  public String getClusterName() {
    Operation<String> op = new Operation<String>(OperationType.META_READ) {
      @Override
      public String execute(Cassandra.Client cassandra) throws InvalidRequestException,
          UnavailableException, TException, TimedOutException {
        return cassandra.describe_cluster_name();
      }
    };
    operateWithFailover(op);
    return op.getResult();
  }
}
