package me.prettyprint.hom.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import me.prettyprint.hom.HectorObjectMapper;



/**
 * Annotation for marking the method used to get "anonymous" properties from the
 * POJO. Anonymous properties are Columns in Cassandra that do not map directly
 * to the POJO.  See {@link HectorObjectMapper} for details.
 * 
 * @author Todd Burruss
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AnonymousPropertyCollectionGetter {

}
