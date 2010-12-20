package me.prettyprint.hom.annotations;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

/**
 * Scan for classes annotated with an annotation. The scan starts in the given
 * package root so it doesn't need to scan through the entire package structure.
 * 
 * @author Todd Burruss
 */
public class AnnotationScanner {
  private static Logger logger = LoggerFactory.getLogger(AnnotationScanner.class);

  public Set<Class<?>> scan(String packageRoot, Class<? extends Annotation> anno) {
    ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);

    AnnotationTypeFilter filter = new AnnotationTypeFilter(anno);
    scanner.addIncludeFilter(filter);
    Set<BeanDefinition> beanSet = scanner.findCandidateComponents(packageRoot);

    Set<Class<?>> classSet = new HashSet<Class<?>>();
    for (BeanDefinition beanDef : beanSet) {
      logger.debug("found candidate bean = " + beanDef.getBeanClassName());

      Class<?> clazz;
      try {
        clazz = Class.forName(beanDef.getBeanClassName());
        if (clazz.isAnnotationPresent(anno)) {
          logger.debug( "found annotated class, " + clazz.getName());
          classSet.add(clazz);
        }
      }
      catch (ClassNotFoundException e) {
        logger.error("exception while scanning classpath for annotated classes", e);
      }
    }

    return classSet;
  }
}
