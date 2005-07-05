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
package com.tonbeller.wcf.form;

import javax.servlet.http.HttpSession;

import org.w3c.dom.Element;

import com.tonbeller.wcf.controller.RequestContext;

/**
 * Part of a Component that is attached to a DOM Element. A NodeHandler is attached to an element
 * node at initialization time.
 * @author av
 */
public interface NodeHandler {
  
  /**
   * initializes the handler
   * @param context the current request
   * @param parent the dispatcher of the owner component. NodeHandler may register
   * RequestListeners with this dispatcher
   * @param form allows the NodeHandler to send/receive form events (validate/revert)
   * @param element the element that this handler is responsible for
   */
  void initialize(RequestContext context, XmlComponent comp, Element element) throws Exception;
  void destroy(HttpSession session) throws Exception;
  
  /**
   * renders itself by changing the DOM Element
   */
  void render(RequestContext context) throws Exception;
}
