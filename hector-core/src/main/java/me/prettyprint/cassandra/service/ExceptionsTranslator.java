package me.prettyprint.cassandra.service;

import me.prettyprint.hector.api.exceptions.HectorException;

/**
 * Translates exceptions throw by thrift or pool to HectorException instances.
 * 
 * @author Ran Tavory (ran@outbrain.com)
 *
 */
public interface ExceptionsTranslator {
  
  HectorException translate(Throwable originalException);
    
}
