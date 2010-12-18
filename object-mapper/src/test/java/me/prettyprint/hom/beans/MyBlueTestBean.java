package me.prettyprint.hom.beans;

import me.prettyprint.hom.annotations.DiscriminatorValue;
import me.prettyprint.hom.annotations.Entity;

@Entity
@DiscriminatorValue("Blue")
public class MyBlueTestBean extends MyTestBean {

}
