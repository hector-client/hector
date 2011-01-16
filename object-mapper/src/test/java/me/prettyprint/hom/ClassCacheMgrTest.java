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
import me.prettyprint.hom.badbeans.MyMissingIdSetterBean;
import me.prettyprint.hom.beans.MyBlueTestBean;
import me.prettyprint.hom.beans.MyPurpleTestBean;
import me.prettyprint.hom.beans.MyRedTestBean;
import me.prettyprint.hom.beans.MyTestBean;
import me.prettyprint.hom.dupebean.MyDupeCF1;
import me.prettyprint.hom.dupebean.MyDupeCF2;

import org.junit.Ignore;
import org.junit.Test;

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

    CFMappingDef<?, Long> cfMapDef = cacheMgr.getCfMapDef(MyTestBean.class, false);

    assertNotNull(cfMapDef);
    assertEquals(MyTestBean.class, cfMapDef.getClazz());
    assertEquals("did not find @Id properly", "baseId", cfMapDef.getIdPropertyDef().getPropDesc()
                                                                .getName());
    // assertEquals("did not setup properties properly", ColorConverter.class,
    // cfMapDef.getPropMapByColumnName("color").getConverter().getClass());
  }

  @Test
  public void testGetColFamMapDefByClassAnonymousClassOK() {
    ClassCacheMgr cacheMgr = new ClassCacheMgr();
    cacheMgr.initializeCacheForClass(tmplMap.get(1L).getClass());

    assertTrue(tmplMap.get(1L).getClass().isAnonymousClass());

    CFMappingDef<?, Long> cfMapDef = cacheMgr.getCfMapDef(tmplMap.get(1L).getClass(), false);

    assertNotNull(cfMapDef);
    assertEquals(MyTestBean.class, cfMapDef.getClazz());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetColFamMapDefByClassSubclassNotOK() {
    ClassCacheMgr cacheMgr = new ClassCacheMgr();
    cacheMgr.initializeCacheForClass(MyTestBean.class);

    NewBean obj = new NewBean();

    assertFalse(obj.getClass().isAnonymousClass());

    cacheMgr.getCfMapDef(obj.getClass(), true);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetColFamMapDefByClassAnonymousSubclassNotOK() {
    ClassCacheMgr cacheMgr = new ClassCacheMgr();
    cacheMgr.initializeCacheForClass(MyTestBean.class);

    assertTrue(tmplMap.get(2L).getClass().isAnonymousClass());

    cacheMgr.getCfMapDef(tmplMap.get(2L).getClass(), true);
  }

  @Test
  public void testInheritanceOfEntity() {
    ClassCacheMgr cacheMgr = new ClassCacheMgr();
    CFMappingDef<MyRedTestBean, String> cfMapDef = cacheMgr
                                                           .initializeCacheForClass(MyRedTestBean.class);

    // 13 is valid when custom conversion of enumerations works again
    // don't like hard coding numbers into JUnits, but took easy way for now
    assertEquals( 13, cfMapDef.getAllProperties().size() );
    
    assertNotNull(cfMapDef.getCfBaseMapDef());
    assertEquals(MyRedTestBean.class, cfMapDef.getClazz());
    assertEquals("TestBeanColumnFamily", cfMapDef.getColFamName());
    assertEquals("myType", cfMapDef.getDiscColumn());
    assertEquals(DiscriminatorType.STRING, cfMapDef.getDiscType());
    assertEquals("baseId", cfMapDef.getIdPropertyDef().getPropDesc().getName());
  }

  @Test
  public void testInheritanceWithMultiLevels() {
    ClassCacheMgr cacheMgr = new ClassCacheMgr();
    CFMappingDef<Desk, String> cfMapDef = cacheMgr.initializeCacheForClass(Desk.class);
    CFMappingDef<Furniture, String> cfBaseMapDef = cacheMgr.getCfMapDef(Furniture.class, true);

    assertEquals(5, cfMapDef.getAllProperties().size());
    assertNotNull(cfMapDef.getCfSuperMapDef());
    assertNotNull(cfMapDef.getCfBaseMapDef());
    assertEquals(Desk.class.getSuperclass(), cfMapDef.getCfSuperMapDef().getClazz());
    assertEquals(Desk.class.getSuperclass().getSuperclass(), cfMapDef.getCfSuperMapDef()
                                                                     .getCfSuperMapDef().getClazz());
    assertEquals(cfBaseMapDef.getColFamName(), cfMapDef.getColFamName());
    assertEquals("type", cfMapDef.getDiscColumn());
    assertEquals("table_desk", cfMapDef.getDiscValue());
    assertEquals(DiscriminatorType.STRING, cfMapDef.getDiscType());
    assertEquals("id", cfMapDef.getIdPropertyDef().getPropDesc().getName());
  }

  @Test
  public void testInheritanceOfNonEntity() {
    ClassCacheMgr cacheMgr = new ClassCacheMgr();
    CFMappingDef<MyPurpleTestBean, String> cfMapDef = cacheMgr
                                                              .initializeCacheForClass(MyPurpleTestBean.class);

    assertEquals(2, cfMapDef.getAllProperties().size());
    assertNull(cfMapDef.getCfBaseMapDef());
    assertEquals(MyPurpleTestBean.class, cfMapDef.getClazz());
  }

  @Test
  @Ignore("looks as if this isn't finished - investigate")
  public void testInheritanceOfEntityWithNoProperties() {
    ClassCacheMgr cacheMgr = new ClassCacheMgr();
    cacheMgr.initializeCacheForClass(MyBlueTestBean.class);

  }

  @Test(expected = IllegalStateException.class)
  public void testBadPojo() {
    ClassCacheMgr cacheMgr = new ClassCacheMgr();
    cacheMgr.initializeCacheForClass(MyBadTestBean.class);
  }

  @Test(expected = IllegalStateException.class)
  public void testBadIdGetterSetter() {
    ClassCacheMgr cacheMgr = new ClassCacheMgr();
    cacheMgr.initializeCacheForClass(MyMissingIdSetterBean.class);
  }

  @Test(expected = IllegalStateException.class)
  public void testDupeEntityColumnFamilyMapping() {
    ClassCacheMgr cacheMgr = new ClassCacheMgr();
    cacheMgr.initializeCacheForClass(MyDupeCF1.class);
    cacheMgr.initializeCacheForClass(MyDupeCF2.class);
  }
}

// --------------

class NewBean extends MyTestBean {

}
