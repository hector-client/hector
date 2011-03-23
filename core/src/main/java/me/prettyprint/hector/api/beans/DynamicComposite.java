package me.prettyprint.hector.api.beans;

public class DynamicComposite extends AbstractComposite {

  public DynamicComposite() {
    super(true);
  }

  public DynamicComposite(Object... o) {
    super(true, o);
  }

}
