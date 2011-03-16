package me.prettyprint.cassandra.service.template;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.mutation.Mutator;

/**
 * A simple specialization of the generic class for the very common all-string
 * column name and key type scenario. This just provided the StringSerializer at
 * all the places required by the generic base type. There are method overloads
 * for update/query that remove the need to pass serializers anywhere.
 * 
 * @author david
 * @since Mar 10, 2011
 */
public class ColumnFamilyStringTemplate extends ColumnFamilyTemplate<String, String> {

  public ColumnFamilyStringTemplate(Keyspace keyspace, String columnFamily) {
    super(keyspace, columnFamily, StringSerializer.get(), StringSerializer
        .get());
  }

  public ColumnFamilyStringTemplate(Keyspace keyspace, String columnFamily,
      Mutator<String> mutator) {
    super(keyspace, columnFamily, StringSerializer.get(), StringSerializer
        .get(), mutator);
  }

  // Just so method chaining will return this type instead of the parent class
  // for operations down the chain
  public ColumnFamilyStringTemplate setBatched(boolean batched) {
    super.setBatched(batched);
    return this;
  }

  // Just so method chaining will return this type instead of the parent class
  // for operations down the chain
  public ColumnFamilyStringTemplate setMutator(Mutator<String> mutator) {
    super.setMutator(mutator);
    return this;
  }
}
