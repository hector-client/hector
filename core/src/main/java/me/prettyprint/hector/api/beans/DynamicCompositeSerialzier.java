/**
 * 
 */
package me.prettyprint.hector.api.beans;

import java.nio.ByteBuffer;

import me.prettyprint.cassandra.serializers.AbstractSerializer;

/**
 * @author Todd Nine
 *
 */
public class DynamicCompositeSerialzier extends AbstractSerializer<DynamicComposite>{

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

}
