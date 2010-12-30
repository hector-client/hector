package me.prettyprint.hom;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.factory.HFactory;

public class EntityManagerFactoryImpl implements EntityManagerFactory {

  private Logger log = LoggerFactory.getLogger(EntityManagerFactoryImpl.class);
  
  private Map<String, Object> properties;
  private final Cluster cluster;
  private final String keyspaceName;
  private final String classpathPrefix;
  
  public EntityManagerFactoryImpl(Map<String, Object> properties) {
    this.properties = properties;
    this.cluster = HFactory.getOrCreateCluster("ormcluster", new CassandraHostConfigurator());
    this.keyspaceName = "Keyspace1";
    this.classpathPrefix = properties.get(EntityManagerConfigurator.CLASSPATH_PREFIX_PROP).toString();
    // hector.hom.keyspace
    // hector.hom.clusterName
    // hector.hom.classpathPrefix
    // 
    // cassandraHostConfigurator properties
    // Steps:
    // 1. initialize Hector 
    // 2. aquire keyspace
    // 3. build the cache mgr
    // 4. build the objectMapper
  }
  
  public EntityManagerFactoryImpl(String clusterName,
      String keyspaceName,
      String classpathPrefix,
      CassandraHostConfigurator cassandraHostConfigurator) {
    this.cluster = HFactory.getOrCreateCluster(clusterName, cassandraHostConfigurator);
    this.keyspaceName = keyspaceName;
    this.classpathPrefix = classpathPrefix;
  }
  
  @Override
  public void close() {
    cluster.getConnectionManager().shutdown();
  }

  @Override
  public EntityManager createEntityManager() {
    EntityManagerImpl entityManager = new EntityManagerImpl(HFactory.createKeyspace(keyspaceName, cluster), classpathPrefix);
    log.debug("Built entityManager {}", entityManager);
    return entityManager;
  }

  @Override
  public EntityManager createEntityManager(Map map) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isOpen() {
    try {
      cluster.describeClusterName();
      return true;
    } catch (HectorException he) {
      log.debug("isOpen failed to connecto to cluster: {}",he.getMessage());
    }
    return false;
  }

}
