package me.prettyprint.hom;

import me.prettyprint.hom.cache.HectorObjectMapperException;

@SuppressWarnings("serial")
public class HomMissingEntityAnnotationException extends HectorObjectMapperException {

  public HomMissingEntityAnnotationException(String msg) {
    super(msg);
  }

}
