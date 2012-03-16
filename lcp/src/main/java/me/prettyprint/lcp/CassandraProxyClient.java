/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.prettyprint.lcp;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.cassandra.config.KSMetaData;
import org.apache.cassandra.thrift.*;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.transport.*;


/**
 * This wraps the underlying Cassandra thrift client and attempts to handle
 * disconnect, unavailable, timeout errors gracefully.
 * <p/>
 * On disconnect, if it cannot reconnect to the same host then it will use a
 * different host from the ring. After a successful connecting, the ring will be
 * refreshed.
 * <p/>
 * This incorporates the CircuitBreaker pattern so not to overwhelm the network
 * with reconnect attempts.
 */
public class CassandraProxyClient implements java.lang.reflect.InvocationHandler {

  private static final Logger logger = Logger.getLogger(CassandraProxyClient.class);

  public enum ConnectionStrategy {
    RANDOM, ROUND_ROBIN, STICKY
  }

  private String host;
  private int port;

  private Cassandra.Client client;

  /**
   * Build the Cassandra.Iface proxy
   *
   * @param host   cassandra host
   * @param port   cassandra port
   * @return a Casandra.Client Interface
   * @throws IOException
   */
  public static Cassandra.Iface newProxyConnection(String host, int port)
          throws IOException {
    return (Cassandra.Iface) java.lang.reflect.Proxy.newProxyInstance(Cassandra.Client.class.getClassLoader(),
            Cassandra.Client.class.getInterfaces(), new CassandraProxyClient(host, port));
  }

  /**
   * Create connection to a given host.
   *
   * @return cassandra thrift client
   * @throws IOException error
   */
  public Cassandra.Client createConnection() throws IOException {
    TSocket socket = new TSocket(host, port, 10000);
    TTransport trans;
    try {
      socket.getSocket().setKeepAlive(true);
      socket.getSocket().setSoLinger(false,0);
      socket.getSocket().setTcpNoDelay(true);

      trans = new TFastFramedTransport(socket);
      trans.open();

      return new Cassandra.Client(new TBinaryProtocol(trans));

    } catch (Exception e) {
      throw new IOException("unable to connect to server", e);
    }

  }

  private CassandraProxyClient(String host, int port) throws IOException {
    this.host = host;
    this.port = port;
  }



  public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
    Object result = null;
    try {
      this.client = createConnection();
      result = m.invoke(client, args);

      return result;

    } catch (Exception e) {
      logger.error("Error invoking a method via proxy: ", e);
      throw new RuntimeException(e);
    }

  }


}

