package me.prettyprint.cassandra.connection;

/**
 * Utilitary class to format hosts and urls.
 * 
 * @author patricioe (Patricio Echague - patricio@datastax.com)
 *
 */
public final class HostUtils {

  public static String parseHostFromUrl(String urlPort) {
    return urlPort.lastIndexOf(':') > 0 ? urlPort.substring(0,
        urlPort.lastIndexOf(':')) : urlPort;
  }

  public static int parsePortFromUrl(String urlPort) {
    return urlPort.lastIndexOf(':') > 0 ? Integer.valueOf(urlPort.substring(
        urlPort.lastIndexOf(':') + 1, urlPort.length())) : HCassandraHost.DEFAULT_PORT;
  }

}
