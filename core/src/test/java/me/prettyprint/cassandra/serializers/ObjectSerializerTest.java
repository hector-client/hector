package me.prettyprint.cassandra.serializers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.net.URLClassLoader;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.prettyprint.hector.api.exceptions.HectorSerializationException;

import org.junit.Test;

/**
 *
 * @author Bozhidar Bozhanov
 *
 */
public class ObjectSerializerTest {

  @Test
  public void testConversions() {
    test(new SampleObject());
    test("Test string");
    test(Integer.MAX_VALUE);
    test(Long.MIN_VALUE);
    test(null);
  }

  @Test
  public void testEmptyByteArray_shouldReturnNull() {
    ObjectSerializer ser = ObjectSerializer.get();
    assertNull("An empty byte array should be translated to null", ser.fromByteBuffer(ByteBuffer.wrap(new byte[0])));
  }

  @Test(expected = HectorSerializationException.class)
  public void testMalformedObject_shouldThrow() {
    ObjectSerializer ser = ObjectSerializer.get();
    ser.fromByteBuffer(ByteBuffer.wrap(new byte[]{1, 2, 3}));
  }

  @Test
  public void testCustomClassLoader() throws Exception {
    ClassLoader bootstrapClassLoader = ClassLoader.getSystemClassLoader().getParent();

    //create a new class loader which has the same urls as the class loader used to load this class
    //the new class loader will create classes that are independent of this class
    URLClassLoader thisClassLoader = (URLClassLoader) SampleObject.class.getClassLoader();
    ClassLoader customClassLoader = new URLClassLoader(thisClassLoader.getURLs() , bootstrapClassLoader);

    //load the SampleObjectClass from the other class loader
    Class<?> sampleObjectClassOtherClassLoader = customClassLoader.loadClass(SampleObject.class.getName());
    //get a constructor we can access, we are not able to see the default
    //constructor without going through some hoops as we are no longer
    //the same class
    Constructor<?> constructor = null;
	final Constructor<?>[] declaredConstructors = sampleObjectClassOtherClassLoader.getDeclaredConstructors();
	// find the constructor w/o parameters
	for (Constructor<?> con : declaredConstructors) {
		if(con.getParameterTypes().length == 0) {
			con.setAccessible(true);
		 	constructor = con;
		  	break;
		}
	}
    //create the object
    //this is an instance of SampleObject, but from another class loader, so
    //we can't assign it to a variable of type SampleObject


    Object sampleObjectOtherCl = constructor.newInstance();

    ObjectSerializer ser = new ObjectSerializer(customClassLoader);
    Object deserialized = ser.fromByteBuffer(ser.toByteBuffer(sampleObjectOtherCl));

    assertFalse(deserialized.getClass() == SampleObject.class);
    assertTrue(deserialized.getClass() == sampleObjectClassOtherClassLoader);
    assertEquals(sampleObjectOtherCl, deserialized);
  }

  private void test(Object object) {
    ObjectSerializer ser = ObjectSerializer.get();
    assertEquals(object, ser.fromByteBuffer(ser.toByteBuffer(object)));
  }


  @SuppressWarnings("serial")
  private static class SampleObject implements Serializable {
    private final String a = "test";
    private final List<Void> b = new ArrayList<Void>();
    private final Calendar c = Calendar.getInstance();
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + (a == null ? 0 : a.hashCode());
      result = prime * result + (b == null ? 0 : b.hashCode());
      result = prime * result + (c == null ? 0 : c.hashCode());
      return result;
    }
    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      SampleObject other = (SampleObject) obj;
      if (a == null) {
        if (other.a != null) {
          return false;
        }
      } else if (!a.equals(other.a)) {
        return false;
      }
      if (b == null) {
        if (other.b != null) {
          return false;
        }
      } else if (!b.equals(other.b)) {
        return false;
      }
      if (c == null) {
        if (other.c != null) {
          return false;
        }
      } else if (!c.equals(other.c)) {
        return false;
      }
      return true;
    }
  }
}
