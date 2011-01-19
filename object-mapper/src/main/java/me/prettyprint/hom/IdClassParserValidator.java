package me.prettyprint.hom;

import java.lang.annotation.Annotation;

import me.prettyprint.hom.cache.ParserValidator;

public class IdClassParserValidator implements ParserValidator {

  @Override
  public <T, I> void parse(ClassCacheMgr cacheMgr, Annotation anno, CFMappingDef<T, I> cfMapDef) {
    // TODO Auto-generated method stub

  }

  @Override
  public <T, I> void validateAndSetDefaults(ClassCacheMgr cacheMgr, CFMappingDef<T, I> cfMapDef) {
    // TODO Auto-generated method stub

  }

}
