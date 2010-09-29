package me.prettyprint.cassandra.serializers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.exceptions.HectorSerializationException;

/**
 * The ObjectSerializer is used to turn objects into their binary representations.
 *
 * @author Bozhidar Bozhanov
 *
 */
public class ObjectSerializer extends AbstractSerializer<Object> implements Serializer<Object> {

  private static final ObjectSerializer INSTANCE = new ObjectSerializer();

  @Override
  public byte[] toBytes(Object obj) {
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(baos);
      oos.writeObject(obj);
      oos.close();

      return baos.toByteArray();
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  public Object fromBytes(byte[] bytes) {
    if (bytes == null || bytes.length == 0) {
      return null;
    }
    try {
      ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
      ObjectInputStream ois = new ObjectInputStream(bais);
      Object obj = ois.readObject();
      ois.close();

      return obj;
    } catch (Exception ex) {
      throw new HectorSerializationException(ex);
    }
  }

  public static ObjectSerializer get() {
    return INSTANCE;
  }

}
