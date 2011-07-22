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
package com.tonbeller.wcf.catedit;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tonbeller.wcf.controller.RequestContext;

/**
 * creates a DOM Element named "cat-item" with attribute "name"
 * 
 * @author av
 */
public class DefaultItemElementRenderer implements ItemElementRenderer {
  private String elementName = "cat-item";

  /**
   * Constructor for DefaultItemElementRenderer.
   */
  public DefaultItemElementRenderer() {
    super();
  }

  public Element render(RequestContext context, Document factory, Category cat, Item item) {
    Element elem = factory.createElement(elementName);
    elem.setAttribute("name", item.getLabel());
    return elem;
  }

  public String getElementName() {
    return elementName;
  }

  public void setElementName(String elementName) {
    this.elementName = elementName;
  }
  
  public void startRendering(RequestContext context) {
  }

  public void stopRendering() {
  }
  public void categoryModelChanged(CategoryModelChangeEvent event) {
  }
  public void request(RequestContext context) throws Exception {
  }
}
