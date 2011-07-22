/*
 * ====================================================================
 * This software is subject to the terms of the Common Public License
 * Agreement, available at the following URL:
 *   http://www.opensource.org/licenses/cpl.html .
 * Copyright (C) 2003-2004 TONBELLER AG.
 * All Rights Reserved.
 * You must accept the terms of that agreement to use this software.
 * ====================================================================
 *
 * 
 */
package com.tonbeller.wcf.ui;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tonbeller.wcf.utils.XoplonNS;


/** base class factory for ListItem, RadioButton, CheckBox */
public abstract class Item extends XoplonCtrl {

  /** select */
  public static void setSelected(Element element, boolean flag) {
    String selected = flag ? "true" : "false";
    XoplonNS.setAttribute(element, "selected", selected);
  }

  /** select */
  public static boolean isSelected(Element element) {
    boolean flag = (XoplonNS.getAttribute(element, "selected")).equals("true") ? true : false;
    return flag;
  }

  /** factory function */
  protected static Element createItem(Document doc, String type) {
    return EditCtrl.createValueHolder(doc, type);
  }
  
  /** set the items value. Type and modelReference are properties of the parent (e.g. the ListBox itself) */
  public static void setValue(Element element, String value) {
    XoplonNS.setAttribute(element, "value", value);
  }

  /** get the items value. Type and modelReference are properties of the parent (e.g. the ListBox itself) */
  public static String getValue(Element element) {
    return XoplonNS.getAttribute(element, "value");
  }

  
}
