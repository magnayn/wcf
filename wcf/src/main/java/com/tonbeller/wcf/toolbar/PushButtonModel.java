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
 * Created on 05.12.2002
 * 
 * @author av
 */
public class PushButtonModel implements ToolButtonModel {
  boolean pressed;

  public boolean isPressed(RequestContext context) {
    return pressed;
  }

  public void setPressed(RequestContext context, boolean pressed) {
    this.pressed = pressed;
  }

}
