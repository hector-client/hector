package me.prettyprint.hom.service;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import me.prettyprint.hom.CassandraTestBase;
import me.prettyprint.hom.beans.SimpleRelationshipBean;
import me.prettyprint.hom.openjpa.EntityFacade;
import me.prettyprint.hom.service.EntitySchemaStatus.SchemaResult;

import org.apache.openjpa.persistence.JPAFacadeHelper;
import org.junit.BeforeClass;
import org.junit.Test;

public class EntitySchemaManagerTest extends CassandraTestBase {

  private static EntitySchemaManager entitySchemaManager;
  private static EntityManagerFactory entityManagerFactory;
  private static EntityFacade entityFacade;
  
  @BeforeClass
  public static void setup() {
    entitySchemaManager = new EntitySchemaManager(cluster, keyspace);
    entityManagerFactory = Persistence.createEntityManagerFactory("openjpa");    
  }
  
  @Test
  public void testCreateSchemaFromEntity() {
    entityFacade = new EntityFacade(JPAFacadeHelper.getMetaData(entityManagerFactory, SimpleRelationshipBean.class));
    EntitySchemaStatus ecs = entitySchemaManager.createSchema(entityFacade);
    assertEquals(SchemaResult.CREATED, ecs.getSchemaResult());    
  }
  
  @Test
  public void testCreateSchemaFailExisting() {
    entityFacade = new EntityFacade(JPAFacadeHelper.getMetaData(entityManagerFactory, SimpleRelationshipBean.class));
    EntitySchemaStatus ecs = entitySchemaManager.createSchema(entityFacade);
    assertEquals(SchemaResult.NOT_MODIFIED, ecs.getSchemaResult());    
  }
}
