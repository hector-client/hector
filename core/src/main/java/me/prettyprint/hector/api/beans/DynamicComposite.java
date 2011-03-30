package me.prettyprint.hector.api.beans;

import java.nio.ByteBuffer;

public class DynamicComposite extends AbstractComposite {

  public DynamicComposite() {
    super(true);
  }

  public DynamicComposite(Object... o) {
    super(true, o);
  }

  public static DynamicComposite fromByteBuffer(ByteBuffer byteBuffer) {

    DynamicComposite composite = new DynamicComposite();
    composite.deserialize(byteBuffer);

    return composite;
  }
}
