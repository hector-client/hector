package me.prettyprint.hom.cache;

import java.lang.annotation.Annotation;

import javax.persistence.IdClass;

import me.prettyprint.hom.CFMappingDef;
import me.prettyprint.hom.ClassCacheMgr;
import me.prettyprint.hom.beans.MyCompositePK;
import me.prettyprint.hom.beans.MyTestBean;

import org.junit.Test;

public class IdClassParserValidatorTest {

  @Test
  public void testNotSerializable() {
    ClassCacheMgr cacheMgr = new ClassCacheMgr();
    IdClassParserValidator parVal = new IdClassParserValidator();

    CFMappingDef<MyTestBean> cfMapDef = new CFMappingDef<MyTestBean>(MyTestBean.class);
    
    IdClass anno = new IdClass() {
      @Override
      public Class<? extends Annotation> annotationType() {
        return IdClass.class;
      }
      
      @SuppressWarnings("rawtypes")
      @Override
      public Class value() {
        return MyCompositePK.class;
      }
    };
    
    parVal.parse(cacheMgr, anno, cfMapDef);
  }
}
