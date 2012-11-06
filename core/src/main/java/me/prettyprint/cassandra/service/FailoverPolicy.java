package me.prettyprint.cassandra.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

import java.util.Set;

/**
 * What should the client do if a call to cassandra node fails and we suspect that the node is
 * down. (e.g. it's a communication error, not an application error).
 *
 * {@value #FAIL_FAST} will return the error as is to the user and not try anything smart
 *
 * {@value #ON_FAIL_TRY_ONE_NEXT_AVAILABLE} will try one more random server before returning to the
 * user with an error
 *
 * {@value #ON_FAIL_TRY_ALL_AVAILABLE} will try all available servers in the cluster before giving
 * up and returning the communication error to the user.
 *
 */
public class FailoverPolicy<E extends Throwable> {

  /** On communication failure, just return the error to the client and don't retry */
  public static FailoverPolicy FAIL_FAST = new FailoverPolicy(0, 0);
  /** On communication error try one more server before giving up */
  public static FailoverPolicy ON_FAIL_TRY_ONE_NEXT_AVAILABLE = new FailoverPolicy(1, 0);
  /** On communication error try all known servers before giving up */
  public static FailoverPolicy ON_FAIL_TRY_ALL_AVAILABLE = new FailoverPolicy(Integer.MAX_VALUE - 1, 0);

  public final int numRetries;

  public final int sleepBetweenHostsMilli;

  /** Optional set of classes representing Exceptions/Errors for which retry should not happen. */
  public final Set<Class<E>> dontRetry;

  public FailoverPolicy(int numRetries, int sleepBwHostsMilli) {
    this.numRetries = numRetries;
    sleepBetweenHostsMilli = sleepBwHostsMilli;
    dontRetry = ImmutableSet.of();
  }

  public FailoverPolicy(int numRetries, int sleepBwHostsMilli, Class<E> dontRetryForType) {
    this.numRetries = numRetries;
    sleepBetweenHostsMilli = sleepBwHostsMilli;
    dontRetry = ImmutableSet.of(dontRetryForType);
  }

  public FailoverPolicy(int numRetries, int sleepBwHostsMilli, Set<Class<E>> dontRetryForTypes) {
    this.numRetries = numRetries;
    sleepBetweenHostsMilli = sleepBwHostsMilli;
    this.dontRetry = dontRetryForTypes;
  }

  /**
   * Determines if a given class is an exception or error that this FailoverPolicy supports retry for.
   * This is a supplement to the explicit behavior defined in {@link me.prettyprint.cassandra.connection.HConnectionManager}.
   */
  public boolean shouldRetryFor(Class<E> candidate) {
    Preconditions.checkNotNull(candidate, "Requires a non-null candidate class");
    if (dontRetry != null) {
      return !dontRetry.contains(candidate);
    } else {
      return true;
    }
  }
}
