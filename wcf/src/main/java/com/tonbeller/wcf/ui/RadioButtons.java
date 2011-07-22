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
 * factory for generating radio buttons
 * @deprecated - does this make sense?
 */
public class RadioButtons extends SelectSingle {
  public static final String NODENAME = "radioButtons";

  public static boolean isRadioButtons(Element elem) {
    return elem.getNodeName().equals(NODENAME);
  }

  /** factory function for creating radio buttons */
  public static Element createRadioButtons(Document doc) {
    Element rbs = XoplonCtrl.createCtrl(doc, NODENAME);
    return rbs;
  }

  /** factory function for adding radio buttons to parent */
  public static Element addRadioButtons(Element parent) {
    Element rbs = createRadioButtons(parent.getOwnerDocument());
    parent.appendChild(rbs);
    return rbs;
  }

}
