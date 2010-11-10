package me.prettyprint.cassandra.connection;

import java.util.Collection;
import java.util.Set;

import me.prettyprint.cassandra.service.CassandraHost;

public interface LoadBalancingPolicy {

  ConcurrentHClientPool getPool(Collection<ConcurrentHClientPool> pools, Set<CassandraHost> excludeHosts);
}
