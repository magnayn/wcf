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
 * Button ctrl class, used as factory for generating buttons
 */
public class ImgButton extends XoplonCtrl {
  public static final String NODENAME = "imgButton";

  public static boolean isImgButton(Element elem) {
    return elem.getNodeName().equals(NODENAME);
  }
  
  public static Element createImgButton(Document doc, String id, String src) {
    Element elem = XoplonCtrl.createCtrl(doc, NODENAME);
    XoplonCtrl.setId(elem, id);
    XoplonNS.setAttribute(elem, "src", src);
    return elem;
  }

  public static Element addButton(Element parent, String id, String src) {
    Element elem = createImgButton(parent.getOwnerDocument(), id, src);
    parent.appendChild(elem);
    return elem;
  }
  
}
