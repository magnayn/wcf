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
 * Toolbar button associated with a property of a session bean. The property is addressed via
 * a script string.
 * 
 * @author av
 */
public class ScriptButtonModel implements ToolButtonModel {
  String modelReference;
  

  /**
   * Constructor for ScriptModel.
   */
  public ScriptButtonModel(String modelReference) {
    this.modelReference = modelReference;
  }

  /**
   * @see com.tonbeller.wcf.toolbar.ToolButtonModel#isPressed(RequestContext)
   */
  public boolean isPressed(RequestContext context) {
    Boolean value = (Boolean)context.getModelReference(modelReference);
    return value.booleanValue();
  }

  /**
   * @see com.tonbeller.wcf.toolbar.ToolButtonModel#setPressed(RequestContext, boolean)
   */
  public void setPressed(RequestContext context, boolean value) {
    context.setModelReference(modelReference, new Boolean(value));
  }

}
