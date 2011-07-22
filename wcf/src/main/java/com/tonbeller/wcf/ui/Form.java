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
 * Form ctrl class, used as factory for generating buttons
 */
public class Form extends XoplonCtrl {
  public static final String NODENAME = "form";

  public static boolean isForm(Element elem) {
    return elem.getNodeName().equals(NODENAME);
  }

  public static Element createForm(Document doc) {
    Element retVal = XoplonCtrl.createCtrl(doc, NODENAME);
    return retVal;
  }

  public static Element addForm(Element parent) {
    Element form = createForm(parent.getOwnerDocument());
    parent.appendChild(form);
    return form;
  }

}
