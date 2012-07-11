package com.mycompany.furniture;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("chair")
public class Chair extends Furniture {

  @Column(name="recliner")
  private boolean recliner;

  @Column(name="arms")
  private boolean arms;

  public boolean isRecliner() {
    return recliner;
  }

  public void setRecliner(boolean recliner) {
    this.recliner = recliner;
  }

  public boolean isArms() {
    return arms;
  }

  public void setArms(boolean arms) {
    this.arms = arms;
  }
}
