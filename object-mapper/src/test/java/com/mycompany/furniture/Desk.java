package com.mycompany.furniture;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import me.prettyprint.hom.ObjectConverter;

@Entity
@DiscriminatorValue("table_desk")
public class Desk extends BasicTable {

  @Column(name = "desk_type")
  private String deskType;

  @me.prettyprint.hom.annotations.Column(name = "drawerList")
  private List<Drawer> drawerList = new ArrayList<Drawer>();
  
  @me.prettyprint.hom.annotations.Column(name = "oneColumnDrawerList", converter = ObjectConverter.class)
  private List<Drawer> oneColumnDrawerList = new ArrayList<Drawer>();

  public List<Drawer> getDrawerList() {
    return drawerList;
  }

  public void setDrawerList(List<Drawer> drawerList) {
    this.drawerList = drawerList;
  }

  public List<Drawer> getOneColumnDrawerList() {
    return oneColumnDrawerList;
  }

  public void setOneColumnDrawerList(List<Drawer> customDrawerList) {
    this.oneColumnDrawerList = customDrawerList;
  }

  public String getDeskType() {
    return deskType;
  }

  public void setDeskType(String deskType) {
    this.deskType = deskType;
  }

  public Desk addDrawer(Drawer drawer) {
    getDrawerList().add(drawer);
    return this;
  }
}
