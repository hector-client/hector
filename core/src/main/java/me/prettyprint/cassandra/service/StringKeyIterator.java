package me.prettyprint.cassandra.service;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Keyspace;

/**
 * This class returns each key in the specified Column Family as an Iterator.  You 
 * can use this class in a for loop without the overhead of first storing each
 * key in a large array.  This is a convenience class for KeyIterator when the key
 * is a String.
 * @author Tim Koop
 * @see KeyIterator
 */
public class StringKeyIterator extends KeyIterator<String> {

  @Deprecated
  public StringKeyIterator(Keyspace keyspace, String columnFamily) {
    super(keyspace, columnFamily, new StringSerializer());
  }

  public static class Builder extends KeyIterator.Builder<String> {

    public Builder(Keyspace keyspace, String columnFamily) {
      super(keyspace, columnFamily, new StringSerializer());
    }

    @Override
    public Builder start(String start) {
      super.start(start);
      return this;
    }

    @Override
    public Builder end(String end) {
      super.end(end);
      return this;
    }

    @Override
    public Builder maxRowCount(int maxRowCount) {
      super.maxRowCount(maxRowCount);
      return this;
    }

    @Override
    public StringKeyIterator build() {
      return new StringKeyIterator(this);
    }

  }

  private StringKeyIterator(Builder builder) {
    super(builder);
  }
}
