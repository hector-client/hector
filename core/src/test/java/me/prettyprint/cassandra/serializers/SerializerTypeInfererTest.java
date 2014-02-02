package me.prettyprint.cassandra.serializers;

import org.junit.Test;

import java.io.Serializable;

import static org.junit.Assert.*;

/**
 * Unit tests for the {@link me.prettyprint.cassandra.serializers.SerializerTypeInferer} class.
 */
public class SerializerTypeInfererTest {

    @Test
    public void testWhenImplementingInterfaceThatExtendsSerializable() {
        assertTrue(SerializerTypeInferer.getSerializer(MyType.class) instanceof ObjectSerializer);
    }

    public static interface MySerializable extends Serializable {

    }

    public static class MyType implements MySerializable {

    }
}
