package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.serializers.SerializerTypeInferer;

/**
 * The same as Mutator, but with the ability to infer the key serializer
 * from the key passed
 *
 * @author Bozhidar Bozhanov
 *
 * @param <K> key type
 */
public class TypeInferringMutator<K> extends Mutator<K> {

    public TypeInferringMutator(KeyspaceOperator ko) {
        super(ko, null);
    }

    @Override
    public <N, V> MutationResult insert(K key, String cf, HColumn<N, V> c) {
        inferSerializer(key);
        return super.insert(key, cf, c);
    }

    @Override
    public <SN, N, V> MutationResult insert(K key, String cf,
            HSuperColumn<SN, N, V> superColumn) {
        inferSerializer(key);
        return super.insert(key, cf, superColumn);
    }

    @Override
    public <N> MutationResult delete(K key, String cf, N columnName,
            Serializer<N> nameSerializer) {
        inferSerializer(key);
        return super.delete(key, cf, columnName, nameSerializer);
    }

    @Override
    public <SN, N> MutationResult superDelete(K key, String cf,
            SN supercolumnName, N columnName, Serializer<SN> sNameSerializer,
            Serializer<N> nameSerializer, Serializer<K> keySerializer) {
        inferSerializer(key);
        return super.superDelete(key, cf, supercolumnName, columnName, sNameSerializer,
                nameSerializer, keySerializer);
    }

    @Override
    public <N, V> Mutator<K> addInsertion(K key, String cf, HColumn<N, V> c) {
        inferSerializer(key);
        return super.addInsertion(key, cf, c);
    }

    @Override
    public <SN, N, V> Mutator<K> addInsertion(K key, String cf,
            HSuperColumn<SN, N, V> sc) {
        inferSerializer(key);
        return super.addInsertion(key, cf, sc);
    }

    @Override
    public <N> Mutator<K> addDeletion(K key, String cf, N columnName,
            Serializer<N> nameSerializer) {
        inferSerializer(key);
        return super.addDeletion(key, cf, columnName, nameSerializer);
    }

    protected void inferSerializer(K key) {
        keySerializer = SerializerTypeInferer.getSerializer(key);
    }
}
