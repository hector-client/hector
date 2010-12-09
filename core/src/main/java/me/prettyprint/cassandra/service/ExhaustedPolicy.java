package me.prettyprint.cassandra.service;

import me.prettyprint.hector.api.exceptions.PoolExhaustedException;

/**
 * Policy what to do when the connection pool is exhausted.
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
public enum ExhaustedPolicy {
  /**
   * If the pool is full, fail with the exception {@link PoolExhaustedException}
   */
  WHEN_EXHAUSTED_FAIL, 
  /**
   * When pool exhausted, grow.
   */
  WHEN_EXHAUSTED_GROW, 
  /**
   * Block the requesting thread when the pool is exhausted until new connections are available.
   */
  WHEN_EXHAUSTED_BLOCK
}