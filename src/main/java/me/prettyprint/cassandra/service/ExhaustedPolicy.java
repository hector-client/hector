/**
 * 
 */
package me.prettyprint.cassandra.service;

public enum ExhaustedPolicy {
  WHEN_EXHAUSTED_FAIL, WHEN_EXHAUSTED_GROW, WHEN_EXHAUSTED_BLOCK
}