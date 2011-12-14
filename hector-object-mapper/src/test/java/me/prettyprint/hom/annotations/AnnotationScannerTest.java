package me.prettyprint.hom.annotations;

import java.util.Set;

import me.prettyprint.hom.beans.AnonymousWithCustomType;
import me.prettyprint.hom.beans.MyBlueTestBean;
import me.prettyprint.hom.beans.MyComplexEntity;
import me.prettyprint.hom.beans.MyConvertedCollectionBean;
import me.prettyprint.hom.beans.MyCustomIdBean;
import me.prettyprint.hom.beans.MyGreenTestBean;
import me.prettyprint.hom.beans.MyPurpleTestBean;
import me.prettyprint.hom.beans.MyRedTestBean;
import me.prettyprint.hom.beans.MyTestBean;
import me.prettyprint.hom.beans.MyTestBeanNoAnonymous;
import me.prettyprint.hom.beans.SimpleRelationshipBean;
import me.prettyprint.hom.beans.SimpleTestBean;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class AnnotationScannerTest {

  @Test
  public void testScanForAnnotation() {
    AnnotationScanner scanner = new AnnotationScanner();
    Set<Class<?>> classSet = scanner.scan("me.prettyprint.hom.beans", javax.persistence.Entity.class);

    int count = 0;
    assertTrue(classSet.contains(MyTestBean.class));
    count++;
    assertTrue(classSet.contains(MyTestBeanNoAnonymous.class));
    count++;
    assertTrue(classSet.contains(MyBlueTestBean.class));
    count++;
    assertTrue(classSet.contains(MyRedTestBean.class));
    count++;
    assertTrue(classSet.contains(MyPurpleTestBean.class));
    count++;
    assertTrue(classSet.contains(MyGreenTestBean.class));
    count++;
    assertTrue(classSet.contains(MyCustomIdBean.class));
    count++;
    assertTrue(classSet.contains(SimpleTestBean.class));
    count++;
    assertTrue(classSet.contains(MyComplexEntity.class));
    count++;
    assertTrue(classSet.contains(SimpleRelationshipBean.class));
    count++;
    assertTrue(classSet.contains(MyConvertedCollectionBean.class));
    count++;
    assertTrue(classSet.contains(AnonymousWithCustomType.class));
    count++;

    assertEquals(count, classSet.size());
  }

}
