package me.prettyprint.cassandra.connection;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

public interface LoadBalancingPolicy extends Serializable {
  HClientPool getPool(Collection<HClientPool> pools, Set<HCassandraHost> excludeHosts);
  HClientPool createConnection(HCassandraHost host);
}
