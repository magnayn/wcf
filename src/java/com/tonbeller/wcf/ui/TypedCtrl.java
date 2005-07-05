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

import org.w3c.dom.Element;

import com.tonbeller.wcf.format.BasicTypes;
import com.tonbeller.wcf.utils.XoplonNS;

/**
 * A DOM element with attributes describing a data type.
 * @author av
 */
public class TypedCtrl extends XoplonCtrl implements BasicTypes {
  

  /** Set format string */
  public static void setFormatString(Element element, String formatString) {
    XoplonNS.setAttribute(element, "format", formatString);
  }

  /** Get format string */
  public static String getFormatString(Element element) {
    return XoplonNS.getAttribute(element, "format");
  }

  /** returns the data type */
  public static String getType(Element element) {
    return XoplonNS.getAttribute(element, "type");
  }

  /** sets data type */
  public static void setType(Element element, String type) {
    XoplonNS.setAttribute(element, "type", type);
  }


}
