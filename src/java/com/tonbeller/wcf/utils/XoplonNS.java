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
package com.tonbeller.wcf.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * wrapper for a Xoplon namespace. There's no namespace supported
 * yet, but implementation can could be extended to namespace
 * supporting.
 * Actually, I had problems finding the fitting Xalan API function
 * to map namespace uri to namespace prefix, so I postponed
 * namespace support.
 * So this class is a dummy implementation using the DOM API functions
 * ignoring namespaces.
 */
public class XoplonNS {
  /** Xoplon namespace, yet hard coded */
  private static final String NAMESPACE_URI = "urn:xoplon:core";
  private static final String NAMESPACE_PREFIX = "xc";

  private XoplonNS() {}

  /** creates a DOM element of given tag name */
  public static Element createElement(Document factory, String name) {
    return factory.createElement(name);
  }

  /** sets a DOM attribute */
  public static void setAttribute(Element elem, String name, String value) {
    elem.setAttribute(name, value);
  }

  /** adds xmlns:xc="uri:xoplon:core" */
  public static void setNameSpaceAttribute(Element elem) {
  }

  /** removes a DOM attribute */
  public static void removeAttribute(Element elem, String name) {
    DomUtils.removeAttribute(elem, name);
  }

  /** retrieves DOM attribute */
  public static String getAttribute(Element elem, String name) {
    return elem.getAttribute(name);
  }

  public static boolean isXoplonNS(Element elem) {
    return true;
  }

  /** returns tag name w/o prefix */
  public static String getLocalName(Element elem) {
    return elem.getTagName();
  }

}
