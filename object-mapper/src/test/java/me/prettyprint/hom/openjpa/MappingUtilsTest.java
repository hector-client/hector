package me.prettyprint.hom.openjpa;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import me.prettyprint.cassandra.model.thrift.ThriftSliceQuery;
import me.prettyprint.hector.api.query.SliceQuery;
import me.prettyprint.hom.beans.SimpleTestBean;
import me.prettyprint.hom.openjpa.MappingUtils;

import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.persistence.JPAFacadeHelper;
import org.junit.Test;

public class MappingUtilsTest {

  
  private MappingUtils mappingUtils;
  
  
  @Test
  public void testConvertClassToSlice() {
    mappingUtils = new MappingUtils();
    
    
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("openjpa");
    
    ClassMetaData classMetaData = null;
    List<String> cols = mappingUtils.buildColumnMap(JPAFacadeHelper.getMetaData(entityManagerFactory, SimpleTestBean.class));
    assertEquals("name",cols.get(0));
    
    
  }
}
