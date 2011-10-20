package me.prettyprint.cassandra.connection.security;

import java.io.IOException;
import java.net.Socket;
import java.security.PrivilegedAction;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.ietf.jgss.*;

public class KerberosHelper {

  public static Subject loginService(String serviceName) throws LoginException {
    LoginContext loginCtx = new LoginContext(serviceName,
        new CallbackHandler() {
          // as we use .keytab file there is no need to specify any options in
          // callback
          public void handle(Callback[] callbacks) throws IOException,
              UnsupportedCallbackException {
          }
        });

    loginCtx.login();

    return loginCtx.getSubject();
  }

  /**
   * Authenticate client to use this service and return secure context
   * 
   * @param socket
   *          The socket used for communication
   * @param subject
   *          The Kerberos service subject
   * 
   * @return context if authorized or null
   */
  public static GSSContext authenticateClient(final Socket socket, Subject subject) {
    return Subject.doAs(subject, new PrivilegedAction<GSSContext>() {
      public GSSContext run() {
        try {
          GSSManager manager = GSSManager.getInstance();
          GSSContext context = manager.createContext((GSSCredential) null);

          while (!context.isEstablished())
            context.acceptSecContext(socket.getInputStream(), socket.getOutputStream());

          return context;
        } catch (Exception e) {
          e.printStackTrace();
          return null;
        }
      }
    });
  }

  public static String getSourcePrinciple(GSSContext context) {
    try {
      return context.getSrcName().toString();
    } catch (GSSException e) {
      throw new RuntimeException(e);
    }
  }
}
