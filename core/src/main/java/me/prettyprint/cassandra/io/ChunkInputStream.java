package me.prettyprint.cassandra.io;

import java.io.IOException;
import java.io.InputStream;

import me.prettyprint.cassandra.serializers.BytesArraySerializer;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.ColumnQuery;
import me.prettyprint.hector.api.query.QueryResult;

/**
 * Return an InputStream which retrieve columns from a row which stores chunk of
 * data. See also {@link ChunkOutputStream}
 * 
 * This implementation is not thread-safe!
 * 
 * @param <T>
 */
public class ChunkInputStream<T> extends InputStream {

  private T key;
  private byte[] chunk;
  private Keyspace keyspace;
  private String cf;
  private long chunkPos = 0;
  private int pos;
  private Serializer<T> rowKeySerializer;

  public ChunkInputStream(Keyspace keyspace, String cf, T key, Serializer<T> rowKeySerializer) {
    this.key = key;
    this.keyspace = keyspace;
    this.cf = cf;
    this.rowKeySerializer = rowKeySerializer;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.io.InputStream#read()
   */
  public int read() throws IOException {
    if (chunk == null || pos + 1 == chunk.length) {
      if (!fetchChunk()) {
        return -1;
      }
    }
    return chunk[pos++] & 0xff;
  }

  /**
   * Fetch the next chunk.
   * 
   * @return exists if there was a chunk to fetch.
   * @throws IOException
   */
  private boolean fetchChunk() throws IOException {
    try {
      ColumnQuery<T, Long, byte[]> query = HFactory.createColumnQuery(keyspace, rowKeySerializer, LongSerializer.get(), BytesArraySerializer.get());
      QueryResult<HColumn<Long, byte[]>> result = query.setColumnFamily(cf).setKey(key).setName(chunkPos).execute();
      HColumn<Long, byte[]> column = result.get();
      if (column != null) {
        chunk = column.getValue();
        chunkPos++;
        pos = 0;

        return true;
      } else {
        return false;
      }
    } catch (HectorException e) {
      throw new IOException("Unable to read data", e);
    }
  }

  /**
   * Not supported
   */
  public boolean markSupported() {
    return false;
  }

}
