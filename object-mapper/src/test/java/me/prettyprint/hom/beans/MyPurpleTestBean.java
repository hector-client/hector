package me.prettyprint.hom.beans;

import me.prettyprint.hom.annotations.Column;
import me.prettyprint.hom.annotations.Entity;
import me.prettyprint.hom.annotations.Id;
import me.prettyprint.hom.annotations.Table;

@Entity
@Table("PurpleColumnFamily")
public class MyPurpleTestBean extends MyNonEntityTestBean {
    @Id
    private String id;
    
    @Column("lp1")
    private long longProp1;
    
    @Column("lp2")
    private long longProp2;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
    
    public long getLongProp1() {
        return longProp1;
    }

    public void setLongProp1(long longProp1) {
        this.longProp1 = longProp1;
    }

    public long getLongProp2() {
        return longProp2;
    }

    public void setLongProp2(long longProp2) {
        this.longProp2 = longProp2;
    }

}
