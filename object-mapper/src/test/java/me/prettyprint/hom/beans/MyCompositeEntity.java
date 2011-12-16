package me.prettyprint.hom.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.mycompany.furniture.Drawer;

@Entity
@IdClass(MyComposite2PK.class)
@Table(name = "CompositeColumnFamily")
public class MyCompositeEntity {

	@Id
	private String strProp1;
	
	@Id
	private int intProp1;

	@Column(name = "strProp2")
	private String strProp2;

	@Column(name = "strProp3")
	private String strProp3;

	@Column(name = "drawer")
	private Drawer drawer;

	public int getIntProp1() {
		return intProp1;
	}

	public void setIntProp1(int intProp1) {
		this.intProp1 = intProp1;
	}

	public String getStrProp1() {
		return strProp1;
	}

	public void setStrProp1(String strProp1) {
		this.strProp1 = strProp1;
	}

	public String getStrProp2() {
		return strProp2;
	}

	public void setStrProp2(String strProp2) {
		this.strProp2 = strProp2;
	}

	public String getStrProp3() {
		return strProp3;
	}

	public void setStrProp3(String strProp3) {
		this.strProp3 = strProp3;
	}

	public Drawer getDrawer() {
		return drawer;
	}

	public void setDrawer(Drawer drawer) {
		this.drawer = drawer;
	}

}
