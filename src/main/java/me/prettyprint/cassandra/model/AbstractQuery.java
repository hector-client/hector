package me.prettyprint.cassandra.model;

import org.apache.commons.lang.Validate;

public abstract class AbstractQuery<K, N, V, T> implements Query<T> {

    protected final KeyspaceOperator keyspaceOperator;
    protected String columnFamilyName;
    protected Serializer<K> keySerializer;
    protected Serializer<N> columnNameSerializer;
    protected Serializer<V> valueSerializer;

    /* package */AbstractQuery(KeyspaceOperator ko,
            Serializer<K> keySerializer, Serializer<N> nameSerializer,
            Serializer<V> valueSerializer) {
        Validate.notNull(ko);
        keyspaceOperator = ko;
        this.keySerializer = keySerializer;
        this.columnNameSerializer = nameSerializer;
        this.valueSerializer = valueSerializer;
    }

    public AbstractQuery<K, N, V, T> setColumnFamily(String cf) {
        this.columnFamilyName = cf;
        return this;
    }

    public Serializer<K> getKeySerializer() {
        return keySerializer;
    }

    public AbstractQuery<K, N, V, T> setKeySerializer(Serializer<K> keySerializer) {
        this.keySerializer = keySerializer;
        return this;
    }

    public Serializer<N> getColumnNameSerializer() {
        return columnNameSerializer;
    }

    public AbstractQuery<K, N, V, T> setColumnNameSerializer(Serializer<N> columnNameSerializer) {
        this.columnNameSerializer = columnNameSerializer;
        return this;
    }

    public Serializer<V> getValueSerializer() {
        return valueSerializer;
    }

    public void setValueSerializer(Serializer<V> valueSerializer) {
        this.valueSerializer = valueSerializer;
    }

}
