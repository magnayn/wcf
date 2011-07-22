/*
 * Copyright (c) 1971-2003 TONBELLER AG, Bensheim.
 * All rights reserved.
 */
package com.tonbeller.wcf.popup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import com.tonbeller.wcf.controller.Dispatcher;
import com.tonbeller.wcf.controller.DispatcherSupport;
import com.tonbeller.wcf.controller.RequestContext;
import com.tonbeller.wcf.controller.WcfController;

public class TestBean implements HttpSessionBindingListener {

  Dispatcher disp = new DispatcherSupport();
  String lastItem;

  /**
   * Items w/o groups
   */
  public PopUp getPopUp0() {
    PopUp p = new PopUp();
    p.setLabel("popup 0");
    List items = new ArrayList();
    addItems(items);
    for (Iterator it = items.iterator(); it.hasNext();) {
      Item item = (Item) it.next();
      p.addItem(item);
    }
    return p;
  }

  /**
   * 2 Items in anonymous group
   */
  public PopUp getPopUp1a() {
    PopUp p = new PopUp();
    p.setLabel("popup 1a");
    // anonymous group
    GroupItem g = new GroupItemSupport();
    addItems(g.getChildren());
    p.addItem(g);
    return p;
  }

  /**
   * 2 Items in named group
   */
  public PopUp getPopUp1b() {
    PopUp p = new PopUp();
    p.setLabel("popup 1b");
    // anonymous group
    GroupItem g = new GroupItemSupport("Group 1");
    addItems(g.getChildren());
    p.addItem(g);
    return p;
  }

  public PopUp getPopUp2() {
    PopUp p = new PopUp();
    p.setLabel("popup 2");

    GroupItem g = new GroupItemSupport("Group 1");
    addItems(g.getChildren());
    p.addItem(g);

    g = new GroupItemSupport("Group 2", "/wcf/table/page-last.png");
    addItems(g.getChildren());
    p.addItem(g);

    g = new GroupItemSupport("Group 3 is empty", "/wcf/table/page-first.png");
    p.addItem(g);

    g = new GroupItemSupport("Group 4", "/wcf/table/page-first.png");
    addItems(g.getChildren());
    p.addItem(g);
    GroupItem sub = new GroupItemSupport("Group 4.1");
    addItems(sub.getChildren());
    g.getChildren().add(sub);
    addItems(g.getChildren());
    sub = new GroupItemSupport("Group 4.2", "/wcf/table/page-first.png");
    addItems(sub.getChildren());
    g.getChildren().add(sub);
    addItems(g.getChildren());

    return p;
  }

  private void addItems(List list) {
    MenuItemSupport m;
    m = new WcfMenuItem(disp, "Item 1") {
      public void request(RequestContext context) throws Exception {
        lastItem = "Item 1";
      }
    };
    list.add(m);
    m = new WcfMenuItem(disp, "Item 2", "/wcf/tree/accept.png") {
      public void request(RequestContext context) throws Exception {
        lastItem = "Item 2";
      }
    };
    list.add(m);
  }

  public String getLastItem() {
    disp.clear();
    return lastItem;
  }

  public void setLastItem(String lastItem) {
    this.lastItem = lastItem;
  }

  public void valueBound(HttpSessionBindingEvent e) {
    WcfController.instance(e.getSession()).addRequestListener(disp);
  }

  public void valueUnbound(HttpSessionBindingEvent e) {
  }

}
