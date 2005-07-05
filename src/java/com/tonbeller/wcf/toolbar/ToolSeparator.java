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
package com.tonbeller.wcf.toolbar;

import org.w3c.dom.Element;

import com.tonbeller.wcf.controller.RequestContext;
import com.tonbeller.wcf.utils.DomUtils;

/**
 * separator for toolbar
 * @author av
 */
public class ToolSeparator extends ToolBarComponentSupport {
  int size;
  
  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public void initialize(RequestContext context, ToolBar owner) {
  }

  public void render(RequestContext context, Element parent) throws Exception {
    Element sep = DomUtils.appendElement(parent, "tool-sep");
    if (size > 0)
      sep.setAttribute("size", Integer.toString(size));
    else
      sep.setAttribute("size", "1ex");
  }

  public boolean isSeparator() {
    return true;
  }

}
