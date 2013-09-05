package me.prettyprint.cassandra.service;

import java.net.SocketTimeoutException;
import java.util.NoSuchElementException;

import me.prettyprint.hector.api.exceptions.HCassandraInternalException;
import me.prettyprint.hector.api.exceptions.HInactivePoolException;
import me.prettyprint.hector.api.exceptions.HInvalidRequestException;
import me.prettyprint.hector.api.exceptions.HNotFoundException;
import me.prettyprint.hector.api.exceptions.HPoolExhaustedException;
import me.prettyprint.hector.api.exceptions.HPoolRecoverableException;
import me.prettyprint.hector.api.exceptions.HTimedOutException;
import me.prettyprint.hector.api.exceptions.HUnavailableException;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.exceptions.HectorTransportException;
import me.prettyprint.hector.api.exceptions.PoolIllegalStateException;

import org.apache.thrift.TApplicationException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.transport.TTransportException;

public final class ExceptionsTranslatorImpl implements ExceptionsTranslator {


  @Override
  public HectorException translate(Throwable original) {
    return translate(original, null);
  }

  @Override
  public HectorException translate(Throwable original, CassandraHost host) {
    if (original instanceof HectorException) {
      return setHost((HectorException) original, host);
    } else if (original instanceof TApplicationException) {
      return new HCassandraInternalException(((TApplicationException)original).getType(), original.getMessage());    
    } else if (original instanceof TTransportException) {
    	// if the underlying cause is a scoket timeout, reflect that directly
    	// TODO this may be an issue on the Cassandra side which warrants investigation.
    	// I seem to remember these coming back as TimedOutException previously
    	if (((TTransportException) original).getCause() instanceof SocketTimeoutException) {
    		return setHost(new HTimedOutException(original), host);
    	} else {
    		return setHost(new HectorTransportException(original), host);
    	}
    } else if (original instanceof org.apache.cassandra.thrift.TimedOutException) {
      return setHost(new HTimedOutException(original), host);
    } else if (original instanceof org.apache.cassandra.thrift.InvalidRequestException) {
      // See bug https://issues.apache.org/jira/browse/CASSANDRA-1862
      // If a bootstrapping node is accessed it responds with IRE. It makes more sense to throw
      // UnavailableException.
      // Hector wraps this caveat and fixes this.
      String why = ((org.apache.cassandra.thrift.InvalidRequestException) original).getWhy();
      if (why != null && why.contains("bootstrap")) {
        return setHost(new HUnavailableException(original), host);
      }
      HInvalidRequestException e = new HInvalidRequestException(original);
      e.setWhy(why);
      return setHost(e, host);
    } else if (original instanceof HPoolExhaustedException ) {
      return setHost((HPoolExhaustedException) original, host);
    } else if (original instanceof HPoolRecoverableException ) {
      return setHost((HPoolRecoverableException) original, host);
    } else if (original instanceof HInactivePoolException ) {
      return setHost((HInactivePoolException) original, host);
    } else if (original instanceof TProtocolException) {
      return setHost(new HInvalidRequestException(original), host);
    } else if (original instanceof org.apache.cassandra.thrift.NotFoundException) {
      return setHost(new HNotFoundException(original), host);
    } else if (original instanceof org.apache.cassandra.thrift.UnavailableException) {
      return setHost(new HUnavailableException(original), host);
    } else if (original instanceof TException) {
      return setHost(new HectorTransportException(original), host);
    } else if (original instanceof NoSuchElementException) {
      return setHost(new HPoolExhaustedException(original), host);
    } else if (original instanceof IllegalStateException) {
      return setHost(new PoolIllegalStateException(original), host);
    } else {
      return setHost(new HectorException(original), host);
    }
  }

  private HectorException setHost(HectorException he, CassandraHost host) {
    he.setHost(host);
    return he;
  }

}
