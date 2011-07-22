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
package com.tonbeller.wcf.controller;





/**
 * A RequestListener listens to HttpServletRequests. Multiple
 * listeners may get invoked for a single request, so the
 * listener should not produce output.
 * <p/>
 * @see Dispatcher
 */
public interface RequestListener {

  /**
   * notified by a Dispatcher for specific HTTP Requests
   * @param context the current request
   */
  void request(RequestContext context) throws Exception;
}