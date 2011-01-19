package me.prettyprint.cassandra.serializers;

import java.nio.ByteBuffer;

import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.exceptions.HectorSerializationException;

public class PrefixedSerializer<P, S> extends AbstractSerializer<S> {

	P prefix;
	Serializer<P> prefixSerializer;
	ByteBuffer prefixBytes;
	Serializer<S> suffixSerializer;

	public PrefixedSerializer(P prefix, Serializer<P> prefixSerializer, Serializer<S> suffixSerializer) {
		this.prefix = prefix;
		this.prefixSerializer = prefixSerializer;
		this.suffixSerializer = suffixSerializer;

		prefixBytes = prefixSerializer.toByteBuffer(prefix);
		prefixBytes.rewind();
	}

	@Override
	public ByteBuffer toByteBuffer(S s) {

		ByteBuffer sb = suffixSerializer.toByteBuffer(s);
		sb.rewind();

		ByteBuffer bb = ByteBuffer.allocate(prefixBytes.remaining()
				+ sb.remaining());

		bb.put(prefixBytes.slice());
		bb.put(sb);

		return bb;
	}

	@Override
	public S fromByteBuffer(ByteBuffer bytes) {
		bytes = bytes.duplicate();
		bytes.rewind();

		P p = prefixSerializer.fromByteBuffer(bytes);
		if (!prefix.equals(p)) {
			throw new HectorSerializationException("Unexpected prefix value");
		}
		bytes.position(prefixBytes.remaining());

		S s = suffixSerializer.fromByteBuffer(bytes);
		return s;
	}
}
