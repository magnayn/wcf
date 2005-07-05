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
 * Image Button is an image (img) with a hyperlink (a) and a target window name 
 */
public class ImgButtonTag extends TagSupport {

  // URL
  private String href;
  // browser window name, see HTML spec 
  private String target;
  // Image URL
  private String img;
  private String tooltip;
  private String visibleRef;
  private String role;
	private static Logger logger = Logger.getLogger(ImgButtonTag.class);

  public int doStartTag() throws JspException {
		logger.info("enter");
   // find the toolbar
    ToolBarTag tbt = (ToolBarTag)super.findAncestorWithClass(this, ToolBarTag.class);
    if (tbt == null)
      throw new JspException("ToolButtonTag must be nested in ToolBarTag");
    ToolBar tbar = tbt.getToolBar();
    
    // create a separator
    ImgButton btn = new ImgButton(tbar);
    btn.setImg(getImg());
    btn.setId(getId());
    btn.setHref(getHref());
    btn.setTarget(getTarget());
    btn.setVisibleExpr(visibleRef);
    btn.setRole(role);
    btn.setTooltip(tooltip);
    
    tbar.addImgButton(btn);
		logger.info("leave");
    return EVAL_BODY_INCLUDE;
  }

  /**
   * Returns the href.
   * @return String
   */
  public String getHref() {
    return href;
  }

  /**
   * Returns the target.
   * @return String
   */
  public String getTarget() {
    return target;
  }

  /**
   * Sets the href.
   * @param href The href to set
   */
  public void setHref(String href) {
    this.href = href;
  }

  /**
   * Sets the target.
   * @param target The target to set
   */
  public void setTarget(String target) {
    this.target = target;
  }

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
   * @param string
   */
  public void setRole(String string) {
    role = string;
  }

}
