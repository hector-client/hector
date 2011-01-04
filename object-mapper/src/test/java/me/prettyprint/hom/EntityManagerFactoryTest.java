package me.prettyprint.hom;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.Before;
import org.junit.Test;

public class EntityManagerFactoryTest extends CassandraTestBase {
  
  private EntityManagerFactory entityManagerFactory;

  
  @Test
  public void testCreateEntityManager() {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    assertNotNull(entityManager);
    assertTrue(entityManagerFactory.isOpen());    
  }

  @Test
  public void testCloseEntityManagerFactory() {
    assertTrue(entityManagerFactory.isOpen());
    entityManagerFactory.close();
    assertFalse(entityManagerFactory.isOpen());
    
  }
  
  @Before
  public void initFactory() {
    Map<String, Object> properties = new HashMap<String, Object>();
    properties.put(EntityManagerConfigurator.CLASSPATH_PREFIX_PROP, "me.prettyprint.hom.beans");
    properties.put(EntityManagerConfigurator.CLUSTER_NAME_PROP, "TestPool");
    properties.put(EntityManagerConfigurator.KEYSPACE_PROP, "TestKeyspace");
    EntityManagerConfigurator emc = new EntityManagerConfigurator(properties);
    entityManagerFactory = new EntityManagerFactoryImpl(properties);
  }
}
