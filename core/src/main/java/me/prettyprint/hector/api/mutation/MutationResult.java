package me.prettyprint.hector.api.mutation;

import me.prettyprint.hector.api.ResultStatus;

/**
 * Result from a mutation. Execution time and host used
 * have moved up to {@link ResultStatus}. Consider this a
 * marker interface only.
 *
 * @author Ran Tavory
 * @author zznate
 */
public interface MutationResult extends ResultStatus {

  
}