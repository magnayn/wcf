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
public class ScriptButtonTag extends ToolButtonTag {

  String model;

  /**
   * @see com.tonbeller.wcf.toolbar.ToolButtonTag#getToolButtonModel()
   */
  protected ToolButtonModel getToolButtonModel(RequestContext context) {
    return new ScriptButtonModel(model);
  }

  /**
   * Sets the model.
   * @param model The model to set
   */
  public void setModel(String model) {
    this.model = model;
  }

}
