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

import com.tonbeller.wcf.controller.RequestContext;

/**
 * @author av
 */
public abstract class ToolButtonTag extends TagSupport {
  String img;
  String radioGroup;
  String tooltip;
  String visibleRef;
  String role;
  
	private static Logger logger = Logger.getLogger(ToolButtonTag.class);

  /**
   * Returns the radioGroup.
   * @return String
   */
  public String getRadioGroup() {
    return radioGroup;
  }

  /**
   * Sets the radioGroup.
   * @param radioGroup The radioGroup to set
   */
  public void setRadioGroup(String radioGroup) {
    this.radioGroup = radioGroup;
  }

  public int doStartTag() throws JspException {
  	logger.info("enter " + id);
    RequestContext context = RequestContext.instance();

    // find the toolbar
    ToolBarTag tbt = (ToolBarTag)findAncestorWithClass(this, ToolBarTag.class);
    if (tbt == null)
      throw new JspException("ToolButtonTag must be nested in ToolBarTag");
    ToolBar tbar = tbt.getToolBar();

    ToolButtonModel tbm = getToolButtonModel(context);
    
    // create a button
    ToolButton tbut = new ToolButton(tbm, getImg()+"-up", getImg()+"-down");
    tbut.setId(getId());
    tbut.setRadioGroup(getRadioGroup());
    tbut.setTooltip(tooltip);
    tbut.setVisibleExpr(visibleRef);
    tbut.setRole(role);
    tbar.addButton(tbut);
  
    logger.info("leave " + id);
    return EVAL_BODY_INCLUDE;
  }


  /** 
   * factory method to create the button model
   */
  protected abstract ToolButtonModel getToolButtonModel(RequestContext context) throws JspException;

  /**
   * Returns the img.
   * @return String
   */
  public String getImg() {
    return img;
  }

  /**
   * Sets the img.
   * @param img The img to set
   */
  public void setImg(String img) {
    this.img = img;
  }

  /**
   * @return
   */
  public String getTooltip() {
    return tooltip;
  }

  /**
   * @param string
   */
  public void setTooltip(String string) {
    tooltip = string;
  }

  /**
   * @param string
   */
  public void setVisibleRef(String string) {
    visibleRef = string;
  }

  /**
   * @param role
   */
  public void setRole(String role) {
    this.role = role;
  }

  /**
   * @return
   */
  public String getRole() {
    return role;
  }

  /**
   * @return
   */
  public String getVisibleRef() {
    return visibleRef;
  }

}
