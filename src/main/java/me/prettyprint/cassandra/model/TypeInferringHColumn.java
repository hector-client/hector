package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.serializers.SerializerTypeInferer;

import org.apache.cassandra.thrift.Clock;

/**
 * The same as HColumn, but with the ability to infer the name and value
 * serializers from the arguments passed
 *
 * @author Bozhidar Bozhanov
 *
 * @param <N> column name type
 * @param <V> value type
 */
public class TypeInferringHColumn<N,V> extends HColumn<N,V> {

    public TypeInferringHColumn(N name, V value, Clock clock) {
        super(name, value, clock, SerializerTypeInferer.<N>getSerializer(name),
                SerializerTypeInferer.<V>getSerializer(value));
    }
}
