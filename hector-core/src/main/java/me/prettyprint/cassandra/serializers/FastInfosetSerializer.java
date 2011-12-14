package me.prettyprint.cassandra.serializers;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.sun.xml.fastinfoset.stax.StAXDocumentParser;
import com.sun.xml.fastinfoset.stax.StAXDocumentSerializer;

/**
 * A serializer which performs JAXB serialization with fast infoset encoding.
 * Fast infoset is a binary encoding format for XML data which optimizes data
 * size as well as parse and serialization time. An instance of this class may
 * only serialize JAXB compatible objects of classes known to its configured
 * context.
 * 
 * @author shuzhang0@gmail.com
 * 
 */
public class FastInfosetSerializer extends JaxbSerializer {
  /**
   * Constructor.
   * 
   * @param serializableClasses
   *          List of classes which can be serialized by this instance. Note
   *          that concrete classes directly referenced by any class in the list
   *          will also be serializable through this instance.
   */
  public FastInfosetSerializer(final Class... serializableClasses) {
    super(serializableClasses);
  }

  /** {@inheritDoc} */
  @Override
  protected XMLStreamWriter createStreamWriter(OutputStream output)
      throws XMLStreamException {
    return new StAXDocumentSerializer(output);
  }

  /** {@inheritDoc} */
  @Override
  protected XMLStreamReader createStreamReader(InputStream input)
      throws XMLStreamException {
    StAXDocumentParser parser = new StAXDocumentParser(input);
    parser.setStringInterning(true);
    parser.setParseFragments(true);
    return parser;
  }
}
