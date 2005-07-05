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

import com.tonbeller.wcf.format.FormatException;
import com.tonbeller.wcf.utils.XoplonNS;

/**
 * Factory class for generating text area ctrls, provides also access
 * to the text area's special attributes
 */
public class TextArea extends EditCtrl {
  public static final String NODENAME = "textArea";

  public static boolean isTextArea(Element elem) {
    return elem.getNodeName().equals(NODENAME);
  }

  /** factory function for text ctrl */
  public static Element createTextArea(Document doc, int iRows) throws FormatException {
    Element retVal = createValueHolder(doc, NODENAME);
    setRows(retVal, iRows);
    return retVal;
  }

  /** factory function for adding text ctrl to parent */
  public static Element addTextArea(Element parent, int iRows) throws FormatException {
    Element textArea = createTextArea(parent.getOwnerDocument(), iRows);
    parent.appendChild(textArea);
    return textArea;
  }

  /** set Rows */
  public static void setRows(Element element, int rows) throws FormatException {
    XoplonNS.setAttribute(element, "rows", Integer.toString(rows));
  }

  /** get Rows */
  public static int getRows(Element element) throws FormatException {
    return Integer.parseInt(XoplonNS.getAttribute(element, "rows"));
  }

  /** set Columns */
  public static void setCols(Element element, int cols) throws FormatException {
    XoplonNS.setAttribute(element, "cols", Integer.toString(cols));
  }

  /** get Columns */
  public static int getCols(Element element) throws FormatException {
    return Integer.parseInt(XoplonNS.getAttribute(element, "cols"));
  }
}
