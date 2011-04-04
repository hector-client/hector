/**
 * 
 */
package me.prettyprint.cassandra.serializers;

import static me.prettyprint.hector.api.ddl.ComparatorType.COMPOSITETYPE;

import java.nio.ByteBuffer;

import me.prettyprint.hector.api.beans.Composite;
import me.prettyprint.hector.api.ddl.ComparatorType;

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

  @Override
  public ComparatorType getComparatorType() {
    return COMPOSITETYPE;
  }

}
