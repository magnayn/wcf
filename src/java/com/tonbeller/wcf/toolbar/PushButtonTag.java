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

import com.tonbeller.wcf.controller.RequestContext;


/**
 * Created on 06.01.2003
 * 
 * @author av
 */
public class PushButtonTag extends ToolButtonTag {

  private boolean pressed = false;
  
  /**
   * @see com.tonbeller.wcf.toolbar.ToolButtonTag#getToolButtonModel()
   */
  protected ToolButtonModel getToolButtonModel(RequestContext context) {

    PushButtonModel pbm = new PushButtonModel();
    pbm.setPressed(context, isPressed());
    return pbm;
  }

  /**
   * Returns the pressed.
   * @return boolean
   */
  public boolean isPressed() {
    return pressed;
  }

  /**
   * Sets the pressed.
   * @param pressed The pressed to set
   */
  public void setPressed(boolean pressed) {
    this.pressed = pressed;
  }

}
