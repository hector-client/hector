package me.prettyprint.cassandra.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.exceptions.HectorException;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.TokenRange;

public class ThriftCluster extends AbstractCluster implements Cluster {

  public ThriftCluster(String clusterName, CassandraHostConfigurator cassandraHostConfigurator) {
    super(clusterName, cassandraHostConfigurator);
  }

  public ThriftCluster(String clusterName, CassandraClientPool pool) {
    super(clusterName, pool);
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

  @Override
  protected Set<String> buildHostNames(Cassandra.Client cassandra) throws HectorException {
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
