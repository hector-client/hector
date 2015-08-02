/**
 * 
 */
package me.prettyprint.cassandra.serializers;

import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.Composite;
import me.prettyprint.hector.api.ddl.ComparatorType;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

import static me.prettyprint.hector.api.ddl.ComparatorType.COMPOSITETYPE;

/**
 * @author Todd Nine
 * 
 */
public class CompositeSerializer extends AbstractSerializer<Composite> {

  private static final CompositeSerializer INSTANCE = new CompositeSerializer();

  private final List<Serializer<?>> serializersByPosition;

  public static CompositeSerializer get() {
    return INSTANCE;
  }

  public CompositeSerializer(Serializer<?>... serializers) {
    serializersByPosition = Arrays.asList(serializers);
  }

  @Override
  public ByteBuffer toByteBuffer(Composite obj) {

    return obj.serialize();
  }

  @Override
  public Composite fromByteBuffer(ByteBuffer byteBuffer) {

    Composite composite = createComposite();
    composite.deserialize(byteBuffer);

    return composite;

  }

  @Override
  public ComparatorType getComparatorType() {
    return COMPOSITETYPE;
  }

  private Composite createComposite() {
    Composite composite = new Composite();
    composite.setSerializersByPosition(serializersByPosition);
    return composite;
  }
}
