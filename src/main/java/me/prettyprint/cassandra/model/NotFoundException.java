package me.prettyprint.cassandra.model;


/**
 * @author Ran Tavory (rantav@gmail.com)
 */
public class NotFoundException extends HectorException {

  private static final long serialVersionUID = -8772138078816510007L;

  public NotFoundException(String s) {
    super(s);
  }

  public NotFoundException(Throwable t) {
    super(t);
  }
}
