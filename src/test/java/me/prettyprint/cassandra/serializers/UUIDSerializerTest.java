package me.prettyprint.cassandra.serializers;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;

/**
 *
 * @author Bozhidar Bozhanov
 *
 */
public class UUIDSerializerTest {

  @Test
  public void testConversions() {
    test(UUID.randomUUID());
    test(null);
  }

  private void test(UUID uuid) {
    UUIDSerializer ext = UUIDSerializer.get();
    assertEquals(uuid, ext.fromBytes(ext.toBytes(uuid)));
  }
}
