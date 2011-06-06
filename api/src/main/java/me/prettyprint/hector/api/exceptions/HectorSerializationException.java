package me.prettyprint.hector.api.exceptions;

import me.prettyprint.cassandra.serializers.ObjectSerializer;
import me.prettyprint.hector.api.Serializer;

/**
 * Describes a serialization exception.
 * Serialization exceptions may happen when {@link Serializer#fromBytes(byte[])} encounters a value
 * it's unable to interpret to it's target object. For example an {@link ObjectSerializer} may be
 * unable to deserialize a java object back to its class if the object it of the wrong format
 * or the target class is not on the classpath.
 * @author Ran Tavory
 *
 */
public class HectorSerializationException extends HectorException {

  private static final long serialVersionUID = -8704123219107599786L;

  public HectorSerializationException(String msg) {
    super(msg);
  }

  public HectorSerializationException(Throwable t) {
    super(t);
  }

  public HectorSerializationException(String msg, Throwable t) {
    super(msg, t);
  }


}
