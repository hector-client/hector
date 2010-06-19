package me.prettyprint.cassandra.model;

/*package*/ class ColumnImpl implements Column {

  public ColumnImpl(org.apache.cassandra.thrift.Column thriftColumn) {
    // TODO Auto-generated constructor stub
  }

  public ColumnImpl(org.apache.cassandra.avro.Column avroColumn) {
    // TODO Auto-generated constructor stub
  }

  @Override
  public Value getName() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public long getTimestamp() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public Value getValue() {
    // TODO Auto-generated method stub
    return null;
  }

}
