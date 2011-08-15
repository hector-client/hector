package me.prettyprint.cassandra.constants;

/**
 * Defaults
 */
public class CFMetaDataDefaults {
  public final static double DEFAULT_ROW_CACHE_SIZE = 0.0;
  public final static double DEFAULT_KEY_CACHE_SIZE = 200000;
  public final static double DEFAULT_READ_REPAIR_CHANCE = 1.0;
  public final static boolean DEFAULT_REPLICATE_ON_WRITE = true;
  public final static int DEFAULT_SYSTEM_MEMTABLE_THROUGHPUT_IN_MB = 8;
  public final static int DEFAULT_ROW_CACHE_SAVE_PERIOD_IN_SECONDS = 0;
  public final static int DEFAULT_KEY_CACHE_SAVE_PERIOD_IN_SECONDS = 4 * 3600;
  public final static int DEFAULT_GC_GRACE_SECONDS = 864000;
  public final static int DEFAULT_MIN_COMPACTION_THRESHOLD = 4;
  public final static int DEFAULT_MAX_COMPACTION_THRESHOLD = 32;
  public final static int DEFAULT_MEMTABLE_LIFETIME_IN_MINS = 60 * 24;
  public final static double DEFAULT_MERGE_SHARDS_CHANCE = 0.1;
  //this defaults to ram / 16 / 1MB on the server
  //but we are on the client, so we don't know how much 
  //ram is on the server, assume a conservative 8G
  public final static int DEFAULT_MEMTABLE_THROUGHPUT_IN_MB = 500;
  public final static double DEFAULT_MEMTABLE_OPERATIONS_IN_MILLIONS = 
      sizeMemtableOperations(DEFAULT_MEMTABLE_THROUGHPUT_IN_MB);
  
  
  private static double sizeMemtableOperations(int mem_throughput)
  {
      return 0.3 * mem_throughput / 64.0;
  }
  
}
