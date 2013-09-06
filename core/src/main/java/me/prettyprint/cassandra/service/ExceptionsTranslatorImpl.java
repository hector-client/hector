package me.prettyprint.cassandra.service;

import java.net.SocketTimeoutException;
import java.util.NoSuchElementException;

import me.prettyprint.hector.api.exceptions.HCassandraInternalException;
import me.prettyprint.hector.api.exceptions.HInvalidRequestException;
import me.prettyprint.hector.api.exceptions.HNotFoundException;
import me.prettyprint.hector.api.exceptions.HPoolExhaustedException;
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
    HectorException he;
    if (original instanceof HectorException) {
      he = (HectorException) original;
    } else if (original instanceof TApplicationException) {
      he = new HCassandraInternalException(((TApplicationException)original).getType(), original.getMessage());
    } else if (original instanceof TTransportException) {
    	// if the underlying cause is a scoket timeout, reflect that directly
    	// TODO this may be an issue on the Cassandra side which warrants investigation.
    	// I seem to remember these coming back as TimedOutException previously
    	if (original.getCause() instanceof SocketTimeoutException) {
    		he = new HTimedOutException(original);
    	} else {
    		he = new HectorTransportException(original);
    	}
    } else if (original instanceof org.apache.cassandra.thrift.TimedOutException) {
      he = new HTimedOutException(original);
    } else if (original instanceof org.apache.cassandra.thrift.InvalidRequestException) {
      // See bug https://issues.apache.org/jira/browse/CASSANDRA-1862
      // If a bootstrapping node is accessed it responds with IRE. It makes more sense to throw
      // UnavailableException.
      // Hector wraps this caveat and fixes this.
      String why = ((org.apache.cassandra.thrift.InvalidRequestException) original).getWhy();
      if (why != null && why.contains("bootstrap")) {
        he = new HUnavailableException(original);
      } else {
        HInvalidRequestException e = new HInvalidRequestException(original);
        e.setWhy(why);
        he = e;
      }
    } else if (original instanceof TProtocolException) {
      he = new HInvalidRequestException(original);
    } else if (original instanceof org.apache.cassandra.thrift.NotFoundException) {
      he = new HNotFoundException(original);
    } else if (original instanceof org.apache.cassandra.thrift.UnavailableException) {
      he = new HUnavailableException(original);
    } else if (original instanceof TException) {
      he = new HectorTransportException(original);
    } else if (original instanceof NoSuchElementException) {
      he = new HPoolExhaustedException(original);
    } else if (original instanceof IllegalStateException) {
      he = new PoolIllegalStateException(original);
    } else {
      he = new HectorException(original);
    }
    he.setHost(host);
    return he;
  }

}
