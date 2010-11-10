package me.prettyprint.cassandra.service;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Resolution used to create clocks.
 * Clients may wish to use seconds, milliseconds, microseconds or synchronized microseconds,
 * depending on the application needs and existing data and other tools, so Hector makes that
 * configurable.
 * Synchronized microseconds(MICROSECONDS_SYNC) guarantees unique clock within and across threads.
 *
 * @author Ran Tavory (rantav@gmail.com)
 * @author Patricio Echague (pechague@gmail.com)
 */
public enum ClockResolution {
  SECONDS, MILLISECONDS, MICROSECONDS, MICROSECONDS_SYNC;

  private static final long ONE_THOUSAND = 1000L;

  /** The last time value issued. Used to try to prevent duplicates. Valid only with MICROSECONDS_SYNC*/
  private static long lastTime = -1;

  static {
	  synchronized (ClockResolution.class) {
		  lastTime = System.currentTimeMillis() * ONE_THOUSAND;
	}
  }

  public long createClock() {
    switch (this) {
	    case MICROSECONDS:
	    	return System.currentTimeMillis() * ONE_THOUSAND;
	    case MICROSECONDS_SYNC:
	      // The following simulates a microseconds resolution by advancing a static counter
	      // every time a client calls the createClock method, simulating a tick.
	      long us = System.currentTimeMillis() * ONE_THOUSAND;
	      // Synchronized to guarantee unique time within and across threads.
	      synchronized (ClockResolution.class) {
	         if (us > lastTime) {
	        	 lastTime = us;
	         } else {
	        	 // the time i got from the system is equals or less
	        	 // (hope not - clock going backwards)
	        	 // One more "microsecond"
	        	 us = ++lastTime;
	         }
	      }
	      return us;
	    case MILLISECONDS:
	      return System.currentTimeMillis();
	    case SECONDS:
	      return System.currentTimeMillis() / 1000;
	}

    return System.currentTimeMillis();
  }
}
