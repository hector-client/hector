package me.prettyprint.hom.cache;

import static org.junit.Assert.fail;

import javax.persistence.InheritanceType;

import me.prettyprint.hom.CFMappingDef;
import me.prettyprint.hom.ClassCacheMgr;
import me.prettyprint.hom.beans.MyBlueTestBean;
import me.prettyprint.hom.beans.MyTestBean;

import org.junit.Test;

public class InheritanceParserValidatorTest {

  @Test
  public void testValidate() {
    CFMappingDef<MyTestBean> cfMapDef = new CFMappingDef<MyTestBean>(MyTestBean.class);
    cfMapDef.setInheritanceType(InheritanceType.SINGLE_TABLE);
    cfMapDef.setDiscColumn("myType");
    cfMapDef.setDiscValue("blue");

    InheritanceParserValidator val = new InheritanceParserValidator();
    try {
      val.validateAndSetDefaults(null, cfMapDef);
    } catch (HectorObjectMapperException e) {
      fail(e.getMessage());
    }
  }

  @Test(expected = HectorObjectMapperException.class)
  public void testValidateInvalidInheritanceType() {
    CFMappingDef<MyTestBean> cfMapDef = new CFMappingDef<MyTestBean>(MyTestBean.class);
    cfMapDef.setInheritanceType(InheritanceType.JOINED);

    InheritanceParserValidator val = new InheritanceParserValidator();
    val.validateAndSetDefaults(null, cfMapDef);
    fail("should have found invalid inheritance type");
  }

  @Test(expected = HectorObjectMapperException.class)
  public void testValidateMissingDiscriminatorColumn() {
    CFMappingDef<MyTestBean> cfMapDef = new CFMappingDef<MyTestBean>(MyTestBean.class);
    cfMapDef.setInheritanceType(InheritanceType.SINGLE_TABLE);

    InheritanceParserValidator val = new InheritanceParserValidator();
    val.validateAndSetDefaults(null, cfMapDef);
    fail("should have reported missing discriminator column annotation");
  }

  @Test(expected = HectorObjectMapperException.class)
  public void testValidateMissingDiscriminatorValue() {
    CFMappingDef<MyTestBean> cfMapDef = new CFMappingDef<MyTestBean>(MyTestBean.class);
    cfMapDef.setInheritanceType(InheritanceType.SINGLE_TABLE);
    cfMapDef.setDiscColumn("myType");

    InheritanceParserValidator val = new InheritanceParserValidator();
    val.validateAndSetDefaults(null, cfMapDef);
    fail("should have reported missing discriminator value annotation");
  }

  public void testValidateDerivedClass() {
    CFMappingDef<MyTestBean> cfBaseMapDef = new CFMappingDef<MyTestBean>(
        MyTestBean.class);
    cfBaseMapDef.setInheritanceType(InheritanceType.SINGLE_TABLE);
    cfBaseMapDef.setDiscColumn("myType");

    CFMappingDef<MyBlueTestBean> cfMapDef = new CFMappingDef<MyBlueTestBean>(
        MyBlueTestBean.class);
    cfMapDef.setCfBaseMapDef(cfBaseMapDef);
    cfMapDef.setDiscValue("blue");

    InheritanceParserValidator val = new InheritanceParserValidator();
    try {
      val.validateAndSetDefaults(null, cfMapDef);
    } catch (HectorObjectMapperException e) {
      fail(e.getMessage());
    }
  }

  @Test(expected = HectorObjectMapperException.class)
  public void testValidateDerivedClassMissingDiscriminatorValue() {
    ClassCacheMgr cacheMgr = new ClassCacheMgr();
    CFMappingDef<MyTestBean> cfBaseMapDef = new CFMappingDef<MyTestBean>(
        MyTestBean.class);
    cfBaseMapDef.setInheritanceType(InheritanceType.SINGLE_TABLE);
    cfBaseMapDef.setDiscColumn("myType");

    CFMappingDef<MyBlueTestBean> cfMapDef = new CFMappingDef<MyBlueTestBean>(
        MyBlueTestBean.class);
    cfMapDef.setCfSuperMapDef(cfBaseMapDef);
    cfMapDef.setCfBaseMapDef(cfBaseMapDef);

    InheritanceParserValidator val = new InheritanceParserValidator();
    val.validateAndSetDefaults(cacheMgr, cfMapDef);
    fail("should have reported missing discriminator value annotation");
  }

}
