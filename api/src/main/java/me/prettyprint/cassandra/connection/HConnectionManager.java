package me.prettyprint.cassandra.connection;

import java.util.*;

import me.prettyprint.hector.api.ClockResolution;
import me.prettyprint.hector.api.exceptions.*;

import org.apache.cassandra.thrift.AuthenticationRequest;
import org.apache.cassandra.thrift.Cassandra;
import org.cliffc.high_scale_lib.NonBlockingHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecyrd.speed4j.StopWatch;
import com.ecyrd.speed4j.StopWatchFactory;
import com.ecyrd.speed4j.log.PeriodicalLog;

public interface HConnectionManager {

 

  /**
   * Returns true if the host was successfully added. In any sort of failure exceptions are 
   * caught and logged, returning false.
   * @param cassandraHost
   * @return
   */
  public boolean addCassandraHost(CassandraHost cassandraHost);

  /**
   * Remove the {@link CassandraHost} from the pool, bypassing retry service. This
   * would be called on a host that is known to be going away. Gracefully shuts down
   * the underlying connections via {@link HClientPool#shutdown()}. This method
   * will also shutdown pools in the suspended state, removing them from the underlying
   * suspended map.
   * @param cassandraHost
   */
  public boolean removeCassandraHost(CassandraHost cassandraHost);
  
  /**
   * Remove the {@link HClientPool} referenced by the {@link CassandraHost} from 
   * the active host pools. This does not shut down the pool, only removes it as a candidate from
   * future operations.
   * @param cassandraHost
   * @return true if the operation was successful.
   */
  public boolean suspendCassandraHost(CassandraHost cassandraHost);

  /** 
   * The opposite of suspendCassandraHost, places the pool back into selection
   * @param cassandraHost
   * @return true if this operation was successful. A no-op returning false 
   * if there was no such host in the underlying suspendedHostPool map.
   */
  public boolean unsuspendCassandraHost(CassandraHost cassandraHost);
  
  /**
   * Returns a Set of {@link CassandraHost} which are in the suspended status
   * @return
   */
  public Set<CassandraHost> getSuspendedCassandraHosts();
  
  public Set<CassandraHost> getHosts() ;

  public List<String> getStatusPerPool();

  public void operateWithFailover(Operation<?> op) throws HectorException;

  private HClientPool getClientFromLBPolicy(Set<CassandraHost> excludeHosts);

  void releaseClient(HThriftClient client);

  HThriftClient borrowClient();

  void markHostAsDown(CassandraHost cassandraHost);

  public Set<CassandraHost> getDownedHosts();

  public Collection<HClientPool> getActivePools();

  public long createClock();
  
  public String getClusterName();

  public void shutdown();


}
