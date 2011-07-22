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

/**
 * factory for generating check box container
 * @deprecated - does this make sense?
 */
public class CheckBoxes extends SelectMultiple {
  public static final String NODENAME = "checkBoxes";

  public static boolean isCheckBoxes(Element elem) {
    return elem.getNodeName().equals(NODENAME);
  }

  /** factory function for creating checkBoxes */
  public static Element createCheckBoxes(Document doc) {
    Element cbs = XoplonCtrl.createCtrl(doc, NODENAME);
    return cbs;
  }

  /** factory function for adding check boxes to parent */
  public static Element addCheckBoxes(Element parent) {
    Element cbs = createCheckBoxes(parent.getOwnerDocument());
    parent.appendChild(cbs);
    return cbs;
  }

}
