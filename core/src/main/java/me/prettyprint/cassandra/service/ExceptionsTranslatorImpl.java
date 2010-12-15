package me.prettyprint.cassandra.service;

import java.util.NoSuchElementException;

import me.prettyprint.hector.api.exceptions.HCassandraInternalException;
import me.prettyprint.hector.api.exceptions.HInvalidRequestException;
import me.prettyprint.hector.api.exceptions.HNotFoundException;
import me.prettyprint.hector.api.exceptions.HTimedOutException;
import me.prettyprint.hector.api.exceptions.HUnavailableException;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.exceptions.HectorTransportException;
import me.prettyprint.hector.api.exceptions.PoolExhaustedException;
import me.prettyprint.hector.api.exceptions.PoolIllegalStateException;

import org.apache.thrift.TApplicationException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.transport.TTransportException;

public final class ExceptionsTranslatorImpl implements ExceptionsTranslator {


  @Override
  public HectorException translate(Throwable original) {
    if (original instanceof HectorException) {
      return (HectorException) original;
    } else if (original instanceof TApplicationException) {
      return new HCassandraInternalException(((TApplicationException)original).getType(), original.getMessage());
    } else if (original instanceof TException || original instanceof TTransportException) {
      return new HectorTransportException(original);
    } else if (original instanceof org.apache.cassandra.thrift.TimedOutException) {
      return new HTimedOutException(original);
    } else if (original instanceof org.apache.cassandra.thrift.InvalidRequestException) {
      // See bug https://issues.apache.org/jira/browse/CASSANDRA-1862
      // If a bootstrapping node is accessed it responds with IRE. It makes more sense to throw
      // UnavailableException.
      // Hector wraps this caveat and fixes this.
      String why = ((org.apache.cassandra.thrift.InvalidRequestException) original).getWhy();
      if (why != null && why.contains("bootstrap")) {
        return new HUnavailableException(original);
      }
      HInvalidRequestException e = new HInvalidRequestException(original);
      e.setWhy(why);
      return e;
    } else if (original instanceof TProtocolException) {
      return new HInvalidRequestException(original);
    } else if (original instanceof org.apache.cassandra.thrift.NotFoundException) {
      return new HNotFoundException(original);
    } else if (original instanceof org.apache.cassandra.thrift.UnavailableException) {
      return new HUnavailableException(original);
    } else if (original instanceof NoSuchElementException) {
      return new PoolExhaustedException(original);
    } else if (original instanceof IllegalStateException) {
      return new PoolIllegalStateException(original);
    } else {
      return new HectorException(original);
    }
  }

}
