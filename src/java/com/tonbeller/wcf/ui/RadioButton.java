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


public class RadioButton extends Item {
  public static final String NODENAME = "radioButton";

  public static boolean isRadioButton(Element elem) {
    return elem.getNodeName().equals(NODENAME);
  }

  /** set Group id */
  public static void setGroupId(Element element, String groupId) {
    XoplonNS.setAttribute(element, "group-id", groupId);
  }

  /** get group id */
  public static String getGroupId(Element element) {
    return XoplonNS.getAttribute(element, "group-id");
  }

  /** factory function */
  public static Element createRadioButton(Document doc) {
    return Item.createItem(doc, NODENAME);
  }

  /** factory function, using given groupId */
  public static Element createRadioButton(Document doc, String groupId) {
    Element radioButton = createRadioButton(doc);
    setGroupId(radioButton, groupId);
    return radioButton;
  }

  /** adds radio button to parent element, using parent's id as group id */
  public static Element addRadioButton(Element parent) {
    Element rb = createRadioButton(parent.getOwnerDocument());
    parent.appendChild(rb);
    setGroupId(rb, XoplonCtrl.getId(parent));
    return rb;
  }

}
