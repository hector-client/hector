package me.prettyprint.hom;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import me.prettyprint.hom.beans.SimpleTestBean;

import org.junit.Test;

public class StandaloneEntityManagerFactoryTest extends CassandraTestBase {
  
  private EntityManagerFactory entityManagerFactory;
  
  @Test
  public void testBuildEntityManagerFactory() {
    entityManagerFactory = Persistence.createEntityManagerFactory("openjpa");  
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    em.persist(new SimpleTestBean(1, "foo"));
    em.getTransaction().commit();
    SimpleTestBean stb = em.find(SimpleTestBean.class, 1);
    
    assertNotNull(stb);
  }

}
