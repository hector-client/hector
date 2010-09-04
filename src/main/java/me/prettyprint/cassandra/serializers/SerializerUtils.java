package me.prettyprint.cassandra.serializers;

import java.util.UUID;

import me.prettyprint.cassandra.model.Serializer;
import me.prettyprint.cassandra.serializers.BytesSerializer;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;

public class SerializerUtils {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <T> Serializer<T> getSerializer(Object value) {
        Serializer serializer = null;
        if (value instanceof UUID) {
            serializer = UUIDSerializer.get();
        } else if (value instanceof String) {
            serializer = StringSerializer.get();
        } else if (value instanceof Number) {
            serializer = LongSerializer.get();
        } else if (value instanceof byte[]) {
            serializer = BytesSerializer.get();
        } else {
            serializer = ObjectSerializer.get();
        }

        return serializer;
    }
}
