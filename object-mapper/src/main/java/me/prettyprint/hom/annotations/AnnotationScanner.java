package me.prettyprint.hom.annotations;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * Scan for classes annotated with an annotation. The scan starts in the given
 * package root so it doesn't need to scan through the entire package structure.
 *
 * @author Todd Burruss
 */
public interface AnnotationScanner {
  Set<Class<?>> scan(String packageRoot, Class<? extends Annotation> anno);
}
