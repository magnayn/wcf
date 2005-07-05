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
package com.tonbeller.wcf.form;

import javax.servlet.http.HttpSession;

import org.w3c.dom.Element;

import com.tonbeller.wcf.controller.RequestContext;

/**
 * default implementation of a NodeHandler
 * @author av
 */
public class NodeHandlerSupport implements NodeHandler {

  private XmlComponent xmlComponent;
  private Element element;
 
  public void initialize(RequestContext context, XmlComponent comp, Element element) throws Exception {
    this.xmlComponent = comp;
    this.element = element;
  }

  public void destroy(HttpSession session) throws Exception {
  }
 
  /**
   * does nothing
   */
  public void render(RequestContext context) throws Exception {
  }

  /**
   * Returns the element.
   * @return Element
   */
  public Element getElement() {
    return element;
  }

  /**
   * @return
   */
  public XmlComponent getXmlComponent() {
    return xmlComponent;
  }


}
