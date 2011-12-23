package me.prettyprint.hom.beans;

import java.io.Serializable;

/**
 * Used with @IdClass. Properties of this class must match @Id properties
 * defined by the entity.
 * 
 * @author B. Todd Burruss
 */
@SuppressWarnings("serial")
public class MyComposite2PK implements Serializable {

	private String strProp1;
	private int intProp1;

	public MyComposite2PK() {
	}

	public MyComposite2PK(String strProp1, int intProp1) {
		this.intProp1 = intProp1;
		this.strProp1 = strProp1;
	}

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
}
