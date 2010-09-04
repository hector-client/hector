package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.serializers.SerializerUtils;
import me.prettyprint.cassandra.serializers.StringSerializer;

public class AdvancedColumnQuery<K,N,V> extends ColumnQuery<K,N,V> {

    public AdvancedColumnQuery(KeyspaceOperator keyspaceOperator,
            Serializer<V> valueSerializer) {
        super(keyspaceOperator, null, null, valueSerializer);
    }

    @SuppressWarnings("unchecked")
    public AdvancedColumnQuery(KeyspaceOperator keyspaceOperator) {
        super(keyspaceOperator, null, null, (Serializer<V>) StringSerializer.get());
    }

    @Override
    public ColumnQuery<K,N,V> setKey(K key) {
        setKeySerializer(SerializerUtils.<K>getSerializer(key));
        return super.setKey(key);
    }

    @Override
    public ColumnQuery<K,N,V> setName(N name) {
        setKeySerializer(SerializerUtils.<K>getSerializer(name));
        return super.setName(name);
    }

}
