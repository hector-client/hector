package me.prettyprint.hom.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import me.prettyprint.hom.converters.Converter;
import me.prettyprint.hom.converters.DefaultConverter;


/**
 * Annotation marking the ID property in the POJO. Marking the ID property is
 * required so the object mapper can get/set the ID (Cassandra row key) in the
 * POJO.
 * 
 * @author Todd Burruss
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Id {

    /**
     * The optional converter to use when converting POJO property value to/from
     * byte[]. If not specified, {@link DefaultConverter} is used.
     * 
     * @return
     */
    Class<? extends Converter<?>> converter() default DefaultConverter.class;

}
