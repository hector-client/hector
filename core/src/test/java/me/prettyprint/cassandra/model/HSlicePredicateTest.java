package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.serializers.StringSerializer;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author zznate
 */
public class HSlicePredicateTest {

  @Test
  public void test_slice_predicate_clone() {
    HSlicePredicate<String> orig = new HSlicePredicate(StringSerializer.get());
    orig.setRange("start","end",true,50);

    HSlicePredicate<String> cp = orig.clone();

    assertEquals(orig.toString(), cp.toString());
  }

  @Test
  public void test_set_range_for_count_only() {
    HSlicePredicate<String> orig = new HSlicePredicate(StringSerializer.get());
    orig.setRangeForCountOnly(100);
    assertNotNull(orig.toThrift());
  }
}
