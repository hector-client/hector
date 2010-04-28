package me.prettyprint.cassandra.service;

/**
 * Resolution used to create timestamps.
 * Clients may with to use millisec, micro or sec, depending on the application
 * needs and existing data and other tools, so Hector wants to make that
 * configurable.
 * @author Ran
 *
 */
public enum TimestampResolution {
  SECONDS, MILLISECONDS, MICROSECONDS
}
