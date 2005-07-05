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

/** factory for listbox item */
public class ListItem extends Item {
  public static final String NODENAME = "listItem";

  public static boolean isListItem(Element elem) {
    return elem.getNodeName().equals(NODENAME);
  }

  /** factory function */
  public static Element createListItem(Document doc) {
    return Item.createItem(doc, NODENAME);
  }

  /** factory function */
  public static Element addListItem(Element parent) {
    Element retVal = createListItem(parent.getOwnerDocument());
    parent.appendChild(retVal);
    return retVal;
  }
}
