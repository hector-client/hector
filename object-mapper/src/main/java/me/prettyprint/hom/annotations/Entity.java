package me.prettyprint.hom.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import me.prettyprint.hom.HectorObjectMapper;


/**
 * Annotation for marking a POJO that can be persisted via {@link HectorObjectMapper}.
 * 
 * @author Todd Burruss
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Entity {

}
