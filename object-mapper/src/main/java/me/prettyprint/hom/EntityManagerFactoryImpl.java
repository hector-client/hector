package me.prettyprint.hom;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.factory.HFactory;

public class EntityManagerFactoryImpl implements EntityManagerFactory {

  private Logger log = LoggerFactory.getLogger(EntityManagerFactoryImpl.class);

  private EntityManagerConfigurator entityManagerConfigurator;
  private Cluster cluster;
  
  public EntityManagerFactoryImpl(Map<String, Object> properties) {
    this(new EntityManagerConfigurator(properties));

    // cassandraHostConfigurator properties
    // Steps:
    // 1. initialize Hector 
    // 2. aquire keyspace
    // 3. build the cache mgr (internal to EntityManager)
    // 4. build the objectMapper (internal to EntityManger)
  }
  
  public EntityManagerFactoryImpl(EntityManagerConfigurator entityManagerConfigurator) {
    this.entityManagerConfigurator = entityManagerConfigurator;
    this.cluster = HFactory.getOrCreateCluster(entityManagerConfigurator.getClusterName(), 
        entityManagerConfigurator.getCassandraHostConfigurator());

  }
  
  @Override
  public void close() {
    cluster.getConnectionManager().shutdown();
  }

  @Override
  public EntityManager createEntityManager() {
    log.debug("building EMF");
    return buildEntityManager(entityManagerConfigurator.getKeyspace());
  }
  
  private EntityManagerImpl buildEntityManager(String keyspace) {
    EntityManagerImpl entityManager = new EntityManagerImpl(HFactory.
        createKeyspace(keyspace, cluster), 
        entityManagerConfigurator.getClasspathPrefix());
    log.debug("Built entityManager {}", entityManager);
    return entityManager; 
  }

  /**
   * Looks for the {@link EntityManagerConfigurator#KEYSPACE_PROP} using the 
   * keyspace already present on the internal EntityManagerConfigurator if already found
   * @param map
   * @return
   */
  @SuppressWarnings("unchecked")
  @Override
  public EntityManager createEntityManager(Map map) {
    log.debug("building EMF with props");
    String keyspaceStr = EntityManagerConfigurator.getPropertyGently(map, EntityManagerConfigurator.KEYSPACE_PROP, false);
    if ( StringUtils.isBlank(keyspaceStr) ) {
      keyspaceStr = entityManagerConfigurator.getKeyspace();
    }            
    return buildEntityManager(keyspaceStr);
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
