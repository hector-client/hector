/*******************************************************************************
 * Copyright 2012 Apigee Corporation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package me.prettyprint.hector.api.locking;

/**
 * Observer interface for lock events
 * @author tnine
 *
 */
public interface HLockObserver {

  /**
   * We timed out waiting for the lock
   * @param lock
   */
  public void timeout(HLock lock);
  
  /**
   * The lock is acquired
   * @param lock
   */
  public void acquired(HLock lock);
  
  /**
   * The lock is released
   * @param lock
   */
  public void release(HLock lock);
  
  /**
   * The lock was lost/disconnected.  This should be treated as a fail state, and the lock should be re-acquired
   * @param lock
   */
  public void disconnected(HLock lock);
  
}
