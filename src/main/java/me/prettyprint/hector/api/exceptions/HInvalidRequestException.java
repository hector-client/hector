package me.prettyprint.hector.api.exceptions;


/**
 * @author Ran Tavory (rantav@gmail.com)
 */
public final class HInvalidRequestException extends HectorException {

  private static final long serialVersionUID = 7186392651338685069L;

  private String why;

  public HInvalidRequestException(String msg) {
    super(msg);
    setWhy(msg);
  }
  public HInvalidRequestException(Throwable t) {
    super(t);
  }

  public void setWhy(String w) {
    why = w;
  }

  public String getWhy() {
    return why;
  }
}
