package me.prettyprint.hector.api.beans;

import java.nio.ByteBuffer;

public class Composite extends AbstractComposite {

  public Composite() {
    super(false);
  }

  public Composite(Object... o) {
    super(false, o);
  }

  public static Composite fromByteBuffer(ByteBuffer byteBuffer) {

    Composite composite = new Composite();
    composite.deserialize(byteBuffer);

    return composite;
  }

  public static ByteBuffer toByteBuffer(Object... o) {
    Composite composite = new Composite(o);
    return composite.serialize();
  }

}
