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

import me.prettyprint.hector.api.exceptions.HectorException;

/**
 * Thrown when our heart beat can't renew our lock
 * 
 * @author tnine (Todd Nine)
 *
 */
public class HLockDisconnecteException extends HectorException {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * @param msg
   */
  public HLockDisconnecteException(String msg) {
    super(msg);
  }

}
