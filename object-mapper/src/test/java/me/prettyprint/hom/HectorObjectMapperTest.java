package me.prettyprint.hom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import me.prettyprint.cassandra.serializers.BooleanSerializer;
import me.prettyprint.cassandra.serializers.BytesArraySerializer;
import me.prettyprint.cassandra.serializers.DateSerializer;
import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.ObjectSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hom.CFMappingDef;
import me.prettyprint.hom.ClassCacheMgr;
import me.prettyprint.hom.HectorObjectMapper;
import me.prettyprint.hom.beans.MyCustomIdBean;
import me.prettyprint.hom.beans.MyTestBean;

import org.junit.Before;
import org.junit.Test;

import com.mycompany.MySerial;


public class HectorObjectMapperTest {
  ClassCacheMgr cacheMgr = new ClassCacheMgr();

  @Test
  public void testCreateInstance() {
    UUID id = UUID.randomUUID();
    long longProp1 = 1L;
    Long longProp2 = 2L;
    int intProp1 = 3;
    Integer intProp2 = 4;
    boolean boolProp1 = false;
    Boolean boolProp2 = true;
    String strProp = "a string property";
    UUID uuidProp = UUID.randomUUID();
    Date dateProp = new Date();
    byte[] bytesProp = "somebytes".getBytes();
    String extraProp = "extra extra";
    Colors color = Colors.RED;
    MySerial serialProp = new MySerial(1, 2L);

    ColumnSliceMockImpl slice = new ColumnSliceMockImpl();
    slice.add("lp1", LongSerializer.get().toBytes(longProp1));
    slice.add("lp2", LongSerializer.get().toBytes(longProp2));
    slice.add("ip1", IntegerSerializer.get().toBytes(intProp1));
    slice.add("ip2", IntegerSerializer.get().toBytes(intProp2));
    slice.add("bp1", BooleanSerializer.get().toBytes(boolProp1));
    slice.add("bp2", BooleanSerializer.get().toBytes(boolProp2));
    slice.add("sp", StringSerializer.get().toBytes(strProp));
    slice.add("up", UUIDSerializer.get().toBytes(uuidProp));
    slice.add("dp", DateSerializer.get().toBytes(dateProp));
    slice.add("bytes", BytesArraySerializer.get().toBytes(bytesProp));
    slice.add("color", StringSerializer.get().toBytes(color.getName()));
    slice.add("serialProp", ObjectSerializer.get().toBytes(serialProp));
    slice.add("extra", StringSerializer.get().toBytes(extraProp));

    CFMappingDef<MyTestBean, UUID> cfMapDef = cacheMgr.getCfMapDef(MyTestBean.class, true);
    MyTestBean obj = new HectorObjectMapper(cacheMgr).createObject(cfMapDef, id, slice);

    assertEquals(id, obj.getBaseId());
    assertEquals(longProp1, obj.getLongProp1());
    assertEquals(longProp2, obj.getLongProp2());
    assertEquals(intProp1, obj.getIntProp1());
    assertEquals(intProp2, obj.getIntProp2());
    assertFalse(obj.isBoolProp1());
    assertTrue(obj.getBoolProp2());
    assertEquals(strProp, obj.getStrProp());
    assertEquals(uuidProp, obj.getUuidProp());
    assertEquals("somebytes", new String(obj.getBytesProp()));
    // TODO fixme
    //assertEquals(color, obj.getColor());
    assertEquals(dateProp.getTime(), obj.getDateProp().getTime());
    assertEquals(serialProp, obj.getSerialProp());
    // TODO fixme
    //assertEquals(1, obj.getAnonymousProps().size());
    assertEquals(extraProp, obj.getAnonymousProp("extra"));
  }

  @Test
  public void testCreateColumnSet() {
    MyTestBean obj = new MyTestBean();
    obj.setBaseId(UUID.randomUUID());
    obj.setLongProp1(111L);
    obj.setLongProp2(222L);
    obj.setIntProp1(333);
    obj.setIntProp2(444);
    obj.setBoolProp1(false);
    obj.setBoolProp2(true);
    obj.setStrProp("aStr");
    obj.setUuidProp(UUID.randomUUID());
    obj.setDateProp(new Date());
    obj.setBytesProp("somebytes".getBytes());
    obj.setColor(Colors.BLUE);
    obj.setSerialProp(new MySerial(1, 2L));
    obj.addAnonymousProp("foo", "bar");
    obj.addAnonymousProp("rice", "beans");

    Map<String, HColumn<String, byte[]>> colMap =
      new HectorObjectMapper(cacheMgr).createColumnMap(obj);
    // TODO fixme
    //assertEquals(11 + 2, colMap.size());
    assertNull("id should not have been added to column collection", colMap.get("id"));
    assertEquals(obj.getLongProp1(), (long)LongSerializer.get().fromBytes(colMap.get("lp1").getValue()));
    assertEquals(obj.getLongProp2(), LongSerializer.get().fromBytes(colMap.get("lp2").getValue()));
    assertEquals(obj.getIntProp1(), (int)IntegerSerializer.get().fromBytes(colMap.get("ip1").getValue()));
    assertEquals(obj.getIntProp2(), IntegerSerializer.get().fromBytes(colMap.get("ip2").getValue()));
    assertEquals(obj.isBoolProp1(), BooleanSerializer.get().fromBytes(colMap.get("bp1").getValue()));
    assertEquals(obj.getBoolProp2(), BooleanSerializer.get().fromBytes(colMap.get("bp2").getValue()));
    assertEquals(obj.getStrProp(), StringSerializer.get().fromBytes(colMap.get("sp").getValue()));
    assertEquals(obj.getUuidProp(), UUIDSerializer.get().fromBytes(colMap.get("up").getValue()));
    assertEquals(obj.getDateProp(), DateSerializer.get().fromBytes(colMap.get("dp").getValue()));
    assertEquals("somebytes", new String(BytesArraySerializer.get().fromBytes(colMap.get("bytes").getValue())));
    // TODO fixme
    //assertEquals(obj.getColor().getName(), new String(StringSerializer.get().fromBytes(
      //  colMap.get("color").getValue())));
    assertEquals(obj.getSerialProp(), ObjectSerializer.get().fromBytes(colMap.get("serialProp").getValue()));

    assertEquals(2, obj.getAnonymousProps().size());
    assertEquals(obj.getAnonymousProp("foo"), StringSerializer.get().fromBytes(colMap.get("foo").getValue()));
    assertEquals(obj.getAnonymousProp("rice"), StringSerializer.get().fromBytes(colMap.get("rice").getValue()));
  }

  @Test
  public void testCreateInstanceCustomIdType() {
    Colors id = Colors.GREEN;
    long longProp1 = 1L;

    ColumnSliceMockImpl slice = new ColumnSliceMockImpl();
    slice.add("lp1", LongSerializer.get().toBytes(longProp1));

    CFMappingDef<MyCustomIdBean, Colors> cfMapDef = cacheMgr.getCfMapDef(MyCustomIdBean.class, true);
    MyCustomIdBean obj = new HectorObjectMapper(cacheMgr).createObject(cfMapDef, id, slice);

    assertEquals(id, obj.getId());
    assertEquals(longProp1, obj.getLongProp1());
  }
  
  @Test
  public void testIsSerializable() {
    assertTrue(HectorObjectMapper.isSerializable(UUID.class));
  }
  
  @Test
  public void testIsNotSerializable() {
    assertFalse(HectorObjectMapper.isSerializable(HectorObjectMapper.class));
  }

  // --------------------

  @Before
  public void setupTest() {
    cacheMgr.initializeCacheForClass(MyTestBean.class);
    cacheMgr.initializeCacheForClass(MyCustomIdBean.class);
  }
}
