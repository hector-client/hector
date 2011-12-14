package me.prettyprint.hom.cache;

import me.prettyprint.hector.api.exceptions.HectorException;

@SuppressWarnings("serial")
public class HectorObjectMapperException extends HectorException {

  public HectorObjectMapperException(String msg) {
    super(msg);
  }

  public HectorObjectMapperException(String s, Throwable t) {
    super(s, t);
  }

  public HectorObjectMapperException(Throwable t) {
    super(t);
  }

}
