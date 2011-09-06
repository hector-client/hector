package me.prettyprint.cassandra.utils;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Random;

import org.apache.cassandra.utils.ByteBufferUtil;
import org.junit.Test;

public class ByteBufferOutputStreamTest {

  @Test
  public void testWriteLargeByteArray() throws IOException {
    ByteBufferOutputStream sink = new ByteBufferOutputStream();
    byte[] written = new byte[32 * 1000];
    new Random().nextBytes(written);
    sink.write(written);
    assertEquals(0, ByteBufferUtil.compare(sink.getByteBuffer(), written));
  }
  
  @Test
  public void testWriteByteArraySeveralSteps() throws IOException {
    ByteBufferOutputStream sink = new ByteBufferOutputStream();
    byte[] written = new byte[32 * ByteBufferOutputStream.INITIAL_BUFFER_SIZE];
    new Random().nextBytes(written);
    for(int i = 0; i < 32; i++) { 
      sink.write(written, i * ByteBufferOutputStream.INITIAL_BUFFER_SIZE, ByteBufferOutputStream.INITIAL_BUFFER_SIZE);      
    }
    assertEquals(0, ByteBufferUtil.compare(sink.getByteBuffer(), written));
  }

  @Test
  public void testWriteLargeByteBuffer() throws IOException {
    ByteBufferOutputStream sink = new ByteBufferOutputStream();
    byte[] written = new byte[32 * 1000];
    new Random().nextBytes(written);
    sink.write(ByteBuffer.wrap(written));
    assertEquals(0, ByteBufferUtil.compare(sink.getByteBuffer(), written));
  }
}
