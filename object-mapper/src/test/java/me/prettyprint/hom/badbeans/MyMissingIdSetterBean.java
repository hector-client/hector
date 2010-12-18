package me.prettyprint.hom.badbeans;

import me.prettyprint.hom.annotations.Column;
import me.prettyprint.hom.annotations.Entity;
import me.prettyprint.hom.annotations.Id;
import me.prettyprint.hom.annotations.Table;

@Entity
@Table("MyMissingIdSetterBean")
public class MyMissingIdSetterBean {
    @Id
    private String id;
    
    @Column("lp1")
    private long longProp1;

    public long getLongProp1() {
        return longProp1;
    }

    public void setLongProp1(long longProp1) {
        this.longProp1 = longProp1;
    }

}
