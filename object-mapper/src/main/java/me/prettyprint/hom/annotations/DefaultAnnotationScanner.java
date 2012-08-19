package me.prettyprint.hom.annotations;

import org.apache.xbean.finder.AnnotationFinder;
import org.apache.xbean.finder.UrlSet;
import org.apache.xbean.finder.archive.ClasspathArchive;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Scan for classes annotated with an annotation. The scan starts in the given
 * package root so it doesn't need to scan through the entire package structure.
 *
 * @author Todd Burruss
 */
public class DefaultAnnotationScanner implements AnnotationScanner {
  @Override
  public Set<Class<?>> scan(String packageRoot, Class<? extends Annotation> anno) {
    final ClassLoader cl = Thread.currentThread().getContextClassLoader();
    UrlSet set;
    try {
      set = new UrlSet(cl);
      if (cl.getParent() != cl) {
        set = set.exclude(cl.getParent());
      }
      set = set.excludeJavaExtDirs();
      set = set.excludeJavaHome();
      set = set.excludeJavaEndorsedDirs();
      // exclude some well known libs to go faster in real apps
      set = set.exclude(".*/activation(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/activeio-core(-[\\d.]+)?(-incubator)?.jar(!/)?");
      set = set.exclude(".*/activemq-(core|ra)(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/annotations-api-6.[01].[\\d.]+.jar(!/)?");
      set = set.exclude(".*/asm-(all|commons|util|tree)?[\\d.]+.jar(!/)?");
      set = set.exclude(".*/avalon-framework(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/axis2-jaxws-api(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/backport-util-concurrent(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/bcprov-jdk15(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/catalina(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/cglib-(nodep-)?[\\d.]+.jar(!/)?");
      set = set.exclude(".*/com\\.ibm\\.ws\\.[^/]*.jar(!/)?");
      set = set.exclude(".*/commons-(logging|logging-api|cli|pool|lang|collections|dbcp|dbcp-all)(-[\\d.r-]+)?.jar(!/)?");
      set = set.exclude(".*/cxf-bundle(-[\\d.]+)?(incubator)?.jar(!/)?");
      set = set.exclude(".*/openejb-cxf-bundle(-[\\d.]+)?(incubator)?.jar(!/)?");
      set = set.exclude(".*/derby(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/ejb31-api-experimental(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/geronimo-(connector|transaction)(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/geronimo-[^/]+_spec(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/geronimo-javamail_([\\d.]+)_mail(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/hibernate-(entitymanager|annotations)?(-[\\d.]+(ga)?)?.jar(!/)?");
      set = set.exclude(".*/howl(-[\\d.-]+)?.jar(!/)?");
      set = set.exclude(".*/hsqldb(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/idb(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/idea_rt.jar(!/)?");
      set = set.exclude(".*/javaee-api(-embedded)?-[\\d.-]+.jar(!/)?");
      set = set.exclude(".*/javassist[^/]*.jar(!/)?");
      set = set.exclude(".*/jaxb-(impl|api)(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/jboss-[^/]*.jar(!/)?");
      set = set.exclude(".*/jbossall-[^/]*.jar(!/)?");
      set = set.exclude(".*/jbosscx-[^/]*.jar(!/)?");
      set = set.exclude(".*/jbossjts-?[^/]*.jar(!/)?");
      set = set.exclude(".*/jbosssx-[^/]*.jar(!/)?");
      set = set.exclude(".*/jmdns(-[\\d.]+)?(-RC\\d)?.jar(!/)?");
      set = set.exclude(".*/juli(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/junit(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/log4j(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/logkit(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/mail(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/neethi(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/org\\.eclipse\\.persistence\\.[^/]*.jar(!/)?");
      set = set.exclude(".*/org\\.junit_.[^/]*.jar(!/)?");
      set = set.exclude(".*/openjpa-(jdbc|kernel|lib|persistence|persistence-jdbc)(-5)?(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/openjpa(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/opensaml(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/quartz(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/saaj-impl(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/spring(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/serp(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/servlet-api(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/slf4j-api(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/slf4j-jdk14(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/stax-api(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/swizzle-stream(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/sxc-(jaxb|runtime)(-[\\d.]+)?(-SNAPSHOT)?.jar(!/)?");
      set = set.exclude(".*/wsdl4j(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/wss4j(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/wstx-asl(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/xbean-(reflect|naming|finder)-(shaded-)?[\\d.]+.jar(!/)?");
      set = set.exclude(".*/xmlParserAPIs(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/xmlunit(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/xmlsec(-[\\d.]+)?.jar(!/)?");
      set = set.exclude(".*/XmlSchema(-[\\d.]+)?.jar(!/)?");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    final ClasspathArchive archive = new ClasspathArchive(cl, set.getUrls());
    final AnnotationFinder scanner = new AnnotationFinder(archive);

    final List<Class<?>> list = scanner.findAnnotatedClasses(anno);
    final Set<Class<?>> result = new HashSet<Class<?>>();
    for (Class<?> clazz : list) {
      if (clazz.getPackage().getName().startsWith(packageRoot) && !Modifier.isAbstract(clazz.getModifiers())) {
        result.add(clazz);
      }
    }
    return result;
  }
}
