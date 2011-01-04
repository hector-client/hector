package me.prettyprint.hom;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/spring-jpa-container-context.xml")
public class ContainerInitalizationTest extends CassandraTestBase {

  @PersistenceUnit
  private EntityManagerFactory entityManagerFactory;
  
  @Test
  public void testInjectEntityManager() {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    
    assertTrue(entityManagerFactory.isOpen());
  }
}
