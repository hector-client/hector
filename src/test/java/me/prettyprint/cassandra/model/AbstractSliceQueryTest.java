package me.prettyprint.cassandra.model;


import static me.prettyprint.cassandra.utils.StringUtils.bytes;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.Rows;
import me.prettyprint.hector.api.query.QueryResult;

import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.SliceRange;
import org.junit.Test;
import org.mockito.Mockito;

public class AbstractSliceQueryTest {


  private static final StringSerializer se = StringSerializer.get();
  private static final Keyspace ko = Mockito.mock(ExecutingKeyspace.class);

  @Test
  public void testGetSetPredicate_columnNames() {
    ConcreteSliceQueury<String, String, String, Rows<String, String, String>> q =
      new ConcreteSliceQueury<String, String, String, Rows<String, String,String>>(ko, se, se, se);
    q.setColumnNames("1", "2", "3");
    SlicePredicate p = q.getPredicate();
    assertEquals(3, p.getColumn_names().size());
    assertNull(p.getSlice_range());
  }

  @Test
  public void testGetSetPredicate_range() {
    ConcreteSliceQueury<String, String, String, Rows<String, String, String>> q =
      new ConcreteSliceQueury<String, String, String, Rows<String, String,String>>(ko, se, se, se);
    q.setRange("1", "100", false, 10);
    SlicePredicate p = q.getPredicate();
    assertNull(p.getColumn_names());
    SliceRange range = p.getSlice_range();
    assertNotNull(range);
    assertArrayEquals(bytes("1"), range.getStart());
    assertArrayEquals(bytes("100"), range.getFinish());
    assertEquals(10, range.getCount());
  }

  private static class ConcreteSliceQueury<K, N, V, T> extends AbstractSliceQuery<K, N, V, T> {
    ConcreteSliceQueury(Keyspace k, Serializer<K> keySerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
      super(k, keySerializer, nameSerializer, valueSerializer);
    }

    @Override
    public QueryResult<T> execute() {
      return null;
    }
  }
}
