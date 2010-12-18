package com.mycompany.furniture;

import me.prettyprint.hom.annotations.Column;
import me.prettyprint.hom.annotations.DiscriminatorValue;
import me.prettyprint.hom.annotations.Entity;

@Entity
@DiscriminatorValue("chair")
public class Chair extends Furniture {

    @Column("recliner")
    private boolean recliner;
    
    @Column("arms")
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
