package me.prettyprint.cassandra.utils;

/**
 * A generic low weight assert utility, very similar with Spring's Assert class,
 * just without the dependency on Spring
 * 
 * See for example
 * http://www.jarvana.com/jarvana/view/org/springframework/spring
 * /1.2.9/spring-1.2.9-javadoc.jar!/org/springframework/util/Assert.html
 * 
 * @author Ran Tavory
 * 
 */
public class Assert {

  public static void notNull(Object object, String message) {
    if (object == null) {
      throw new IllegalArgumentException(message);
    }
  }
}
