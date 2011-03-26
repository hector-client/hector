package me.prettyprint.cassandra.model.thrift;

import me.prettyprint.cassandra.model.HColumnImpl;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.ColumnFactory;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.factory.HFactory;

public class ThriftColumnFactory implements ColumnFactory {

  @Override
  public <N, V> HColumn<N, V> createColumn(N name, V value,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new HColumnImpl<N,V>(name, value, HFactory.createClock(), nameSerializer,
        valueSerializer);
  }

  @Override
  public <N, V> HColumn<N, V> createColumn(N name, V value, long clock,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new HColumnImpl<N,V>(name, value, clock, nameSerializer,
        valueSerializer);
  }

  @Override
  public HColumn<String, String> createStringColumn(String name, String value) {
    return createColumn(name, value, StringSerializer.get(), StringSerializer.get());
  }


}
