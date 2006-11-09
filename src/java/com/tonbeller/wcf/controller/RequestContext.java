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

import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tonbeller.tbutils.res.Resources;
import com.tonbeller.wcf.convert.Converter;
import com.tonbeller.wcf.expr.ExprContext;
import com.tonbeller.wcf.format.Formatter;

/**
 * Created on 05.11.2002
 *
 * @author av
 */
public abstract class RequestContext implements ExprContext {
  public abstract HttpServletRequest getRequest();
  public abstract HttpServletResponse getResponse();
  public abstract ServletContext getServletContext();
  public abstract HttpSession getSession();

  public abstract Converter getConverter();
  public abstract Formatter getFormatter();

  private static ThreadLocalStack instanceStack = new ThreadLocalStack();

  private static final String RESPONSE_COMPLETE_KEY = RequestContext.class.getName() + ".responseComplete";

  public abstract Map getFileParameters();

  protected RequestContext() {
  }
  
  /**
   * returns the RequestContext associated with the current thread / request
   * @throws EmptyThreadLocalStackException if there is no instance set via #setInstance()
   * @see RequestContextFactoryFinder#createContext(HttpServletRequest, HttpServletResponse, boolean)
   */
  public static RequestContext instance() throws EmptyThreadLocalStackException {
    return (RequestContext) instanceStack.peek(true);
  }
  
  /**
   * returns the RequestContext associated with the current thread / request
   * @param 
   * @see RequestContextFactoryFinder#createContext(HttpServletRequest, HttpServletResponse, boolean)
   */
  public static RequestContext instance(boolean failIfEmpty) throws EmptyThreadLocalStackException {
    return (RequestContext) instanceStack.peek(failIfEmpty);
  }

  /**
   * pushes a thread local instance. The context
   * will be returned by {@link #instance()}. This method is public for test purpose only.
   */
  public static void setInstance(RequestContext context) {
    instanceStack.push(context);
  }

  /**
   * invalidate context after request processing is complete. This must be called exactly
   * as many times RequestContextFactoryFinder.createContext is called.
   * <p />
   * @see RequestContextFactoryFinder#createContext(HttpServletRequest, HttpServletResponse, boolean)
   */
  public void invalidate() {
    instanceStack.pop();
  }

  /**
   * returns the request parameters
   * @return same as servlet 2.3 HttpServletRequest.getParameterMap()
   */
  public abstract Map getParameters();
  public abstract String[] getParameters(String name);
  public abstract String getParameter(String name);

  /**
   * the locale preferred by the user
   */
  public abstract Locale getLocale();

  /**
   * change the Locale for this session including
   * the Locale for JSTL &lt;fmt:message &gt; tag
   */
  public abstract void setLocale(Locale locale);

  public abstract Resources getResources();
  public abstract Resources getResources(String bundleName);
  public abstract Resources getResources(Class clasz);

  /**
   * evaluates a JSP EL expression. Example #{user.profile}
   */
  public abstract Object getModelReference(String expr);
  public abstract void setModelReference(String expr, Object value);


  /**
   * evaluates a role expression.
   * <p />
   * If <code>roleExpr</code> is a JSP EL expression
   * (e.g. ${bean.stringProperty}), then the result
   * of the EL is used as the role name.
   * <p />
   * If <code>roleExpr</code> starts with "!" the result
   * is inverted, e.g. all users except members of the
   * role will be allowed to access the component.
   * <p />
   * Roles may be mapped in resources.properties (or in
   * System.properties or user.properties - see
   * {@link com.tonbeller.wcf.utils.Resources Resources}
   * for details). The format is <code>role.[role1]=[role2]</code>
   * (not including the brackets).
   * If such an entry is present, role2 will be checked instead
   * of role1.
   * <p />
   * If roleExpr == null, returns true (i.e. everybody is member
   * of the null role).
   */
  public abstract boolean isUserInRole(String roleExpr);

  /**
   * returns the remote User Name without its domain name.
   * In Oracle AS the HttpServletRequest.getRemoteUser() will
   * return a String like "domain\\user". This function returns
   * the name part only.
   *
   * @return name without domain
   */
  public abstract String getRemoteUser();
  public abstract String getRemoteDomain();

  /**
   * allows derived classes to specify a root user who may do everything.
   * The default implementation returns false always.
   * @return
   */
  public abstract boolean isAdmin();

  /**
   * if a RequestListener has called setError() or sentRedirect() on
   * the response it must inform the controller not to forward
   * to the JSP
   */
  public void setResponseComplete(boolean complete) {
    getRequest().setAttribute(RESPONSE_COMPLETE_KEY, Boolean.toString(complete));
  }
  public boolean isResponseComplete() {
    return "true".equals(getRequest().getAttribute(RESPONSE_COMPLETE_KEY));
  }
}
