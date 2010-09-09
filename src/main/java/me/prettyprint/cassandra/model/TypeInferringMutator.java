package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.serializers.TypeInferringSerializer;

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
        super(ko, TypeInferringSerializer.<K>get());
    }
}
