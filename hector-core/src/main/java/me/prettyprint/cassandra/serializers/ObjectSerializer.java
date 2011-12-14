package me.prettyprint.cassandra.serializers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.nio.ByteBuffer;

import com.google.common.base.Preconditions;


import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.exceptions.HectorSerializationException;

/**
 * The ObjectSerializer is used to turn objects into their binary
 * representations.
 * 
 * @author Bozhidar Bozhanov
 * 
 */
public class ObjectSerializer extends AbstractSerializer<Object> implements
    Serializer<Object> {

  private static final ObjectSerializer INSTANCE = new ObjectSerializer();

  private final ClassLoader classLoader;
  
  public ObjectSerializer() {
    classLoader = null;
  }
  
  /**
   * 
   * @param cl - the classloader to use when deserializing objects.
   */
  public ObjectSerializer(ClassLoader cl) {
    Preconditions.checkNotNull(cl, "cl can't be null");
    this.classLoader = cl;
  }
  
  
  @Override
  public ByteBuffer toByteBuffer(Object obj) {
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(baos);
      oos.writeObject(obj);
      oos.close();
      return ByteBuffer.wrap(baos.toByteArray());
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  public Object fromByteBuffer(ByteBuffer bytes) {
    if ((bytes == null) || !bytes.hasRemaining()) {
      return null;
    }
    try {
      int l = bytes.remaining();
      ByteArrayInputStream bais = new ByteArrayInputStream(bytes.array(),
          bytes.arrayOffset() + bytes.position(), l);
      ObjectInputStream ois;
      if(classLoader == null) {
        ois = new ObjectInputStream(bais);
      } else {
        ois = new CustomClassLoaderObjectInputStream(classLoader, bais);
      }
      Object obj = ois.readObject();
      bytes.position(bytes.position() + (l - ois.available()));
      ois.close();
      return obj;
    } catch (Exception ex) {
      throw new HectorSerializationException(ex);
    }
  }

  public static ObjectSerializer get() {
    return INSTANCE;
  }

  /**
   * Object input stream that uses a custom class loader to resolve classes 
   */
  static class CustomClassLoaderObjectInputStream extends ObjectInputStream {
    
    private final ClassLoader classLoader;
    
    CustomClassLoaderObjectInputStream(ClassLoader classLoader, InputStream is) throws IOException { 
      super(is);
      this.classLoader = classLoader;
    }
    
    @Override
    protected Class<?> resolveClass(ObjectStreamClass desc) throws ClassNotFoundException {
      return Class.forName(desc.getName(), false, classLoader);
    }
    
  }
  
}
