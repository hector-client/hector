package me.prettyprint.cassandra.service;

/**
 * Indicates that a client pool has been exhausted.
 *
 * @author Ran Tavory (ran@outbain.com)
 *
 */
public class PoolExhaustedException extends Exception {

  private static final long serialVersionUID = -6200999597951673383L;

  public PoolExhaustedException(String msg) {
    super(msg);
  }
}
