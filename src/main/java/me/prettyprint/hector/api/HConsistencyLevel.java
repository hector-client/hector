package me.prettyprint.hector.api;

/**
 * This is the Hector consistency level which is just a mirror of the thrift or avro consistency
 * levels.
 * @author: peter
 */
public enum HConsistencyLevel {
    ONE, QUORUM, ALL, ANY, EACH_QUORUM, LOCAL_QUORUM;
}
