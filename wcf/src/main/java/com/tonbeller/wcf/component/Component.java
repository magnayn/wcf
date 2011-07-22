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
package com.tonbeller.wcf.component;

import java.util.Map;

import com.tonbeller.wcf.controller.RequestListener;


/**
 * A Component is created by a ComponentTag and stored into the users Session.
 * A RenderTag calls its render() method that produces a DOM. The DOM is then
 * rendered via a xsl stylesheet.
 * @author av
 */
public interface Component extends RequestListener, Renderable, FormListener, Visible, LifeCycle {
  /**
   * returns the parent component as specified by initialize() or null, if
   * this is the root component
   * @return
   */
  Component getParent();

  /**
   * show another jsp
   */
  void setNextView(String uri);
  
  /**
   * @param httpParams map of http parameters that may identify a RequestListener of this component
   * @return true if this component is listening to a http request with <code>httpParams</code>
   */
  boolean isListeningTo(Map httpParams);
}
