package me.prettyprint.cassandra.connection;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import me.prettyprint.cassandra.service.CassandraHost;

public interface LoadBalancingPolicy extends Serializable {
  HClientPool getPool(List<HClientPool> pools, Set<CassandraHost> excludeHosts);
  HClientPool createConnection(CassandraHost host);
}
