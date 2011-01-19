package me.prettyprint.hom.cache;

import java.lang.annotation.Annotation;

import me.prettyprint.hom.CFMappingDef;
import me.prettyprint.hom.ClassCacheMgr;

public interface ParserValidator {

  <T, I> void parse(ClassCacheMgr cacheMgr, Annotation anno, CFMappingDef<T, I> cfMapDef);

  <T, I> void validateAndSetDefaults(ClassCacheMgr cacheMgr, CFMappingDef<T, I> cfMapDef);
  
}
