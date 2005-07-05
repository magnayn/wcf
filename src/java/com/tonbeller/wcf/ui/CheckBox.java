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

public class CheckBox extends Item {
  public static final String NODENAME = "checkBox";

  public static boolean isCheckBox(Element elem) {
    return elem.getNodeName().equals(NODENAME);
  }

  /** factory function */
  public static Element createCheckBox(Document doc) {
    Element cb = Item.createItem(doc, NODENAME);
    return cb;
  }

  /** adds check box to parent element, using parent's id as group id */
  public static Element addCheckBox(Element parent) {
    Element cb = createCheckBox(parent.getOwnerDocument());
    parent.appendChild(cb);
    return cb;
  }

 
}
