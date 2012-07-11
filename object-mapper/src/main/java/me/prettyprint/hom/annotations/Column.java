package me.prettyprint.hom.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import me.prettyprint.hom.converters.Converter;


/**
 * Annotation for specifying which POJO properties should be mapped to Cassandra
 * columns. Must specify "name" as the column name in Cassandra.
 * 
 * @author
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {

  /**
   * The Cassandra column name.
   * 
   * @return name of column
   */
  String name();

  /**
   * The optional converter to use when converting POJO property value to/from
   * byte[]. If not specified, {@link me.prettyprint.hom.converters.DefaultConverter} is used.
   * 
   * @return Class of converter
   */
  @SuppressWarnings("rawtypes")
  Class<? extends Converter> converter() default me.prettyprint.hom.converters.DefaultConverter.class;

}
