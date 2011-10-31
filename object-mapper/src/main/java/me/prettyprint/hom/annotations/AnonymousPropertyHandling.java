package me.prettyprint.hom.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import me.prettyprint.cassandra.serializers.BytesArraySerializer;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hom.HectorObjectMapper;


/**
 * Annotation for marking the method used to add "anonymous" properties to the
 * POJO. Anonymous properties are Columns in Cassandra that do not map directly
 * to the POJO.  See {@link HectorObjectMapper} for details.
 * 
 * @author Todd Burruss
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AnonymousPropertyHandling {

  /**
   * The type of the anonymous property values.  Defaults to byte[].
   * @return
   */
  Class<?> type() default byte[].class;
  
  /**
   * The optional {@link Serializer} to use when converting anonymous property value to/from
   * byte[]. If not specified, {@link BytesArraySerializer} is used.
   * 
   * @return serializer type
   */
  @SuppressWarnings("rawtypes")
  Class<? extends Serializer> serializer() default me.prettyprint.cassandra.serializers.BytesArraySerializer.class;

  String adder();
  
  String getter();
}
