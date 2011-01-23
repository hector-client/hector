package me.prettyprint.hom.openjpa;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import me.prettyprint.cassandra.model.ExecutingKeyspace;
import me.prettyprint.cassandra.model.thrift.ThriftSliceQuery;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.SliceQuery;
import me.prettyprint.hom.beans.SimpleTestBean;
import me.prettyprint.hom.openjpa.MappingUtils;

import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.persistence.JPAFacadeHelper;
import org.apache.openjpa.util.LongId;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class MappingUtilsTest {
  
  private MappingUtils mappingUtils;
  private static EntityManagerFactory entityManagerFactory;
  private Keyspace keyspace = Mockito.mock(ExecutingKeyspace.class);
    
  @Test
  public void testConvertClassToSlice() {
    mappingUtils = new MappingUtils();            
    
    List<String> cols = mappingUtils.buildColumnList(JPAFacadeHelper.getMetaData(entityManagerFactory, SimpleTestBean.class));
    
    assertEquals("name",cols.get(0));        
  }
  
  @Test
  public void testGetSerializer() {
    mappingUtils = new MappingUtils();
    
    Serializer<Long> serializer = mappingUtils.getSerializer(new LongId(SimpleTestBean.class, 1L));
    
    assertTrue(serializer instanceof LongSerializer);
  }
  
  @Test
  public void testBuildSliceQuery() {
    mappingUtils = new MappingUtils();
    LongId id = new LongId(SimpleTestBean.class, 1L);
    EntityFacade entityFacade = new EntityFacade(JPAFacadeHelper.getMetaData(entityManagerFactory, SimpleTestBean.class));
    SliceQuery sliceQuery = mappingUtils.buildSliceQuery(id, entityFacade, keyspace);
    assertTrue(((ThriftSliceQuery)sliceQuery).getColumnNames().contains("name"));
    assertEquals(1,((ThriftSliceQuery)sliceQuery).getColumnNames().size());
  }
  
  @Test
  public void testBuildMutation() {
    mappingUtils = new MappingUtils();
    LongId id = new LongId(SimpleTestBean.class, 1L);
    Broker broker = JPAFacadeHelper.toBroker(entityManagerFactory.createEntityManager());
    //Mutator mutator = mappingUtils.buildMutator(id, broker., keyspace);
  }
  
  @BeforeClass
  public static void setup() {
    entityManagerFactory = Persistence.createEntityManagerFactory("openjpa");
  }
}
