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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
/**
 * WcfController is a {@link Controller} that is global for all
 * components. WcfController just remembers RequestListeners and
 * views, the actual work is done by {@link RequestFilter}. It works
 * in JavaServer Faces context as well as stand-alone.
 */
public class WcfController extends Controller {
  private static Logger logger = Logger.getLogger(WcfController.class);
  private List requestListeners = new LinkedList();

  WcfController() {
  }

  private static final String WEBKEY = WcfController.class.getName() + ".dispatcher";

  public static Controller instance(HttpSession session) {
    WcfController ctrl = (WcfController) session.getAttribute(WEBKEY);
    if (ctrl == null) {
      ctrl = new WcfController();
      session.setAttribute(WEBKEY, ctrl);
    }
    return ctrl;
  }

  public void addRequestListener(RequestListener l) {
    requestListeners.add(l);
  }

  public void removeRequestListener(RequestListener l) {
    requestListeners.remove(l);
  }

  public void setNextView(String uri) {
    RequestContext context = RequestContext.instance();
    context.getRequest().setAttribute(RequestFilter.NEXTVIEW, uri);
  }
  
  public String getNextView() {
    RequestContext context = RequestContext.instance();
    return (String)context.getRequest().getAttribute(RequestFilter.NEXTVIEW);
  }

  public void request(RequestContext context) throws Exception {
    // avoid ConcurrentModificationException - when a Component
    // registers child components while dispatching the request
    ArrayList list = new ArrayList(requestListeners);
    for (Iterator it = list.iterator(); it.hasNext();) {
      RequestListener l = (RequestListener) it.next();
      l.request(context);
    }
  }

  public List getRootListeners() {
    return Collections.unmodifiableList(requestListeners);
  }
}
