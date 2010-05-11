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

  private CassandraClientPool cassandraClientPool;
  private FailoverPolicy failoverPolicy;
  private List<String> knownHosts;
  private CassandraClientMonitor cassandraClientMonitor;

  public CassandraClusterImpl(CassandraClientPool cassandraClientPool) throws PoolExhaustedException, Exception {
    this.cassandraClientPool = cassandraClientPool;
    this.failoverPolicy = FailoverPolicy.ON_FAIL_TRY_ALL_AVAILABLE;
    this.cassandraClientMonitor = JmxMonitor.getInstance().getCassandraMonitor();
    CassandraClient cassandraClient = cassandraClientPool.borrowClient();
    try {
      knownHosts = new ArrayList<String>(buildHostNames(cassandraClient.getCassandra()));
    } finally {
      cassandraClientPool.releaseClient(cassandraClient);
    }
  }

  private void operateWithFailover(Operation<?> op) throws CassandraClusterException {
    CassandraClient cassandraClient = null;
    try {
      cassandraClient = cassandraClientPool.borrowClient();
      FailoverOperator operator = new FailoverOperator(failoverPolicy, knownHosts,
          cassandraClientMonitor, cassandraClient, cassandraClientPool, null);
      operator.operate(op);
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
        cassandraClientPool.releaseClient(cassandraClient);
      } catch (Exception e) {
        log.error("Could not release connection",e);
      }
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

  @Override
  public Set<String> getHostNames() throws CassandraClusterException {
    Operation<Set<String>> op = new Operation<Set<String>>(OperationType.META_READ) {
      @Override
      public Set<String> execute(Cassandra.Client cassandra) throws InvalidRequestException,
          UnavailableException, TException, TimedOutException {
        return buildHostNames(cassandra);
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

  private static final String KEYSPACE_SYSTEM = "system";
}
