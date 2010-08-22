package me.prettyprint.cassandra.model;


import static me.prettyprint.cassandra.utils.StringUtils.bytes;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import me.prettyprint.cassandra.serializers.StringSerializer;

import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.SliceRange;
import org.junit.Test;
import org.mockito.Mockito;

public class AbstractSliceQueryTest {


  private static final StringSerializer se = StringSerializer.get();
  private static final KeyspaceOperator ko = Mockito.mock(KeyspaceOperator.class);

  @Test
  public void testGetSetPredicate_columnNames() {
    ConcreteSliceQueury<String, String, Rows<String, String>> q =
      new ConcreteSliceQueury<String, String, Rows<String,String>>(ko, se, se);
    assertNull(q.getPredicate());
    q.setColumnNames("1", "2", "3");
    SlicePredicate p = q.getPredicate();
    assertEquals(3, p.getColumn_names().size());
    assertNull(p.getSlice_range());
  }

  @Test
  public void testGetSetPredicate_range() {
    ConcreteSliceQueury<String, String, Rows<String, String>> q =
      new ConcreteSliceQueury<String, String, Rows<String,String>>(ko, se, se);
    assertNull(q.getPredicate());
    q.setRange("1", "100", false, 10);
    SlicePredicate p = q.getPredicate();
    assertNull(p.getColumn_names());
    SliceRange range = p.getSlice_range();
    assertNotNull(range);
    assertArrayEquals(bytes("1"), range.getStart());
    assertArrayEquals(bytes("100"), range.getFinish());
    assertEquals(10, range.getCount());
  }

  private static class ConcreteSliceQueury<N, V, T> extends AbstractSliceQuery<N, V, T> {

    ConcreteSliceQueury(KeyspaceOperator ko, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
      super(ko, nameSerializer, valueSerializer);
    }

    @Override
    public Result<T> execute() {
      return null;
    }
  }
}
