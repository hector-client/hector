package me.prettyprint.hom.cache;

import java.lang.annotation.Annotation;

import me.prettyprint.hom.CFMappingDef;
import me.prettyprint.hom.ClassCacheMgr;

public interface ParserValidator {

  <T> void parse(ClassCacheMgr cacheMgr, Annotation anno, CFMappingDef<T> cfMapDef);

  <T> void validateAndSetDefaults(ClassCacheMgr cacheMgr, CFMappingDef<T> cfMapDef);
  
}
