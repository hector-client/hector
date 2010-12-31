package me.prettyprint.hom;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import me.prettyprint.cassandra.service.CassandraHostConfigurator;

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
    entityManagerFactory = new EntityManagerFactoryImpl("TestPool", "TestKeyspace", 
        "me.prettyprint.hom.beans", new CassandraHostConfigurator("localhost:9161"));
  }
}
