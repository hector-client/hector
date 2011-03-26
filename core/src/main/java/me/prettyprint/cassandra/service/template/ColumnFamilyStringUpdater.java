package me.prettyprint.cassandra.service.template;

import me.prettyprint.hector.api.ColumnFactory;

/**
 * A simple specialization of the generic class for the very common all-string
 * column name scenario.
 * 
 * @author david
 * @since Mar 10, 2011
 */
public abstract class ColumnFamilyStringUpdater extends ColumnFamilyUpdater<String, String> {

  public ColumnFamilyStringUpdater(
      ColumnFamilyTemplate<String, String> template, ColumnFactory columnFactory) {
    super(template, columnFactory);   
  }
}
