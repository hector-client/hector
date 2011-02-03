package me.prettyprint.hom.openjpa;

import javax.persistence.EntityManager;

import me.prettyprint.cassandra.utils.TimeUUIDUtils;
import me.prettyprint.hom.beans.SimpleRelationshipBean;
import me.prettyprint.hom.beans.SimpleTestBean;

import org.junit.Test;


public class OneToManyRelationshipTest extends ManagedEntityTestBase {

  @Test
  public void testManyToOneSaveAndLoad() {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();

    SimpleRelationshipBean srb = new SimpleRelationshipBean();
    srb.setBaseId(TimeUUIDUtils.getUniqueTimeUUIDinMillis());
    srb.setMyType("my_type");
    srb.addSimpleTestBean(new SimpleTestBean(5L, "my owned bean"));    
    
    em.persist(srb);
    
    //em.persist(new SimpleTestBean(1, "foo"));
    em.getTransaction().commit();
    em.close();
  }
}
