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

import org.ietf.jgss.GSSContext;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.GSSManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KerberosHelper {
  
  private static Logger log = LoggerFactory.getLogger(KerberosHelper.class);

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
          log.error("Unable to authenticate client against Kerberos", e);
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
