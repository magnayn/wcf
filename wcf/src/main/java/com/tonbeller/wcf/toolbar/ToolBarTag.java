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

import javax.servlet.jsp.JspException;

import com.tonbeller.tbutils.res.Resources;
import com.tonbeller.wcf.component.Component;
import com.tonbeller.wcf.component.ComponentTag;
import com.tonbeller.wcf.controller.RequestContext;

/**
 * @author av
 */
public class ToolBarTag extends ComponentTag {
  String bundle;
  boolean globalButtonIds = false;

  public void release() {
    globalButtonIds = false;
    bundle = null;
    super.release();
  }  
  
  public ToolBar getToolBar() {
    return (ToolBar) super.getComponent();
  }

  public Component createComponent(RequestContext context) throws JspException {
    ToolBar tb = new ToolBar(getId(), null);
    tb.setGlobalButtonIds(globalButtonIds);
    if (bundle != null) {
      Resources resb = context.getResources(bundle);
      tb.setBundle(resb);
    }
    return tb;
  }
  

  /**
   * @return
   */
  public String getBundle() {
    return bundle;
  }

  /**
   * @param string
   */
  public void setBundle(String string) {
    bundle = string;
  }

  public void setGlobalButtonIds(boolean globalButtonIds) {
    this.globalButtonIds = globalButtonIds;
  }
}
