package me.prettyprint.cassandra.serializers;

import java.util.Date;

/**
 * Converts bytes to Date and vice versa, by first converting the Date to or
 * from a long which represents the specified number of milliseconds since
 * the standard base time known as "the Unix epoch", that is
 * January 1, 1970, 00:00:00 UTC.
 *
 * @author Jim Ancona
 * @see java.util.Date
 */
public final class DateSerializer extends AbstractSerializer<Date> {
  private static final LongSerializer LONG_SERIALIZER = LongSerializer.get();
  private static final DateSerializer instance = new DateSerializer();

  public static DateSerializer get() {
    return instance;
  }

  @Override
  public byte[] toBytes(Date obj) {
    if (obj == null) {
      return null;
    }
    return LONG_SERIALIZER.toBytes(obj.getTime());
  }

  @Override
  public Date fromBytes(byte[] bytes) {
    if (bytes == null) {
      return null;
    }
    return new Date(LONG_SERIALIZER.fromBytes(bytes));
  }
}
