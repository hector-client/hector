package me.prettyprint.cassandra.connection.security;

import java.io.IOException;
import java.net.Socket;
import java.security.PrivilegedAction;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.ietf.jgss.GSSContext;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.GSSManager;
import org.ietf.jgss.GSSName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KerberosHelper {
  
  private static Logger log = LoggerFactory.getLogger(KerberosHelper.class);

  /**
   * Log in using the service name for jaas.conf file and .keytab instead of specifying username and password
   * 
   * @param serviceName service name defined in jass.conf file
   * @return the authenticated Subject or <code>null</code> is the authentication failed
   * @throws LoginException if there is any error during the login
   */
  public static Subject loginService(String serviceName) throws LoginException {
    LoginContext loginCtx = new LoginContext(serviceName, new CallbackHandler() {
          // as we use .keytab file there is no need to specify any options in callback
          public void handle(Callback[] callbacks) throws IOException,
              UnsupportedCallbackException {
          }
        });

    loginCtx.login();
    return loginCtx.getSubject();
  }
  
  /**
   * 
   * @param serviceName service name defined in jass.conf file
   * @param username username
   * @param password password
   * @return the authenticated Subject or <code>null</code> is the authentication failed
   * @throws LoginException if there is any error during the login
   */
  public static Subject loginService(String serviceName, String username, String password) throws LoginException {
    LoginContext loginCtx = new LoginContext(serviceName, new LoginCallbackHandler(username, password));
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
   * @param servicePrincipalName
   *          Service principal name
   * 
   * @return context if authorized or null
   */
  public static GSSContext authenticateClient(final Socket socket, Subject subject, final String servicePrincipalName) {
    return Subject.doAs(subject, new PrivilegedAction<GSSContext>() {
      public GSSContext run() {
        try {
          GSSManager manager = GSSManager.getInstance();
          GSSName peerName = manager.createName(servicePrincipalName, GSSName.NT_HOSTBASED_SERVICE);
          GSSContext context = manager.createContext(peerName, null, null, GSSContext.DEFAULT_LIFETIME);

          // Loop while the context is still not established
          while (!context.isEstablished()) {
            context.initSecContext(socket.getInputStream(), socket.getOutputStream());
          }

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
  
  /**
   * Password callback handler for resolving password/usernames for a JAAS login.
   * 
   * @author patricioe (Patricio Echague - patricioe@gmail.com)
   */
  static class LoginCallbackHandler implements CallbackHandler {

    public LoginCallbackHandler() { 
      super();
    }
    
    public LoginCallbackHandler( String name, String password) { 
      super();
      this.username = name;
      this.password = password;
    }
    
    public LoginCallbackHandler( String password) { 
      super();
      this.password = password;
    }
    
    private String password;
    private String username;

    /**
     * Handles the callbacks, and sets the user/password.
     * @param callbacks the callbacks to handle
     * @throws IOException if an input or output error occurs.
     */
    public void handle( Callback[] callbacks) throws IOException, UnsupportedCallbackException {

      for ( int i=0; i<callbacks.length; i++) {
        if ( callbacks[i] instanceof NameCallback && username != null) {
          NameCallback nc = (NameCallback) callbacks[i];
          nc.setName( username);
        } 
        else if ( callbacks[i] instanceof PasswordCallback) {
          PasswordCallback pc = (PasswordCallback) callbacks[i];
          pc.setPassword( password.toCharArray());
        } 
        else {
          /*throw new UnsupportedCallbackException(
          callbacks[i], "Unrecognized Callback");*/
        }
      }
    }
    

  }
}
