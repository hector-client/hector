/*
 * Copyright (c) 2009 Openwave Systems Inc. All rights reserved.
 *
 * The copyright to the computer software herein is the property of
 * Openwave Systems Inc. The software may be used and/or copied only
 * with the written permission of Openwave Systems Inc. or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the software has been supplied.
 *
 * $Id: $
 */
package me.prettyprint.hector.api;


import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import org.junit.Test;

import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.clock.MicrosecondsClockResolution;
import me.prettyprint.cassandra.service.clock.MicrosecondsSyncClockResolution;
import me.prettyprint.cassandra.service.clock.MillisecondsClockResolution;
import me.prettyprint.cassandra.service.clock.SecondsClockResolution;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.factory.HFactory;

/**
 * @author Patricio Echague (patricioe@gmail.com)
 *
 */
public class HFactoryTest {

  @Test
  public void testCreateClockResolution() throws Exception {
    try {
      HFactory.createClockResolution("ItDoesNotExist");
      fail();
    } catch (RuntimeException e) {
        // good!
    }

    assertTrue(HFactory.createClockResolution(ClockResolution.SECONDS) instanceof SecondsClockResolution);
    assertTrue(HFactory.createClockResolution(ClockResolution.MILLISECONDS) instanceof MillisecondsClockResolution);
    assertTrue(HFactory.createClockResolution(ClockResolution.MICROSECONDS) instanceof MicrosecondsClockResolution);
    assertTrue(HFactory.createClockResolution(ClockResolution.MICROSECONDS_SYNC)
            instanceof MicrosecondsSyncClockResolution);
  }
  
  @Test
  public void testCreateColumn() {
	  long clock = HFactory.createClock();
	  HColumn<String, Long> col = HFactory.createColumn("nameString", new Long("345"), clock);
	  HColumn<String, Long> col2 = HFactory.createColumn("nameString", new Long("345"), clock, StringSerializer.get(), LongSerializer.get());
	  assertEquals(col.getName(), col2.getName());
	  assertEquals(col.getValue(), col2.getValue());
	  assertEquals(col.getClock(), col2.getClock());
  }
}
