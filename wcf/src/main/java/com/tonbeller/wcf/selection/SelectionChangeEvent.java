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
package com.tonbeller.wcf.selection;

import java.util.EventObject;

import com.tonbeller.wcf.controller.RequestContext;
public class SelectionChangeEvent extends EventObject {
  RequestContext context;
  public SelectionChangeEvent(RequestContext context, SelectionModel source) {
    super(source);
    this.context = context;
  }
  
  public SelectionModel getSelectionModel() {
    return (SelectionModel)getSource();
  }

  public RequestContext getContext() {
    return context;
  }

}
