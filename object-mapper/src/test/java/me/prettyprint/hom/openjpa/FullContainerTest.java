package me.prettyprint.hom.openjpa;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import me.prettyprint.hom.beans.MyTestBean;
import me.prettyprint.hom.beans.SimpleTestBean;

import org.apache.openjpa.persistence.OpenJPAEntityManager;
import org.apache.openjpa.persistence.OpenJPAEntityManagerFactorySPI;
import org.apache.openjpa.persistence.test.AbstractPersistenceTestCase;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
public class FullContainerTest extends AbstractPersistenceTestCase {
  private static Logger log = LoggerFactory.getLogger(FullContainerTest.class);
  @Ignore
  public void ptestCreateEntityManager() {
    OpenJPAEntityManagerFactorySPI emf = createNamedEMF("openjpa");
    try {
      EntityManager em = emf.createEntityManager();

      EntityTransaction t = em.getTransaction();
      assertNotNull(t);
      t.begin();
      t.setRollbackOnly();
      t.rollback();

      // openjpa-facade test
      assertTrue(em instanceof OpenJPAEntityManager);
      OpenJPAEntityManager ojem = (OpenJPAEntityManager) em;
      ojem.getFetchPlan().setMaxFetchDepth(1);
      assertEquals(1, ojem.getFetchPlan().getMaxFetchDepth());
      em.close();
    } finally {
      closeEMF(emf);
    }
  }
  
  public void testPersistEntity() {
    OpenJPAEntityManagerFactorySPI emf = createNamedEMF("openjpa");
    
    
    try {
      EntityManager em = emf.createEntityManager();
      SimpleTestBean stb = new SimpleTestBean();
      stb.setId(1);
      stb.setName("my name");
      em.persist(stb);
      
      
    } catch (Exception e) {
      e.printStackTrace();
    
    } finally {
      closeEMF(emf);
    }
  }
}
