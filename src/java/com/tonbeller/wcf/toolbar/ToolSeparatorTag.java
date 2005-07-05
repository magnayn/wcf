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
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;


/**
 * @author andreas
 */
public class ToolSeparatorTag extends TagSupport {
  int size;
  String visibleRef;
  String role;
	private static Logger logger = Logger.getLogger(ToolSeparatorTag.class);
  
  public int doStartTag() throws JspException {
  	logger.info("enter");
    // find the toolbar
    ToolBarTag tbt = (ToolBarTag)super.findAncestorWithClass(this, ToolBarTag.class);
    if (tbt == null)
      throw new JspException("ToolButtonTag must be nested in ToolBarTag");
    ToolBar tbar = tbt.getToolBar();
    
    // create a separator
    ToolSeparator tsep = new ToolSeparator();
    tsep.setSize(getSize());
    tsep.setVisibleExpr(visibleRef);
    tsep.setRole(role);
    tbar.addSeparator(tsep);
		logger.info("leave");
    return EVAL_BODY_INCLUDE;
  }

  /**
   * Returns the size.
   * @return int
   */
  public int getSize() {
    return size;
  }

  /**
   * Sets the size.
   * @param size The size to set
   */
  public void setSize(int size) {
    this.size = size;
  }

  /**
   * @param string
   */
  public void setVisibleRef(String string) {
    visibleRef = string;
  }

  public void setRole(String string) {
    role = string;
  }

}
