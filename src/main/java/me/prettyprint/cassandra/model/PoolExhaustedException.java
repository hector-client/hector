package me.prettyprint.cassandra.model;

/**
 * Indicates that a client pool has been exhausted.
 *
 * @author Ran Tavory (ran@outbain.com)
 *
 */
public class PoolExhaustedException extends HectorException {

  private static final long serialVersionUID = -6200999597951673383L;

  public PoolExhaustedException(String msg) {
    super(msg);
  }

  public PoolExhaustedException(Throwable t) {
    super(t);
  }
}
