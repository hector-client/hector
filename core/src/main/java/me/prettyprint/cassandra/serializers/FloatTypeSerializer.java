package me.prettyprint.cassandra.serializers;

import java.nio.ByteBuffer;
/**
 * Serialization class to use with Cassandras FloatType.
 * 
 * @author Felix Obenauer 
 */
public class FloatTypeSerializer extends AbstractSerializer<Float> {
	
	private static final FloatTypeSerializer instance = new FloatTypeSerializer();

	public static FloatTypeSerializer get() {
		return instance;
	
	}
	@Override
	public ByteBuffer toByteBuffer(Float obj) {
		if (obj == null) {
		      return null;
		    }
		byte[] bytes = new byte[4];
	    ByteBuffer result = ByteBuffer.wrap(bytes).putFloat(obj);
	    result.rewind();
	    return result;
	}

	@Override
	public Float fromByteBuffer(ByteBuffer byteBuffer) {
		float result = byteBuffer.getFloat();
		return result;
	}

}
