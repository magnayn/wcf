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

import com.tonbeller.wcf.component.Component;
import com.tonbeller.wcf.controller.RequestContext;

/**
 * toolbar button that optionally validates or reverts the content of forms. Afterwards it optionally
 * forwards to another page.
 * 
 * @author av
 */
public class FormButtonModel implements ToolButtonModel {
  Component comp;
  String action;
  String forward;

  public FormButtonModel(Component comp) {
    this.comp = comp;
  }

  /**
   * @see com.tonbeller.wcf.toolbar.ToolButtonModel#isPressed(RequestContext)
   */
  public boolean isPressed(RequestContext context) {
    return false;
  }

  /**
   * @see com.tonbeller.wcf.toolbar.ToolButtonModel#setPressed(RequestContext, boolean)
   */
  public void setPressed(RequestContext context, boolean pressed) {
    boolean success = true;
    if (action != null) {
      if (action.equals("revert"))
        comp.revert(context);
      else if (action.equals("validate"))
        success = comp.validate(context);
      else
        throw new IllegalArgumentException("invalid action: " + action);
    }
    if (success && forward != null && forward.length() > 0)
      comp.setNextView(forward);
  }

  /**
   * Sets the action.
   * @param action The action to set
   */
  public void setAction(String action) {
    this.action = action;
  }

  /**
   * Sets the forward.
   * @param forward The forward to set
   */
  public void setForward(String forward) {
    this.forward = forward;
  }

}
