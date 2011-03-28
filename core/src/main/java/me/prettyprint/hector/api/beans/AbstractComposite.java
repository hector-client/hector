package me.prettyprint.hector.api.beans;

import static me.prettyprint.hector.api.ddl.ComparatorType.ASCIITYPE;
import static me.prettyprint.hector.api.ddl.ComparatorType.BYTESTYPE;
import static me.prettyprint.hector.api.ddl.ComparatorType.INTEGERTYPE;
import static me.prettyprint.hector.api.ddl.ComparatorType.LEXICALUUIDTYPE;
import static me.prettyprint.hector.api.ddl.ComparatorType.LONGTYPE;
import static me.prettyprint.hector.api.ddl.ComparatorType.TIMEUUIDTYPE;
import static me.prettyprint.hector.api.ddl.ComparatorType.UTF8TYPE;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import me.prettyprint.cassandra.serializers.AsciiSerializer;
import me.prettyprint.cassandra.serializers.BigIntegerSerializer;
import me.prettyprint.cassandra.serializers.ByteBufferSerializer;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.SerializerTypeInferer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.cassandra.utils.ByteBufferOutputStream;
import me.prettyprint.hector.api.Serializer;

import org.apache.cassandra.utils.ByteBufferUtil;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableClassToInstanceMap;

@SuppressWarnings("rawtypes")
public abstract class AbstractComposite extends AbstractList<Object> implements
    Comparable<AbstractComposite> {

  static final Logger logger = Logger.getLogger(AbstractComposite.class
      .getName());

  public static final BiMap<Class<? extends Serializer>, String> DEFAULT_SERIALIZER_TO_COMPARATOR_MAPPING = new ImmutableBiMap.Builder<Class<? extends Serializer>, String>()
      .put(AsciiSerializer.class, ASCIITYPE.getTypeName())
      .put(BigIntegerSerializer.class, INTEGERTYPE.getTypeName())
      .put(ByteBufferSerializer.class, BYTESTYPE.getTypeName())
      .put(LongSerializer.class, LONGTYPE.getTypeName())
      .put(StringSerializer.class, UTF8TYPE.getTypeName())
      .put(UUIDSerializer.class, "UUIDType").build();

  static final ImmutableClassToInstanceMap<Serializer> SERIALIZERS = new ImmutableClassToInstanceMap.Builder<Serializer>()
      .put(AsciiSerializer.class, AsciiSerializer.get())
      .put(BigIntegerSerializer.class, BigIntegerSerializer.get())
      .put(ByteBufferSerializer.class, ByteBufferSerializer.get())
      .put(LongSerializer.class, LongSerializer.get())
      .put(StringSerializer.class, StringSerializer.get())
      .put(UUIDSerializer.class, UUIDSerializer.get()).build();

  public static final BiMap<Byte, String> DEFAULT_ALIAS_TO_COMPARATOR_MAPPING = new ImmutableBiMap.Builder<Byte, String>()
      .put((byte) 'a', ASCIITYPE.getTypeName())
      .put((byte) 'b', BYTESTYPE.getTypeName())
      .put((byte) 'i', INTEGERTYPE.getTypeName())
      .put((byte) 'x', LEXICALUUIDTYPE.getTypeName())
      .put((byte) 'l', LONGTYPE.getTypeName())
      .put((byte) 't', TIMEUUIDTYPE.getTypeName())
      .put((byte) 's', UTF8TYPE.getTypeName()).build();

  BiMap<Class<? extends Serializer>, String> serializerToComparatorMapping = DEFAULT_SERIALIZER_TO_COMPARATOR_MAPPING;

  BiMap<Byte, String> aliasToComparatorMapping = DEFAULT_ALIAS_TO_COMPARATOR_MAPPING;

  boolean autoDeserialize = true;

  final boolean dynamic;

  List<Serializer<?>> serializersByPosition = null;
  List<String> comparatorsByPosition = null;

  public class Component<T> {
    final Serializer<T> serializer;
    final T value;
    final String comparator;
    final boolean inclusive;

    public Component(T value, Serializer<T> serializer, String comparator,
        boolean inclusive) {
      this.serializer = serializer;
      this.value = value;
      this.comparator = comparator;
      this.inclusive = inclusive;
    }

    public Serializer<T> getSerializer() {
      return serializer;
    }

    public T getValue() {
      return value;
    }

    public String getComparator() {
      return comparator;
    }

    public boolean isInclusive() {
      return inclusive;
    }
  }

  List<Component> components = new ArrayList<Component>();

  ByteBuffer serialized = null;

  public AbstractComposite(boolean dynamic) {
    this.dynamic = dynamic;
  }

  public AbstractComposite(boolean dynamic, Object... o) {
    this.dynamic = dynamic;
    this.addAll(Arrays.asList(o));
  }

  public List<Component> getComponents() {
    return components;
  }

  public void setComponents(List<Component> components) {
    serialized = null;
    this.components = components;
  }

  public Map<Class<? extends Serializer>, String> getSerializerToComparatorMapping() {
    return serializerToComparatorMapping;
  }

  public void setSerializerToComparatorMapping(
      Map<Class<? extends Serializer>, String> serializerToComparatorMapping) {
    serialized = null;
    this.serializerToComparatorMapping = new ImmutableBiMap.Builder<Class<? extends Serializer>, String>()
        .putAll(serializerToComparatorMapping).build();
  }

  public Map<Byte, String> getAliasesToComparatorMapping() {
    return aliasToComparatorMapping;
  }

  public void setAliasesToComparatorMapping(
      Map<Byte, String> aliasesToComparatorMapping) {
    serialized = null;
    aliasToComparatorMapping = new ImmutableBiMap.Builder<Byte, String>()
        .putAll(aliasesToComparatorMapping).build();
  }

  public boolean isAutoDeserialize() {
    return autoDeserialize;
  }

  public void setAutoDeserialize(boolean autoDeserialize) {
    this.autoDeserialize = autoDeserialize;
  }

  public boolean isDynamic() {
    return dynamic;
  }

  public List<Serializer<?>> getSerializersByPosition() {
    return serializersByPosition;
  }

  public void setSerializersByPosition(List<Serializer<?>> serializersByPosition) {
    this.serializersByPosition = serializersByPosition;
  }

  public void setSerializersByPosition(Serializer<?>... serializers) {
    serializersByPosition = Arrays.asList(serializers);
  }

  public List<String> getComparatorsByPosition() {
    return comparatorsByPosition;
  }

  public void setComparatorsByPosition(List<String> comparatorsByPosition) {
    this.comparatorsByPosition = comparatorsByPosition;
  }

  public void setComparatorsByPosition(String... comparators) {
    comparatorsByPosition = Arrays.asList(comparators);
  }

  @Override
  public int compareTo(AbstractComposite o) {
    return serialize().compareTo(o.serialize());
  }

  private String comparatorForSerializer(Serializer<?> s) {
    String comparator = serializerToComparatorMapping.get(s.getClass());
    if (comparator != null) {
      return comparator;
    }
    return BYTESTYPE.getTypeName();
  }

  private String comparatorForUUID(UUID uuid) {
    if (uuid.version() == 1) {
      return TIMEUUIDTYPE.getTypeName();
    }
    return LEXICALUUIDTYPE.getTypeName();
  }

  private Serializer<?> serializerForComparator(String c) {
    int p = c.indexOf('(');
    if (p >= 0) {
      c = c.substring(0, p);
    }
    if (LEXICALUUIDTYPE.getTypeName().equals(c)
        || TIMEUUIDTYPE.getTypeName().equals(c)) {
      return UUIDSerializer.get();
    }

    Serializer<?> s = SERIALIZERS.getInstance(serializerToComparatorMapping
        .inverse().get(c));
    if (s != null) {
      return s;
    }
    return ByteBufferSerializer.get();
  }

  private Serializer<?> serializerForPosition(int i) {
    if (serializersByPosition == null) {
      return null;
    }
    if (i >= serializersByPosition.size()) {
      return null;
    }
    return serializersByPosition.get(i);
  }

  private Serializer<?> getSerializer(int i, String c) {
    Serializer<?> s = serializerForPosition(i);
    if (s != null) {
      return s;
    }
    return serializerForComparator(c);
  }

  private String comparatorForPosition(int i) {
    if (comparatorsByPosition == null) {
      return null;
    }
    if (i >= comparatorsByPosition.size()) {
      return null;
    }
    return comparatorsByPosition.get(i);
  }

  private String getComparator(int i, ByteBuffer bb) {
    String name = comparatorForPosition(i);
    if (name != null) {
      return name;
    }
    if (!dynamic) {
      if (bb.hasRemaining()) {
        return BYTESTYPE.getTypeName();
      } else {
        return null;
      }
    }
    if (bb.hasRemaining()) {
      try {
        int header = getShortLength(bb);
        if ((header & 0x8000) == 0) {
          name = ByteBufferUtil.string(getBytes(bb, header));
        } else {
          byte a = (byte) (header & 0xFF);
          name = aliasToComparatorMapping.get(a);
          if (name == null) {
            a = (byte) Character.toUpperCase(a);
            name = aliasToComparatorMapping.get(a);
            if (name != null) {
              name += "(sort=desc)";
            }
          }
        }
      } catch (CharacterCodingException e) {
        throw new RuntimeException(e);
      }
    }
    if ((name != null) && (name.length() == 0)) {
      name = null;
    }
    return name;
  }

  public <T> AbstractComposite add(T value, Serializer<T> s) {
    serialized = null;

    add(value, s, value instanceof UUID ? comparatorForUUID((UUID) value)
        : comparatorForSerializer(s));

    return this;

  }

  public <T> AbstractComposite add(T value, Serializer<T> s, String comparator) {
    serialized = null;

    add(value, s, comparator, false);

    return this;

  }

  @SuppressWarnings("unchecked")
  public <T> AbstractComposite add(T value, Serializer<T> s, String comparator,
      boolean inclusive) {
    serialized = null;

    components.add(new Component(value, s, comparator, inclusive));

    return this;

  }

  @Override
  public void clear() {
    serialized = null;
    components = new ArrayList<Component>();
  }

  @Override
  public int size() {
    return components.size();
  }

  @SuppressWarnings("unchecked")
  @Override
  public void add(int index, Object element) {
    serialized = null;
    Serializer s = serializerForPosition(index);
    if (s == null) {
      s = SerializerTypeInferer.getSerializer(element);
    }
    String c = comparatorForPosition(index);
    if (c == null) {
      c = element instanceof UUID ? comparatorForUUID((UUID) element)
          : comparatorForSerializer(s);
    }
    components.add(index, new Component(element, s, c, false));
  }

  @Override
  public Object remove(int index) {
    serialized = null;
    Component prev = components.remove(index);
    if (prev != null) {
      return prev.getValue();
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Object set(int index, Object element) {
    serialized = null;
    Serializer s = serializerForPosition(index);
    if (s == null) {
      s = SerializerTypeInferer.getSerializer(element);
    }
    String c = comparatorForPosition(index);
    if (c == null) {
      c = element instanceof UUID ? comparatorForUUID((UUID) element)
          : comparatorForSerializer(s);
    }
    Component prev = components.set(index, new Component(element, s, c, false));
    if (prev != null) {
      return prev.getValue();
    }
    return null;
  }

  @Override
  public Object get(int i) {
    Component c = components.get(i);
    if (c != null) {
      return c.getValue();
    }
    return null;
  }

  public <T> T get(int i, Serializer<T> s) throws ClassCastException {
    Component c = components.get(i);
    if (c != null) {
      return s.fromByteBuffer((ByteBuffer) c.getValue());
    }
    return null;
  }

  public Component getComponent(int i) {
    if (i >= components.size()) {
      return null;
    }
    Component c = components.get(i);
    return c;
  }

  public Iterator<Component> componentsIterator() {
    return components.iterator();
  }

  @SuppressWarnings("unchecked")
  public ByteBuffer serialize() {
    if (serialized != null) {
      return serialized.duplicate();
    }

    ByteBufferOutputStream out = new ByteBufferOutputStream();

    int i = 0;
    for (Component c : components) {
      Serializer s = serializerForPosition(i);
      if (s == null) {
        s = c.getSerializer();
      }
      ByteBuffer cb = s.toByteBuffer(c.getValue());

      if (dynamic) {
        String comparator = comparatorForPosition(i);
        if (comparator == null) {
          comparator = c.getComparator();
        }
        int p = comparator.indexOf("(sort=desc)");
        boolean desc = false;
        if (p >= 0) {
          comparator = comparator.substring(0, p);
          desc = true;
        }
        if (aliasToComparatorMapping.inverse().containsKey(comparator)) {
          byte a = aliasToComparatorMapping.inverse().get(comparator);
          if (desc) {
            a = (byte) Character.toUpperCase(a);
          }
          out.writeShort((short) (0x8000 | a));
        } else {
          out.writeShort((short) comparator.length());
          out.write(ByteBufferUtil.bytes(comparator));
        }
      }
      out.writeShort((short) cb.remaining());
      out.write(cb.slice());
      out.write(c.isInclusive() ? 1 : 0);
      i++;
    }

    serialized = out.getByteBuffer();
    return serialized.duplicate();
  }

  @SuppressWarnings("unchecked")
  public void deserialize(ByteBuffer b) {
    serialized = b.duplicate();
    components = new ArrayList<Component>();

    String comparator = null;
    int i = 0;
    while ((comparator = getComparator(i, b)) != null) {
      ByteBuffer data = getWithShortLength(b);
      if (data != null) {
        Serializer<?> s = autoDeserialize ? getSerializer(i, comparator)
            : ByteBufferSerializer.get();
        Object value = s.fromByteBuffer(data);
        boolean inclusive = b.get() != 0;
        components.add(new Component(value, s, comparator, inclusive));
      } else {
        throw new RuntimeException("Missing component data in composite type");
      }
      i++;
    }

  }

  protected static int getShortLength(ByteBuffer bb) {
    int length = (bb.get() & 0xFF) << 8;
    return length | (bb.get() & 0xFF);
  }

  protected static ByteBuffer getBytes(ByteBuffer bb, int length) {
    ByteBuffer copy = bb.duplicate();
    copy.limit(copy.position() + length);
    bb.position(bb.position() + length);
    return copy;
  }

  protected static ByteBuffer getWithShortLength(ByteBuffer bb) {
    int length = getShortLength(bb);
    return getBytes(bb, length);
  }

}
