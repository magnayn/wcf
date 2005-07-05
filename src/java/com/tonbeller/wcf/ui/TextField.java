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
 * Factory class for generating text ctrls
 */
public class TextField extends EditCtrl {
  public static final String NODENAME = "textField";

  public static boolean isTextField(Element elem) {
    return elem.getNodeName().equals(NODENAME);
  }

  /** factory function for text ctrl */
  public static Element createTextCtrl(Document doc, String type) {
    return createValueHolder(doc, NODENAME, type);
  }

  /** factory function for adding text ctrl to parent */
  public static Element addTextCtrl(Element parent, String type) {
    Element textCtrl = createTextCtrl(parent.getOwnerDocument(), type);
    parent.appendChild(textCtrl);
    return textCtrl;
  }
}
