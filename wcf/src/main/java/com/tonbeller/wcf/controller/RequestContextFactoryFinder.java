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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.tonbeller.wcf.utils.SoftException;

/**
 * finds the RequestContextFactory
 * @task the factory should be thread safe and part of the environment
 * @author av
 */
public class RequestContextFactoryFinder {
  private static Logger logger = Logger.getLogger(RequestContextFactoryFinder.class);

  /**
   * an instance of RequestContextFactory ist found in the users session.
   */
  private static final String SESSION_KEY = RequestContextFactory.class.getName();

  /**
   * The name of the concrete class is a configurable context attribute with this
   * name.
   */
  private static final String CONTEXT_KEY = "com.tonbeller.wcf.controller.RequestContextFactory";

  public static RequestContextFactory findFactory(HttpSession session) {
    RequestContextFactory rcf;
    try {
      rcf = (RequestContextFactory) session.getAttribute(SESSION_KEY);
      if (rcf == null) {
        ServletContext context = session.getServletContext();
        String className = (String) context.getInitParameter(CONTEXT_KEY);
        if (className == null)
          className = RequestContextFactoryImpl.class.getName();
        rcf = (RequestContextFactory) Class.forName(className).newInstance();
        session.setAttribute(SESSION_KEY, rcf);
      }
    } catch (InstantiationException e) {
      logger.error(null, e);
      throw new SoftException(e);
    } catch (IllegalAccessException e) {
      logger.error(null, e);
      throw new SoftException(e);
    } catch (ClassNotFoundException e) {
      logger.error(null, e);
      throw new SoftException(e);
    }
    return rcf;
  }

  /**
   * @param request
   * @param response
   * @param threadLocal if true, a thread local RequestContext variable is initialized. The thread
   * local may be accessed via RequestContext.instance() and must be cleaned up by the caller
   * via RequestContext.invalidate().
   * 
   * @see RequestContext#instance()
   * @see RequestContext#invalidate() 
   */
  public static RequestContext createContext(HttpServletRequest request, HttpServletResponse response, boolean threadLocal) {
    HttpSession session = request.getSession(true);
    RequestContextFactory rcf = findFactory(session);
    RequestContext context = rcf.createContext(request, response);
    if (threadLocal)
      RequestContext.setInstance(context);
    return context;
  }
}
