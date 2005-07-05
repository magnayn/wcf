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
 * Label ctrl class, used as factory for generating labels
 */
public class Label extends EditCtrl {
  public static final String NODENAME = "label";

  public static boolean isLabel(Element elem) {
    return elem.getNodeName().equals(NODENAME);
  }

  public static Element createLabel(Document doc, String label) {
    Element retVal = EditCtrl.createCtrl(doc, NODENAME);
    XoplonCtrl.setLabel(retVal, label);
    return retVal;
  }

  public static Element addLabel(Element parent, String label) {
    Element retVal = createLabel(parent.getOwnerDocument(), label);
    parent.appendChild(retVal);
    return retVal;
  }

}
