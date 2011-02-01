package me.prettyprint.hom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.persistence.DiscriminatorType;

import me.prettyprint.hom.badbeans.MyBadTestBean;
import me.prettyprint.hom.badbeans.MyComplexEntityMissingIdField;
import me.prettyprint.hom.badbeans.MyComplexEntityWrongIdField;
import me.prettyprint.hom.badbeans.MyMissingIdAnno;
import me.prettyprint.hom.badbeans.MyMissingIdSetterBean;
import me.prettyprint.hom.beans.MyBlueTestBean;
import me.prettyprint.hom.beans.MyComplexEntity;
import me.prettyprint.hom.beans.MyCompositePK;
import me.prettyprint.hom.beans.MyPurpleTestBean;
import me.prettyprint.hom.beans.MyRedTestBean;
import me.prettyprint.hom.beans.MyTestBean;
import me.prettyprint.hom.beans.MyTestBeanNoAnonymous;
import me.prettyprint.hom.cache.HectorObjectMapperException;
import me.prettyprint.hom.dupebean.MyDupeCF1;
import me.prettyprint.hom.dupebean.MyDupeCF2;

import org.junit.Ignore;
import org.junit.Test;

import com.mycompany.furniture.Chair;
import com.mycompany.furniture.Desk;
import com.mycompany.furniture.Furniture;

public class ClassCacheMgrTest {

  // create an anonymous class .. don't know another way
  @SuppressWarnings("serial")
  Map<Long, MyTestBean> tmplMap = new HashMap<Long, MyTestBean>() {
    {
      put(1L, new MyTestBean() {
        {
          setBaseId(UUID.randomUUID());
          setIntProp1(1);
        }
      });

      put(2L, new NewBean() {
        {
          setBaseId(UUID.randomUUID());
          setIntProp1(1);
        }
      });
    }
  };

  @Test
  public void testGetColFamMapDefByClass() {
    ClassCacheMgr cacheMgr = new ClassCacheMgr();
    cacheMgr.initializeCacheForClass(MyTestBean.class);

    CFMappingDef<?> cfMapDef = cacheMgr.getCfMapDef(MyTestBean.class, false);

    assertNotNull(cfMapDef);
    assertEquals(MyTestBean.class, cfMapDef.getEffectiveClass());
    assertEquals("did not find @Id properly", "baseId", cfMapDef.getKeyDef().getIdPropertyMap().values().iterator()
                                                                .next().getPropDesc().getName());
    assertEquals("did not setup properties properly", ColorConverter.class,
        cfMapDef.getPropMapByColumnName("color").getConverter().getClass());
  }

  @Test
  public void testGetColFamMapDefByClassAnonymousClassOK() {
    ClassCacheMgr cacheMgr = new ClassCacheMgr();
    cacheMgr.initializeCacheForClass(tmplMap.get(1L).getClass());

    assertTrue(tmplMap.get(1L).getClass().isAnonymousClass());

    CFMappingDef<?> cfMapDef = cacheMgr.getCfMapDef(tmplMap.get(1L).getClass(), false);

    assertNotNull(cfMapDef);
    assertEquals(MyTestBean.class, cfMapDef.getEffectiveClass());
  }

  @Test(expected = HectorObjectMapperException.class)
  public void testGetColFamMapDefByClassSubclassNotOK() {
    ClassCacheMgr cacheMgr = new ClassCacheMgr();
    cacheMgr.initializeCacheForClass(MyTestBean.class);

    NewBean obj = new NewBean();

    assertFalse(obj.getClass().isAnonymousClass());

    cacheMgr.getCfMapDef(obj.getClass(), true);
  }

  @Test(expected = HectorObjectMapperException.class)
  public void testGetColFamMapDefByClassAnonymousSubclassNotOK() {
    ClassCacheMgr cacheMgr = new ClassCacheMgr();
    cacheMgr.initializeCacheForClass(MyTestBean.class);

    assertTrue(tmplMap.get(2L).getClass().isAnonymousClass());

    cacheMgr.getCfMapDef(tmplMap.get(2L).getClass(), true);
  }

  @Test
  public void testInheritanceOfEntity() {
    ClassCacheMgr cacheMgr = new ClassCacheMgr();
    CFMappingDef<MyRedTestBean> cfMapDef = cacheMgr.initializeCacheForClass(MyRedTestBean.class);

    // 13 is valid when custom conversion of enumerations works again
    // don't like hard coding numbers into JUnits, but took easy way for now
    assertEquals(14, cfMapDef.getAllProperties().size());

    assertNotNull(cfMapDef.getCfBaseMapDef());
    assertEquals(MyRedTestBean.class, cfMapDef.getEffectiveClass());
    assertEquals("TestBeanColumnFamily", cfMapDef.getEffectiveColFamName());
    assertEquals("myType", cfMapDef.getDiscColumn());
    assertEquals(DiscriminatorType.STRING, cfMapDef.getDiscType());
    assertEquals("baseId", cfMapDef.getKeyDef().getIdPropertyMap().values().iterator().next().getPropDesc().getName());
  }

  @Test
  public void testInheritanceWithMultiLevels() {
    ClassCacheMgr cacheMgr = new ClassCacheMgr();
    CFMappingDef<Desk> cfMapDef = cacheMgr.initializeCacheForClass(Desk.class);
    CFMappingDef<Furniture> cfBaseMapDef = cacheMgr.getCfMapDef(Furniture.class, true);

    assertEquals(5, cfMapDef.getAllProperties().size());
    assertNotNull(cfMapDef.getCfSuperMapDef());
    assertNotNull(cfMapDef.getCfBaseMapDef());
    assertEquals(Desk.class.getSuperclass(), cfMapDef.getCfSuperMapDef().getEffectiveClass());
    assertEquals(Desk.class.getSuperclass().getSuperclass(), cfMapDef.getCfSuperMapDef()
                                                                     .getCfSuperMapDef()
                                                                     .getEffectiveClass());
    assertEquals(cfBaseMapDef.getEffectiveColFamName(), cfMapDef.getEffectiveColFamName());
    assertEquals("type", cfMapDef.getDiscColumn());
    assertEquals("table_desk", cfMapDef.getDiscValue());
    assertEquals(DiscriminatorType.STRING, cfMapDef.getDiscType());
    assertEquals("id", cfMapDef.getKeyDef().getIdPropertyMap().values().iterator().next().getPropDesc().getName());
  }

  @Test
  public void testInheritanceOfNonEntity() {
    ClassCacheMgr cacheMgr = new ClassCacheMgr();
    CFMappingDef<MyPurpleTestBean> cfMapDef = cacheMgr.initializeCacheForClass(MyPurpleTestBean.class);

    assertEquals(2, cfMapDef.getAllProperties().size());
    assertNull(cfMapDef.getCfBaseMapDef());
    assertEquals(MyPurpleTestBean.class, cfMapDef.getEffectiveClass());
  }

  @Test
  @Ignore("looks as if this isn't finished - investigate")
  public void testInheritanceOfEntityWithNoProperties() {
    ClassCacheMgr cacheMgr = new ClassCacheMgr();
    cacheMgr.initializeCacheForClass(MyBlueTestBean.class);

  }

  // property in BadPojo is missing proper setter/getter
  @Test(expected = HectorObjectMapperException.class)
  public void testBadPojo() {
    ClassCacheMgr cacheMgr = new ClassCacheMgr();
    cacheMgr.initializeCacheForClass(MyBadTestBean.class);
  }

  // ID setter/getter not defined properly
  @Test(expected = HectorObjectMapperException.class)
  public void testBadIdGetterSetter() {
    ClassCacheMgr cacheMgr = new ClassCacheMgr();
    cacheMgr.initializeCacheForClass(MyMissingIdSetterBean.class);
  }

  @Test(expected = HectorObjectMapperException.class)
  public void testDupeEntityColumnFamilyMapping() {
    ClassCacheMgr cacheMgr = new ClassCacheMgr();
    cacheMgr.initializeCacheForClass(MyDupeCF1.class);
    cacheMgr.initializeCacheForClass(MyDupeCF2.class);
  }

  @Test
  public void testParsingEntityWithoutAnonymousAddHandler() {
    ClassCacheMgr cacheMgr = new ClassCacheMgr();
    CFMappingDef<MyTestBeanNoAnonymous> cfMapDef = cacheMgr.initializeCacheForClass(MyTestBeanNoAnonymous.class);

    assertFalse("mapping should not indicate there is an anonymous handler",
        cfMapDef.isAnonymousHandlerAvailable());
    assertNotNull("should have set the slice column array", cfMapDef.getSliceColumnNameArr());
    assertEquals(1, cfMapDef.getSliceColumnNameArr().length);
    assertEquals("lp1", cfMapDef.getSliceColumnNameArr()[0]);
  }

  @Test
  public void testParsingInheritedEntityWithoutAnonymousAddHandler() {
    ClassCacheMgr cacheMgr = new ClassCacheMgr();
    CFMappingDef<Chair> cfMapDef = cacheMgr.initializeCacheForClass(Chair.class);

    assertFalse("mapping should not indicate there is an anonymous handler",
        cfMapDef.isAnonymousHandlerAvailable());
    assertNotNull("should have set the slice column array", cfMapDef.getSliceColumnNameArr());
    assertEquals(5, cfMapDef.getSliceColumnNameArr().length);
    assertEquals("type", cfMapDef.getSliceColumnNameArr()[4]);
  }

  @Test
  public void testParsingComplexEntity() {
    ClassCacheMgr cacheMgr = new ClassCacheMgr();
    CFMappingDef<MyComplexEntity> cfMapDef = cacheMgr.initializeCacheForClass(MyComplexEntity.class);
    
    KeyDefinition keyDef = cfMapDef.getKeyDef();
    assertEquals( MyCompositePK.class, keyDef.getPkClazz() );
    assertEquals( 2, keyDef.getIdPropertyMap().size() );
    assertEquals( keyDef.getIdPropertyMap().size(), keyDef.getPropertyDescriptorMap().size() );
    
  }

  @Ignore( "Cannot enable until method annotations are supported by ClassCacheMgr")
  @Test(expected=HectorObjectMapperException.class)
  public void testMissingIdAnno() {
    ClassCacheMgr cacheMgr = new ClassCacheMgr();
    cacheMgr.initializeCacheForClass(MyMissingIdAnno.class);
  }

  @Test(expected=HectorObjectMapperException.class)
  public void testParsingComplexIdFieldMissing() {
    ClassCacheMgr cacheMgr = new ClassCacheMgr();
    cacheMgr.initializeCacheForClass(MyComplexEntityMissingIdField.class);
  }

  @Test(expected=HectorObjectMapperException.class)
  public void testParsingComplexIdFieldWrong() {
    ClassCacheMgr cacheMgr = new ClassCacheMgr();
    cacheMgr.initializeCacheForClass(MyComplexEntityWrongIdField.class);
  }

}

// --------------

class NewBean extends MyTestBean {

}
