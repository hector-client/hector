package me.prettyprint.hom.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for specifying the name of the Cassandra ColumnFamily the POJO should map to.
 * 
 * @author Todd Burruss
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Inheritance {

    /**
     * The strategy for inheritance.
     * 
     * @see InheritanceType
     * @return
     */
    InheritanceType strategy() default InheritanceType.SINGLE_TABLE;

}
