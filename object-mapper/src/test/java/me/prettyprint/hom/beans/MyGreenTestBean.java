package me.prettyprint.hom.beans;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Green")
public class MyGreenTestBean extends MyAbstractGreenTestBean {

}
