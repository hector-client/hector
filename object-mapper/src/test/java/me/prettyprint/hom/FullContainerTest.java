package me.prettyprint.hom;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.openjpa.persistence.OpenJPAEntityManager;
import org.apache.openjpa.persistence.OpenJPAEntityManagerFactorySPI;
import org.apache.openjpa.persistence.test.AbstractPersistenceTestCase;

public class FullContainerTest extends AbstractPersistenceTestCase {

  public void testCreateEntityManager() {
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
}
