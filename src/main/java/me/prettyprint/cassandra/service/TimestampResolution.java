package me.prettyprint.cassandra.service;

/**
 * Resolution used to create timestamps.
 * Clients may wish to use millisec, micro or sec, depending on the application
 * needs and existing data and other tools, so Hector makes that
 * configurable.
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
public enum TimestampResolution {
  SECONDS, MILLISECONDS, MICROSECONDS
}
