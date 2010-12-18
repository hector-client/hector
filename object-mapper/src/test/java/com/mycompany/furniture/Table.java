package com.mycompany.furniture;

import me.prettyprint.hom.annotations.Column;
import me.prettyprint.hom.annotations.DiscriminatorValue;
import me.prettyprint.hom.annotations.Entity;

@Entity
@DiscriminatorValue("table")
public class Table extends Furniture {

    @Column("extendable")
    private boolean extendable;
    
    @Column("shape")
    private String shape;

    public boolean isExtendable() {
        return extendable;
    }

    public void setExtendable(boolean extendable) {
        this.extendable = extendable;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }
    
}
