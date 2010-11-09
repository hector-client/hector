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
package me.prettyprint.cassandra.service;


import static org.junit.Assert.fail;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test @link {@link ClockResolutionTest}
 * @author Patricio Echague (patricioe@gmail.com)
 *
 */
public class ClockResolutionTest {

	private static final Logger log = LoggerFactory.getLogger(ClockResolutionTest.class);

	/**
	 * Test that multiple calls do not generate the same clock (timestamp)
	 */
	@Test
	public void testMicrosecondsSync() throws Exception {
		long previous = ClockResolution.MICROSECONDS_SYNC.createClock();
		for (int i = 0; i < 50; i++) {
			long current = ClockResolution.MICROSECONDS_SYNC.createClock();
			log.debug("previous=" + previous + " - current=" +  current);
			if (current == previous) {
				fail("Two calls to clock generated the same timestamp. (previous="
						+ previous + " - current=" +  current + ")");
			}
			current = previous;

		}
	}

}
