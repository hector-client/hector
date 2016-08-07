/**
 * 
 */
package me.prettyprint.cassandra.serializers;

import static me.prettyprint.hector.api.ddl.ComparatorType.COMPOSITETYPE;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.Composite;
import me.prettyprint.hector.api.ddl.ComparatorType;

/**
 * @author Todd Nine
 * 
 */
public class CompositeSerializer extends AbstractSerializer<Composite> {
    List<Serializer<?> > serializers = new ArrayList<Serializer<?>>();
  
  public static CompositeSerializer get() {
    return new CompositeSerializer();
  }
  
  @Override
  public ByteBuffer toByteBuffer(Composite obj) {
    
    serializers = obj.getSerializersByPosition();
    return obj.serialize();
  }

  @Override
  public Composite fromByteBuffer(ByteBuffer byteBuffer) {

    Composite composite = new Composite();
    if (serializers.size() > 0) {
        composite.setSerializersByPosition(serializers);
    }
    composite.deserialize(byteBuffer);

    return composite;

  }

  @Override
  public ComparatorType getComparatorType() {
    return COMPOSITETYPE;
  }

  public void addSerializers(Serializer<?> s) {
    serializers.add(s);
  }
  
  public void clearSerializers() {
    serializers.clear();
  }
}
