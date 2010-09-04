package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.utils.Assert;

public abstract class AbstractQuery<K, N, V, T> implements Query<T> {

    protected final KeyspaceOperator keyspaceOperator;
    protected String columnFamilyName;
    protected Serializer<K> keySerializer;
    protected Serializer<N> columnNameSerializer;
    protected Serializer<V> valueSerializer;

    /* package */AbstractQuery(KeyspaceOperator ko,
            Serializer<K> keySerializer, Serializer<N> nameSerializer,
            Serializer<V> valueSerializer) {
        Assert.noneNull(ko, keySerializer, nameSerializer, valueSerializer);
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

    public void setKeySerializer(Serializer<K> keySerializer) {
        this.keySerializer = keySerializer;
    }

    public Serializer<N> getColumnNameSerializer() {
        return columnNameSerializer;
    }

    public void setColumnNameSerializer(Serializer<N> columnNameSerializer) {
        this.columnNameSerializer = columnNameSerializer;
    }

    public Serializer<V> getValueSerializer() {
        return valueSerializer;
    }

    public void setValueSerializer(Serializer<V> valueSerializer) {
        this.valueSerializer = valueSerializer;
    }

}
