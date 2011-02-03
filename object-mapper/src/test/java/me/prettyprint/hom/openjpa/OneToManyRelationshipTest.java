package me.prettyprint.hom.openjpa;

import static org.junit.Assert.*;

import java.util.UUID;

import javax.persistence.EntityManager;

import me.prettyprint.cassandra.utils.TimeUUIDUtils;
import me.prettyprint.hom.beans.SimpleRelationshipBean;
import me.prettyprint.hom.beans.SimpleTestBean;
import me.prettyprint.hom.service.EntitySchemaManager;
import me.prettyprint.hom.service.EntitySchemaStatus;

import org.apache.openjpa.persistence.JPAFacadeHelper;
import org.junit.Before;
import org.junit.Test;


public class OneToManyRelationshipTest extends ManagedEntityTestBase {

  @Before
  public void setupSchema() {
    EntitySchemaManager entitySchemaManager = new EntitySchemaManager(cluster, keyspace);
    EntitySchemaStatus ecs = entitySchemaManager.createSchema(new EntityFacade(JPAFacadeHelper.getMetaData(entityManagerFactory, SimpleRelationshipBean.class)));
  }
  
  @Test
  public void testManyToOneSaveAndLoad() {
    EntityManager em = entityManagerFactory.createEntityManager();
    em.getTransaction().begin();

    SimpleRelationshipBean srb = new SimpleRelationshipBean();
    UUID uuid = TimeUUIDUtils.getUniqueTimeUUIDinMillis();
    srb.setBaseId(uuid);
    srb.setMyType("my_type");
    srb.addSimpleTestBean(new SimpleTestBean(5L, "my owned bean"));    
    
    em.persist(srb);
    
    em.getTransaction().commit();
    em.close();
    
    EntityManager em2 = entityManagerFactory.createEntityManager();
    SimpleRelationshipBean srb2 = em2.find(SimpleRelationshipBean.class, uuid);
    assertEquals(1,srb.getSimpleTestBeans().size());
    assertEquals("my owned bean", srb.getSimpleTestBeans().iterator().next().getName());
    assertEquals(uuid,srb.getBaseId());
    
  }
}
