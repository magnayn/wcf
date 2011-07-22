/*
 * Copyright (c) 1971-2003 TONBELLER AG, Bensheim.
 * All rights reserved.
 */
package com.tonbeller.wcf.popup;

import org.w3c.dom.Document;

import com.tonbeller.tbutils.xmlunit.XmlTestCase;
import com.tonbeller.wcf.controller.RequestContext;

public class PopUpTest extends XmlTestCase {
  TestBean tb;
  
  
  public PopUpTest(String name) {
    super(name);
  }
  
  protected void setUp() throws Exception {
    tb = new TestBean();
  }

  public void testPopUp0() throws Exception {
    PopUp pu = tb.getPopUp0();
    Document dom = pu.render((RequestContext)null);
    assertNodeCount(dom, "/popup-menu/popup-group", 0);
    assertNodeCount(dom, "/popup-menu/popup-item", 2);
  }
  
  public void testPopUp1a() throws Exception {
    PopUp pu = tb.getPopUp1a();
    Document dom = pu.render((RequestContext)null);
    assertNodeCount(dom, "/popup-menu[@id]/popup-group", 1);
    assertNodeCount(dom, "/popup-menu/popup-item", 0);
    assertNodeCount(dom, "/popup-menu/popup-group/popup-item", 2);
  }

  public void testPopUp1b() throws Exception {
    PopUp pu = tb.getPopUp1b();
    Document dom = pu.render((RequestContext)null);
    assertNodeCount(dom, "/popup-menu/popup-group", 1);
    assertNodeCount(dom, "/popup-menu/popup-item", 0);
    assertNodeCount(dom, "/popup-menu/popup-group/popup-item", 2);
  }

  public void testPopUp2() throws Exception {
    PopUp pu = tb.getPopUp2();
    Document dom = pu.render((RequestContext)null);
    assertNodeCount(dom, "/popup-menu/popup-group[@label='Group 1']/popup-item", 2);
    assertNodeCount(dom, "/popup-menu/popup-group[@label='Group 2']/popup-item", 2);
    assertNodeCount(dom, "/popup-menu/popup-group[@label='Group 3']", 0);
    assertNodeCount(dom, "/popup-menu/popup-group[@label='Group 4']/popup-item", 6);
    assertNodeCount(dom, "/popup-menu/popup-group[@label='Group 4']/popup-group", 2);
  }

}
