/**
 * 
 */
package me.prettyprint.cassandra.serializers;

import static me.prettyprint.hector.api.ddl.ComparatorType.DYNAMICCOMPOSITETYPE;

import java.nio.ByteBuffer;

import me.prettyprint.hector.api.beans.DynamicComposite;
import me.prettyprint.hector.api.ddl.ComparatorType;

/**
 * @author Todd Nine
 * 
 */
public class DynamicCompositeSerializer extends
    AbstractSerializer<DynamicComposite> {

  @Override
  public ByteBuffer toByteBuffer(DynamicComposite obj) {

    return obj.serialize();
  }

  @Override
  public DynamicComposite fromByteBuffer(ByteBuffer byteBuffer) {

    DynamicComposite composite = new DynamicComposite();
    composite.deserialize(byteBuffer);

    return composite;

  }

  @Override
  public ComparatorType getComparatorType() {
    return DYNAMICCOMPOSITETYPE;
  }

}
