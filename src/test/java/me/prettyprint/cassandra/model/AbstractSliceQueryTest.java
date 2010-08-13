package me.prettyprint.cassandra.model;


import static me.prettyprint.cassandra.utils.StringUtils.bytes;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import me.prettyprint.cassandra.extractors.StringExtractor;

import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.SliceRange;
import org.junit.Test;
import org.mockito.Mockito;

public class AbstractSliceQueryTest {


  private static final StringExtractor se = StringExtractor.get();
  private static final KeyspaceOperator ko = Mockito.mock(KeyspaceOperator.class);

  @Test
  public void testGetSetPredicate_columnNames() {
    ConcreteSliceQueury<String, String, String, Rows<String, String, String>> q =
      new ConcreteSliceQueury<String, String, String, Rows<String, String,String>>(ko, se, se, se);
    assertNull(q.getPredicate());
    q.setColumnNames("1", "2", "3");
    SlicePredicate p = q.getPredicate();
    assertEquals(3, p.getColumn_names().size());
    assertNull(p.getSlice_range());
  }

  @Test
  public void testGetSetPredicate_range() {
    ConcreteSliceQueury<String, String, String, Rows<String, String, String>> q =
      new ConcreteSliceQueury<String, String, String, Rows<String, String,String>>(ko, se, se, se);
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

  private class ConcreteSliceQueury<K, N, V, T> extends AbstractSliceQuery<K, N, V, T> {

    ConcreteSliceQueury(KeyspaceOperator ko, Extractor<K> keyExtractor, Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
      super(ko, keyExtractor, nameExtractor, valueExtractor);
    }

    public Result<T> execute() {
      return null;
    }
  }
}
