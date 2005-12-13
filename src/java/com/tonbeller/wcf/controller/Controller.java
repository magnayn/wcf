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

import java.util.List;

import javax.servlet.http.HttpSession;

/**
 * provides access to the dispatcher and next view
 */
public abstract class Controller implements RequestListener {

  public static Controller instance(HttpSession session) {
    return WcfController.instance(session);
  }
  
  public abstract void addRequestListener(RequestListener l);
  public abstract void removeRequestListener(RequestListener l);
  public abstract void setNextView(String uri);
  public abstract String getNextView();

  /**
   * returns all registered RequestListeners
   */
  public abstract List getRootListeners();
}
