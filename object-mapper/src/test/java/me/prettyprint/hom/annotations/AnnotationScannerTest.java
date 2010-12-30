package me.prettyprint.hom.annotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import me.prettyprint.hom.annotations.AnnotationScanner;
import me.prettyprint.hom.beans.MyBlueTestBean;
import me.prettyprint.hom.beans.MyCustomIdBean;
import me.prettyprint.hom.beans.MyPurpleTestBean;
import me.prettyprint.hom.beans.MyRedTestBean;
import me.prettyprint.hom.beans.MyTestBean;

import org.junit.Test;


public class AnnotationScannerTest {

  @Test
  public void testScanForAnnotation() {
    AnnotationScanner scanner = new AnnotationScanner();
    Set<Class<?>> classSet = scanner.scan("me.prettyprint.hom.beans", javax.persistence.Entity.class);

    int count = 0;
    assertTrue(classSet.contains(MyTestBean.class));
    count++;
    assertTrue(classSet.contains(MyBlueTestBean.class));
    count++;
    assertTrue(classSet.contains(MyRedTestBean.class));
    count++;
    assertTrue(classSet.contains(MyPurpleTestBean.class));
    count++;
    assertTrue(classSet.contains(MyCustomIdBean.class));
    count++;

    assertEquals(count, classSet.size());
  }

}
