package me.prettyprint.hom.beans;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("Blue")
public class MyBlueTestBean extends MyTestBean {

}
