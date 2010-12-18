package me.prettyprint.hom.beans;

import me.prettyprint.hom.annotations.Column;
import me.prettyprint.hom.annotations.DiscriminatorValue;
import me.prettyprint.hom.annotations.Entity;

@Entity
@DiscriminatorValue("Red")
public class MyRedTestBean extends MyTestBean {
    
    @Column("myCol1")
    private int c1;

    public int getC1() {
        return c1;
    }

    public void setC1(int c1) {
        this.c1 = c1;
    }
    
    

}
