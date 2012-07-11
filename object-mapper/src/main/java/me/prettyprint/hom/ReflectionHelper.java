package me.prettyprint.hom;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import me.prettyprint.hom.cache.HectorObjectMapperException;

public class ReflectionHelper {


  public Object invokeGetter(Object obj, PropertyMappingDefinition md) {
    PropertyDescriptor pd = md.getPropDesc();

    Method getter = pd.getReadMethod();
    if (null == getter) {
      throw new RuntimeException("missing getter method for property, " + pd.getName());
    }

    try {
      return getter.invoke(obj, (Object[]) null);
    } catch (Throwable e) {
      throw new HectorObjectMapperException("exception while invoking getter on object of type, "
          + obj.getClass().getName(), e);
    }
  }

}

