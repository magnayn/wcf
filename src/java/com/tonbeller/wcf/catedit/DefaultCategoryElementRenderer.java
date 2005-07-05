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
 * creates a DOM Element named "cat-catecory" with attributes "name" and "icon"
 * 
 * @author av
 */
public class DefaultCategoryElementRenderer implements CategoryElementRenderer {
  String elementName = "cat-category";

  /**
   * Constructor for DefaultCategoryElementRenderer.
   */
  public DefaultCategoryElementRenderer() {
    super();
  }

  /**
   * creates a DOM Element with attributes "name" and "icon"
   */
  public Element render(RequestContext context, Document factory, Category cat) {
    Element elem = factory.createElement(elementName);
    elem.setAttribute("name", cat.getName());
    elem.setAttribute("icon", cat.getIcon());
    return elem;
  }

  /**
   * Returns the elementName.
   * @return String
   */
  public String getElementName() {
    return elementName;
  }

  /**
   * Sets the elementName.
   * @param elementName The elementName to set
   */
  public void setElementName(String elementName) {
    this.elementName = elementName;
  }

}
