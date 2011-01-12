package me.prettyprint.hom;

import static org.junit.Assert.*;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

import me.prettyprint.hom.beans.MyTestBean;

import org.junit.Ignore;
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
  @Ignore
  public void testLookupEntityManagerFactory() {

    // does not use open-jpa
    entityManagerFactory = Persistence.createEntityManagerFactory("myprovider", buildProps());
    //entityManagerFactory = Persistence.createEntityManagerFactory("myprovider");
    
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    
    assertTrue(entityManagerFactory.isOpen());
  }
  
  @Test
  public void testStandaloneOpenJpaProviderConfig() {
    //PersistenceProviderImpl ppi = new PersistenceProviderImpl();
    entityManagerFactory = Persistence.createEntityManagerFactory("openjpa", buildProps());
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    //assertTrue(entityManager.contains(new MyTestBean()));
    
    //EntityManager entityManager = entityManagerFactory.createEntityManager();

  }
  
  private Properties buildProps() {
    Properties props = new Properties();
    props.put(EntityManagerConfigurator.CLUSTER_NAME_PROP, "TestPool");
    props.put(EntityManagerConfigurator.CLASSPATH_PREFIX_PROP, "me.prettyprint.hom.beans");
    props.put(EntityManagerConfigurator.HOST_LIST_PROP, "localhost:9161");
    props.put(EntityManagerConfigurator.KEYSPACE_PROP, "TestKeyspace");
    return props;
  }
}
