package me.prettyprint.cassandra.serializers;

import java.util.Collection;
import java.util.Collections;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import me.prettyprint.hector.api.Serializer;

/**
 * Unit test for {@link JaxbSerializer}.
 * @author shuzhang0@gmail.com
 *
 */
public class JaxbSerializerTest extends SerializerBaseTest<Object> {

    @Override
    protected Serializer<Object> getSerializer() {
        return new JaxbSerializer(JaxbString.class);
    }

    @Override
    protected Collection<Object> getTestData() {
        return Collections.singleton((Object)new JaxbString("test"));
    }

    /** A simple wrapper for a string to make it JAXB compatible. */
    @XmlRootElement()
    @XmlAccessorType(XmlAccessType.FIELD)
    static class JaxbString {
        private String m_str;
        JaxbString(String str) {
            m_str = str;
        }

        /** private constructor for JAXB compatibility. */
        private JaxbString() {
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof JaxbString)) {
                return false;
            }

            if (m_str == null) {
                return ((JaxbString)obj).m_str == null;
            }

            return (m_str.equals(((JaxbString)obj).m_str));
        }
    }
}
