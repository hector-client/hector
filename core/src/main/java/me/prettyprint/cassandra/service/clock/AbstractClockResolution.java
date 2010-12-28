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
package me.prettyprint.cassandra.service.clock;

/**
 * Define common functionaly for ClockResolution Implementations.
 *
 * @author pechague
 *
 */
public abstract class AbstractClockResolution {


    protected long getSystemMilliseconds() {
      return System.currentTimeMillis();
    }

}
