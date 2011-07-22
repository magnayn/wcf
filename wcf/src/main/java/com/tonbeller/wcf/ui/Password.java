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
 * factory for generating password ctrls
 */
public class Password extends EditCtrl {
  public static final String NODENAME = "password";

  public static boolean isPassword(Element elem) {
    return elem.getNodeName().equals(NODENAME);
  }

  /** factory function for password ctrl */
  public static Element createPasswordCtrl(Document doc) {
    return createValueHolder(doc, NODENAME);
  }

  /** factory function for adding text ctrl to parent */
  public static Element addPasswordCtrl(Element parent) {
    Element pwdCtrl = createPasswordCtrl(parent.getOwnerDocument());
    parent.appendChild(pwdCtrl);
    return pwdCtrl;
  }

}
