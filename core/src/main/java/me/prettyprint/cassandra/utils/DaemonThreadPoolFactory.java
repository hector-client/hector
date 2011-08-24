package me.prettyprint.cassandra.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class DaemonThreadPoolFactory implements ThreadFactory {

  private ConcurrentHashMap<String, AtomicInteger> counters = 
      new ConcurrentHashMap<String, AtomicInteger>();
  
  private final String name;

  public DaemonThreadPoolFactory(Class<?> parentClass) {
    this.name = "Hector." + parentClass.getName();
  }

  private int getNextThreadNumber() {
    if(!counters.containsKey(name)) {
      counters.putIfAbsent(name, new AtomicInteger());
    }
    return counters.get(name).incrementAndGet();
  }
  
  @Override
  public Thread newThread(Runnable r) {
      Thread t = new Thread(r);
      t.setDaemon(true);
      t.setName(name + "-" + getNextThreadNumber());
      return t;
  }
    
}