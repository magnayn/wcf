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
 * Base class for all Xoplon controls.
 * Contains static factory function and static access functions to id and label attributes.
 * @See com.tonbeller.wcf.ui
 */
public abstract class XoplonCtrl {
  /** creates ctrl of given type */
  protected static Element createCtrl(Document factory, String type) {
    Element elem = XoplonNS.createElement(factory, type);
    return elem;
  }

  /** returns element's id */
  public static String getId(Element element) {
    return XoplonNS.getAttribute(element, "id");
  }

  /** sets element's id */
  public static void setId(Element elem, String id) {
    XoplonNS.setAttribute(elem, "id", id);
  }

  /** sets element's label */
  public static void setLabel(Element elem, String label) {
    if (label == null)
      XoplonNS.removeAttribute(elem, "label");
    else
      XoplonNS.setAttribute(elem, "label", label);
  }

  /** gets element's label */
  public static String getLabel(Element elem) {
    return XoplonNS.getAttribute(elem, "label");
  }

  /** sets element's disabled property */
  public static void setDisabled(Element elem, boolean disabled) {
    XoplonNS.setAttribute(elem, "disabled", disabled ? "true" : "false");
  }

  /** gets element's disabled property */
  public static boolean isDisabled(Element elem) {
    return "true".equals(XoplonNS.getAttribute(elem, "disabled"));
  }

  /** sets element's hidden property */
  public static void setHidden(Element elem, boolean disabled) {
    XoplonNS.setAttribute(elem, "hidden", disabled ? "true" : "false");
  }

  /** gets element's hidden property */
  public static boolean isHidden(Element elem) {
    return "true".equals(XoplonNS.getAttribute(elem, "hidden"));
  }

  /**
   * defines the model. The simplest modelReference is the name
   * of a beans attribute. Syntax of jakarta-commons/bean-utils
   * is supported
   */
  public static void setModelReference(Element elem, String label) {
    XoplonNS.setAttribute(elem, "modelReference", label);
  }

  /** gets element's modelReference attribute */
  public static String getModelReference(Element elem) {
    return XoplonNS.getAttribute(elem, "modelReference");
  }

}
