package me.prettyprint.cassandra.io;

import java.io.IOException;
import java.io.OutputStream;

import me.prettyprint.cassandra.serializers.BytesArraySerializer;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;

/**
 * Provide an {@link OutputStream} which will write to a row. The written data
 * will be split up by chunks of the given chunkSize. Each chunk we get written
 * to own column which will have the chunk number (starting at 0) as column key
 * (Long).
 * 
 * This implementation is not thread-safe!
 * 
 */
public class ChunkOutputStream<T> extends OutputStream {
  private byte[] chunk;
  private long chunkPos = 0;
  private String cf;
  private T key;
  private long pos = 0;
  private Mutator<T> mutator;

  public ChunkOutputStream(Keyspace keyspace, String cf, T key, Serializer<T> keySerializer, int chunkSize) {
    this.cf = cf;
    this.key = key;
    this.chunk = new byte[chunkSize];
    this.mutator = HFactory.createMutator(keyspace, keySerializer);
    mutator.delete(key, cf, null, null);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.io.OutputStream#write(int)
   */
  public void write(int b) throws IOException {
    if (chunk.length - 1 == pos) {
      flush();
    }
    chunk[(int) pos++] = (byte) b;
  }

  @Override
  public void close() throws IOException {
    writeData(true);
  }

  /**
   * Trigger a flush. This will only write the content to the column if the
   * chunk size is reached
   */
  @Override
  public void flush() throws IOException {
    writeData(false);
  }

  /**
   * Write the data to column if the configured chunk size is reached or if the
   * stream should be closed
   * 
   * @param close
   * @throws IOException
   */
  private void writeData(boolean close) throws IOException {
    if (pos != 0 && (close || pos == chunk.length - 1)) {
      byte[] data;
      if (pos != chunk.length - 1) {
        data = new byte[(int) pos + 1];
        // we need to adjust the array
        System.arraycopy(chunk, 0, data, 0, data.length);
      } else {
        data = chunk;
      }
      try {
        mutator.insert(key, cf, HFactory.createColumn(chunkPos, data, LongSerializer.get(), BytesArraySerializer.get()));
      } catch (HectorException e) {
        throw new IOException("Unable to write data", e);
      }
      chunkPos++;
      pos = 0;
    }

  }

}
