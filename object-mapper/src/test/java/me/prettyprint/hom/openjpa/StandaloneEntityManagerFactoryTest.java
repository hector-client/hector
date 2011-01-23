package me.prettyprint.hom.openjpa;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import me.prettyprint.hom.CassandraTestBase;
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
    em.close();
    
    //em.getTransaction().begin();
    
    EntityManager em2 = entityManagerFactory.createEntityManager();
    SimpleTestBean stb = em2.find(SimpleTestBean.class, 1);
    //em.getTransaction().commit();
    assertEquals("foo",stb.getName());
    

    em2.getTransaction().begin();
    em2.remove(stb);
    em2.getTransaction().commit();
    em2.close();
  }

}
