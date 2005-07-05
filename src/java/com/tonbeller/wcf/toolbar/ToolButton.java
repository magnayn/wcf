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

import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Element;

import com.tonbeller.wcf.controller.RequestContext;
import com.tonbeller.wcf.controller.RequestListener;
import com.tonbeller.wcf.utils.DomUtils;

/**
 * @author av
 */
public class ToolButton extends ToolBarComponentSupport implements RequestListener {
  private String tooltip;
  String upImg;
  String downImg;
  boolean pressed;
  String radioGroup;

  ToolBar toolBar;
  ToolButtonModel model;

  String requestId;

  public ToolButton(ToolButtonModel model, String upImg, String downImg) {
    this.model = model;
    this.upImg = upImg;
    this.downImg = downImg;
  }

  /**
   * Returns the pressed.
   * @return boolean
   */
  public boolean isPressed() {
    return pressed;
  }

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

  /**
   * Returns the downImg.
   * @return String
   */
  public String getDownImg() {
    return downImg;
  }

  /**
   * Returns the upImg.
   * @return String
   */
  public String getUpImg() {
    return upImg;
  }

  /**
   * Sets the downImg.
   * @param downImg The downImg to set
   */
  public void setDownImg(String downImg) {
    this.downImg = downImg;
  }

  /**
   * Sets the upImg.
   * @param upImg The upImg to set
   */
  public void setUpImg(String upImg) {
    this.upImg = upImg;
  }

  public void initialize(RequestContext context, ToolBar owner) {
    this.toolBar = (ToolBar) owner;
    // stay compatible - many tests have to be rewritten to use the local names
    // so if no buttonIdPrefix is set, we still use the plain button ids
    if (toolBar.isGlobalButtonIds())
      this.requestId = getId();
    else
      this.requestId = toolBar.getId() + "." + getId();
    owner.getDispatcher().addRequestListener(requestId, null, this);
  }

  public void render(RequestContext context, Element parent) throws Exception {
    // synchronize with model
    pressed = model.isPressed(context);

    Element elem = DomUtils.appendElement(parent, "tool-button");
    elem.setAttribute("id", requestId);
    if (pressed)
      elem.setAttribute("img", downImg);
    else
      elem.setAttribute("img", upImg);
    elem.setAttribute("title", toolBar.getTooltip(tooltip));
  }

  public void request(RequestContext context) throws Exception {
    List list = toolBar.getRadioGroup(this);
    if (list != null) {
      for (Iterator it = list.iterator(); it.hasNext();) {
        ToolButton btn = (ToolButton) it.next();
        if (btn != this)
          btn.model.setPressed(context, false);
      }
    }

    // flip the button
    pressed = model.isPressed(context);
    pressed = !pressed;
    model.setPressed(context, pressed);
    // sync with model
    pressed = model.isPressed(context);
  }

  /**
   * @param tooltip
   */
  public void setTooltip(String tooltip) {
    this.tooltip = tooltip;
  }

  public boolean isSeparator() {
    return false;
  }

  public void setImg(String img) {
    setUpImg(img + "-up");
    setDownImg(img + "-down");
  }

}