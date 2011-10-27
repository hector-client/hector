package me.prettyprint.hector.api.beans;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;
import java.util.UUID;

import me.prettyprint.cassandra.serializers.AsciiSerializer;
import me.prettyprint.cassandra.serializers.BytesArraySerializer;
import me.prettyprint.cassandra.serializers.DynamicCompositeSerializer;
import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.TimeUUIDSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.hector.api.beans.AbstractComposite.ComponentEquality;

import org.apache.cassandra.db.marshal.AsciiType;
import org.apache.cassandra.db.marshal.LexicalUUIDType;
import org.junit.Test;

public class DynamicCompositeTest {

	@Test
	public void allTypesSerialize() {
		DynamicComposite composite = new DynamicComposite();
		
		UUID lexUUID = UUID.randomUUID();
		com.eaio.uuid.UUID timeUUID = new com.eaio.uuid.UUID();
		
	
		//add all forward comparators
		composite.addComponent(0, "AsciiText", AsciiSerializer.get(), "AsciiType", ComponentEquality.EQUAL);
		composite.addComponent(1, new byte[]{0, 1, 2, 3}, BytesArraySerializer.get(), "BytesType", ComponentEquality.EQUAL);
		composite.addComponent(2, -1, IntegerSerializer.get(), "IntegerType", ComponentEquality.EQUAL);
		composite.addComponent(3,  lexUUID, UUIDSerializer.get(), "LexicalUUIDType", ComponentEquality.EQUAL);
		composite.addComponent(4, -1l, LongSerializer.get(), "LongType", ComponentEquality.EQUAL);
		composite.addComponent(5, timeUUID, TimeUUIDSerializer.get(), "TimeUUIDType", ComponentEquality.EQUAL);
		composite.addComponent(6, "UTF8Text", StringSerializer.get(), "UTF8Type", ComponentEquality.EQUAL);
		composite.addComponent(7,  lexUUID, UUIDSerializer.get(), "UUIDType", ComponentEquality.EQUAL);
		
		//add all reverse comparators
		composite.addComponent(8, "AsciiText", AsciiSerializer.get(), "AsciiType(reversed=true)", ComponentEquality.EQUAL);
		composite.addComponent(9, new byte[]{0, 1, 2, 3}, BytesArraySerializer.get(), "BytesType(reversed=true)", ComponentEquality.EQUAL);
		composite.addComponent(10, -1, IntegerSerializer.get(), "IntegerType(reversed=true)", ComponentEquality.EQUAL);
		composite.addComponent(11,  lexUUID, UUIDSerializer.get(), "LexicalUUIDType(reversed=true)", ComponentEquality.EQUAL);
		composite.addComponent(12, -1l, LongSerializer.get(), "LongType(reversed=true)", ComponentEquality.EQUAL);
		composite.addComponent(13, timeUUID, TimeUUIDSerializer.get(), "TimeUUIDType(reversed=true)", ComponentEquality.EQUAL);
		composite.addComponent(14, "UTF8Text", StringSerializer.get(), "UTF8Type(reversed=true)", ComponentEquality.EQUAL);
		composite.addComponent(15,  lexUUID, UUIDSerializer.get(), "UUIDType(reversed=true)", ComponentEquality.EQUAL);
		composite.addComponent(16, "My element", ComponentEquality.EQUAL);
		
		//serialize to the native bytes value
		
		ByteBuffer buffer = DynamicCompositeSerializer.get().toByteBuffer(composite);
		
	
		//now deserialize and ensure the values are the same
		DynamicComposite parsed = DynamicCompositeSerializer.get().fromByteBuffer(buffer);
		
		assertEquals("AsciiText", parsed.get(0, AsciiSerializer.get()));
		assertArrayEquals(new byte[]{0, 1, 2, 3}, parsed.get(1, BytesArraySerializer.get()));
		assertEquals(Integer.valueOf(-1), parsed.get(2, IntegerSerializer.get()));
		assertEquals(lexUUID, parsed.get(3, UUIDSerializer.get()));
		assertEquals(Long.valueOf(-1l), parsed.get(4, LongSerializer.get()));
		assertEquals(timeUUID, parsed.get(5, TimeUUIDSerializer.get()));
		assertEquals("UTF8Text", parsed.get(6, StringSerializer.get()));
		assertEquals(lexUUID, parsed.get(7, UUIDSerializer.get()));
		
		//now test all the reversed values
		assertEquals("AsciiText", parsed.get(8, AsciiSerializer.get()));
		assertArrayEquals(new byte[]{0, 1, 2, 3}, parsed.get(9, BytesArraySerializer.get()));
		assertEquals(Integer.valueOf(-1), parsed.get(10, IntegerSerializer.get()));
		assertEquals(lexUUID, parsed.get(11, UUIDSerializer.get()));
		assertEquals(Long.valueOf(-1l), parsed.get(12, LongSerializer.get()));
		assertEquals(timeUUID, parsed.get(13, TimeUUIDSerializer.get()));
		assertEquals("UTF8Text", parsed.get(14, StringSerializer.get()));
		assertEquals(lexUUID, parsed.get(15, UUIDSerializer.get()));
		assertEquals("My element", parsed.get(16, StringSerializer.get()));
		

	}

}
