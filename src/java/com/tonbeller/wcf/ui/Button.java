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
 * Button ctrl class, used as factory for generating buttons
 */
public class Button extends XoplonCtrl {
  public static final String NODENAME = "button";

  public static boolean isButton(Element elem) {
    return elem.getNodeName().equals(NODENAME);
  }
  
  public static Element createButton(Document doc, String id, String label) {
    Element retVal = XoplonCtrl.createCtrl(doc, NODENAME);
    XoplonCtrl.setId(retVal, id);
    XoplonCtrl.setLabel(retVal, label);
    return retVal;
  }

  public static Element addButton(Element parent, String id, String label) {
    Element retVal = createButton(parent.getOwnerDocument(), id, label);
    parent.appendChild(retVal);
    return retVal;
  }
}
