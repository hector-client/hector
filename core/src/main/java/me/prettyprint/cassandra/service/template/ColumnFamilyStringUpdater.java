package me.prettyprint.cassandra.service.template;

/**
 * A simple specialization of the generic class for the very common all-string
 * column name scenario.
 * 
 * @author david
 * @since Mar 10, 2011
 */
public abstract class ColumnFamilyStringUpdater extends ColumnFamilyUpdater<String, String> {
}
