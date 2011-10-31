package com.mycompany.furniture;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Drawer implements Serializable {
  private boolean pencilHolder;
  private boolean divided;
  private String description;
  
  public Drawer(boolean pencilHolder, boolean divided, String description) {
    super();
    this.pencilHolder = pencilHolder;
    this.divided = divided;
    this.description = description;
  }

  public boolean isPencilHolder() {
    return pencilHolder;
  }

  public boolean isDivided() {
    return divided;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((description == null) ? 0 : description.hashCode());
    result = prime * result + (divided ? 1231 : 1237);
    result = prime * result + (pencilHolder ? 1231 : 1237);
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Drawer other = (Drawer) obj;
    if (description == null) {
      if (other.description != null)
        return false;
    } else if (!description.equals(other.description))
      return false;
    if (divided != other.divided)
      return false;
    if (pencilHolder != other.pencilHolder)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Drawer [pencilHolder=" + pencilHolder + ", divided=" + divided + ", description="
        + description + "]";
  }
}
