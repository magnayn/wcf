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

/**
 * single selection listbox
 */
public class ListBox1 extends SelectSingle {
  public static final String NODENAME = "listBox1";

  public static boolean isListBox1(Element elem) {
    return elem.getNodeName().equals(NODENAME);
  }

  /** factory function to create listbox */
  public static Element createListBox1(Document doc) {
    Element listBox = XoplonCtrl.createCtrl(doc, NODENAME);
    XoplonNS.setAttribute(listBox, "multiple", "false");
    return listBox;
  }

  /** factory function for adding listbox to parent */
  public static Element addListBox1(Element parent) {
    Element listBox = createListBox1(parent.getOwnerDocument());
    parent.appendChild(listBox);
    return listBox;
  }


}
