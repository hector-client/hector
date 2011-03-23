/**
 * 
 */
package me.prettyprint.cassandra.serializers;

import java.nio.ByteBuffer;

import me.prettyprint.hector.api.beans.Composite;

/**
 * @author Todd Nine
 * 
 */
public class CompositeSerializer extends AbstractSerializer<Composite> {

  @Override
  public ByteBuffer toByteBuffer(Composite obj) {

    return obj.serialize();
  }

  @Override
  public Composite fromByteBuffer(ByteBuffer byteBuffer) {

    Composite composite = new Composite();
    composite.deserialize(byteBuffer);

    return composite;

  }

}
