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

import javax.servlet.http.HttpSession;

import com.tonbeller.wcf.controller.RequestContext;

/**
 * Lifecycle of WCF Components
 * @author av
 */
public interface LifeCycle {
  /**
   * initializes the component. Called once after construction.
   */
  void initialize(RequestContext context) throws Exception;

  /**
   * finalizes the component. Called once on session timeout
   */
  void destroy(HttpSession session) throws Exception;

}
