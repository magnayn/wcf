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

import java.text.MessageFormat;

import org.w3c.dom.Element;

import com.tonbeller.wcf.controller.RequestContext;
import com.tonbeller.wcf.utils.DomUtils;

public class ImgButton extends ToolBarComponentSupport {

  private ToolBar tbar;
  /**
   * @param tbar
   */
  public ImgButton(ToolBar tbar) {
    this.tbar = tbar;
  }

  private String tooltip;
  // URL
  private String href;
  // browser window name, see HTML spec 
  private String target;
  // Image URL
  private String img;

  /**
   * @see com.tonbeller.wcf.toolbar.ToolBarComponent#render(com.tonbeller.wcf.controller.RequestContext, org.w3c.dom.Document)
   */
  public void render(RequestContext context, Element parent) throws Exception {
    Element btn = DomUtils.appendElement(parent, "img-button");
    btn.setAttribute("img", img +"-up");
    btn.setAttribute("id", getId());
    btn.setAttribute("href", makeHref(context));
    if(target!=null && target.length()!=0)
      btn.setAttribute("target", target);
    btn.setAttribute("title", tbar.getTooltip(tooltip));
  }

  String makeHref(RequestContext context) {
    Object[] args = {context.getRequest().getContextPath(), DomUtils.randomId()};
    return MessageFormat.format(href, args);
  }
  
  public void initialize(RequestContext context, ToolBar owner) {
  }

  public String getHref() {
    return href;
  }

  public String getImg() {
    return img;
  }

  public String getTarget() {
    return target;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public void setImg(String img) {
    this.img = img;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  public void setTooltip(String tooltip) {
    this.tooltip = tooltip;
  }

  public boolean isSeparator() {
    return false;
  }

}
