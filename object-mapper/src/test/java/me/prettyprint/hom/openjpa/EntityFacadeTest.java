package me.prettyprint.hom.openjpa;

import static org.junit.Assert.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.hector.api.query.SliceQuery;
import me.prettyprint.hom.beans.SimpleRelationshipBean;
import me.prettyprint.hom.beans.SimpleTestBean;

import org.apache.openjpa.persistence.JPAFacadeHelper;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class EntityFacadeTest {

  private static EntityManagerFactory entityManagerFactory;
  
  @Test
  public void testEntityFacadeValues() {
    EntityFacade entityFacade = new EntityFacade(JPAFacadeHelper.getMetaData(entityManagerFactory, SimpleTestBean.class));
    assertEquals("SimpleTestBeanColumnFamily", entityFacade.getColumnFamilyName());
    assertEquals(LongSerializer.get(), entityFacade.getKeySerializer()); 
    assertEquals(StringSerializer.get(), entityFacade.getSerializer("name"));
  }
  
  @Test
  public void testOneToManyFacadeValues() {
    EntityFacade entityFacade = new EntityFacade(JPAFacadeHelper.getMetaData(entityManagerFactory, SimpleRelationshipBean.class));
    assertEquals(UUIDSerializer.get(), entityFacade.getKeySerializer());
  }
  
  @Ignore  
  public void testBuildSliceQuery() {
    EntityFacade entityFacade = new EntityFacade(JPAFacadeHelper.getMetaData(entityManagerFactory, SimpleTestBean.class));
    // ColumnFamily wrapper would work really well here
  }
  
  @BeforeClass
  public static void setup() {
    entityManagerFactory = Persistence.createEntityManagerFactory("openjpa");
  }

}
