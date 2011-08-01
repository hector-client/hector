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

	private static final DynamicCompositeSerializer instance = new DynamicCompositeSerializer();

	public static DynamicCompositeSerializer get() {
		return instance;
	}

	@Override
	public ByteBuffer toByteBuffer(DynamicComposite obj) {

		return obj.serialize();
	}

	@Override
	public DynamicComposite fromByteBuffer(ByteBuffer byteBuffer) {

		return DynamicComposite.fromByteBuffer(byteBuffer);

	}

	@Override
	public ComparatorType getComparatorType() {
		return DYNAMICCOMPOSITETYPE;
	}

}
