package me.prettyprint.cassandra.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.Cluster;
import me.prettyprint.cassandra.service.ClusterImpl;
import me.prettyprint.cassandra.utils.StringUtils;

import org.apache.cassandra.thrift.ColumnPath;

/**
 * A convenient class with bunch of factory static methods to help create a mutator, 
 * queries etc.
 *  
 * @author Ran 
 *
 */
public class HFactory {

  private static final Map<String, Cluster> clusters = new HashMap<String, Cluster>();
  
  public static Cluster getCluster(String clusterName) {
    return clusters.get(clusterName);
  }
  /**
   * 
   * @param clusterName The cluster name. This is an identifying string for the cluster, e.g. 
   * "production" or "test" etc. Clusters will be created on demand per each unique clusterName key.
   * @param hostIp host:ip format string
   * @return
   */
  public static Cluster getOrCreateCluster(String clusterName, String hostIp) {
    /*
     I would like to move off of string literals for hosts, perhaps
     providing them for convinience, and used specific types
      
     */
    return getOrCreateCluster(clusterName, new CassandraHostConfigurator(hostIp));
  }

  public static Cluster getOrCreateCluster(String clusterName, 
      CassandraHostConfigurator cassandraHostConfigurator) {
    Cluster c = clusters.get(clusterName);
    if (c == null) {
      synchronized (clusters) {
        c = clusters.get(clusterName);
        if (c == null) {
          c = createCluster(clusterName, cassandraHostConfigurator);
          clusters.put(clusterName, c);
        }
      }
    }
    return c;
  }
  
  public static Cluster createCluster(String clusterName, CassandraHostConfigurator cassandraHostConfigurator) {
    return new ClusterImpl(clusterName, cassandraHostConfigurator);
  }

  public static KeyspaceOperator createKeyspaceOperator(String keyspace, Cluster cluster) {
    return new KeyspaceOperatorImpl(keyspace, cluster);
  }

  public static <N,V> Mutator createMutator(KeyspaceOperator ko) {
    //TODO
    return null;
  }

  public static <N,V> ColumnQuery<N,V> createColumnQuery(KeyspaceOperator ko, 
      Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    return new ColumnQuery<N,V>(ko, nameExtractor, valueExtractor);
  }

  public static <SN,N,V> SuperColumnQuery<SN,N,V> createSuperColumnQuery(KeyspaceOperator ko) {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * 
   * @param <K> Row key type
   * @param keyspaceOperator
   * @return
   */
  public static <K,N,V> MultigetSliceQuery<K,N,V> createMultigetSliceQuery(KeyspaceOperator keyspaceOperator) {
    // TODO Auto-generated method stub
    return null;
  }

  
  /**
   * createSuperColumn accepts a variable number of column arguments
   * @param name supercolumn name
   * @param createColumn a variable number of column arguments
   * @return
   */
  public static <SN,N,V> HSuperColumn<SN,N,V> createSuperColumn(N name, List<HColumn<N,V>> column, 
      Extractor<V> superNameExtractor, Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    // TODO
    return null;
  }
  
  public static <N,V> HColumn<N,V> createColumn(N name, V value, Extractor<N> nameExtractor, 
      Extractor<V> valueExtractor) {
    //TODO
    return null;
  }

  
  // probably should be typed for thrift vs. avro
  /*package*/ static ColumnPath createColumnPath(String columnFamilyName, String columnName) {
    ColumnPath columnPath = new ColumnPath(columnFamilyName);
    if ( columnName != null ) {
      columnPath.setColumn(StringUtils.bytes(columnName));
    }
    return columnPath;
  }

}
