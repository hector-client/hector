package me.prettyprint.cassandra.service;

import java.util.NoSuchElementException;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;

import me.prettyprint.cassandra.model.HectorException;
import me.prettyprint.cassandra.model.HectorTransportException;
import me.prettyprint.cassandra.model.InvalidRequestException;
import me.prettyprint.cassandra.model.NotFoundException;
import me.prettyprint.cassandra.model.PoolExhaustedException;
import me.prettyprint.cassandra.model.PoolIllegalStateException;
import me.prettyprint.cassandra.model.TimedOutException;
import me.prettyprint.cassandra.model.UnavailableException;

public class ExceptionsTranslatorImpl implements ExceptionsTranslator {

  @Override
  public HectorException translate(Throwable original) {
    if (original instanceof HectorException) {
      return (HectorException) original;
    } else if (original instanceof TException || original instanceof TTransportException) {
      return new HectorTransportException(original);
    } else if (original instanceof org.apache.cassandra.thrift.TimedOutException) {
      return new TimedOutException(original);
    } else if (original instanceof org.apache.cassandra.thrift.InvalidRequestException) {
      InvalidRequestException e = new InvalidRequestException(original);
      e.setWhy(((org.apache.cassandra.thrift.InvalidRequestException) original).getWhy());
      return e;
    } else if (original instanceof org.apache.cassandra.thrift.NotFoundException) {
      return new NotFoundException(original);
    } else if (original instanceof org.apache.cassandra.thrift.TimedOutException) {
      return new TimedOutException(original);
    } else if (original instanceof org.apache.cassandra.thrift.UnavailableException) {
      return new UnavailableException(original);
    } else if (original instanceof NoSuchElementException) {
      return new PoolExhaustedException(original);
    } else if (original instanceof IllegalStateException) {
      return new PoolIllegalStateException(original);
    } else {
      return new HectorException(original);
    }
  }

}
