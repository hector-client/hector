package me.prettyprint.hector.api.exceptions;



/**
 * Hector transport exception (thrift or avro).
 *
 * @author Ran Tavory (ran@outbrain.com)
 *
 */
public final class HectorTransportException extends HectorException {

  private static final long serialVersionUID = -8687856384223311785L;

  public HectorTransportException(String s) {
    super(s);
  }

  public HectorTransportException(String s, Throwable t) {
    super(s, t);
  }

  public HectorTransportException(Throwable t) {
    super(t);
  }

}
