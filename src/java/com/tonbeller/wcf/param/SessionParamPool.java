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
package com.tonbeller.wcf.param;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

/**
 * A pool that contains all {@link SessionParam} instances of a session.
 * JSPs, SQL Queries and MDX Queries may fetch parameter values that
 * they need for display or computation.
 * 
 * <p/>
 * Parameters may be accessed from a JSP with JSTL scripting, for 
 * example
 * <pre>
 *   Selected Customer: &lt;c:out value="${paramPool.myParam.displayValue}"/&gt;
 * </pre>
 * 
 * <p/>
 * Parameters may be accessed in a JSTL SQL tag like
 * <pre>
 *   &lt;sql:query&gt;
 *     ...
 *     &lt;wcf:sqlParam name="..."/&gt;
 *     ...
 *   &lt;/sql:query&gt;
 * </pre>
 * 
 * <p/>
 * Parameters may be used in MDX Queries like
 * <pre>
 *   &lt;jp:mondrianQuery&gt;
 *     ...
 *     &lt;jp:mondrianParam name="..."/&gt;
 *   &lt;/jp:mondrianQuery&gt;
 * </pre>
 * 
 * @author av
 */

public class SessionParamPool extends HashMap {

  public static final String WEBKEY = "paramPool";
  public static final String SQLVALUEMAP = "sqlValueMap";

  private SessionParamPool() {
    // this.put(SQLVALUEMAP, new SqlValueMap(this));
  }

  /**
   * Package local (test only)
   */
  static SessionParamPool instance() {
    return new SessionParamPool();
  }

  /**
   * finds or creates the SessionParamPool
   */
  public static SessionParamPool instance(HttpSession session) {
    // test environment?
    if (session == null)
      return new SessionParamPool();
    // server!
    SessionParamPool p = (SessionParamPool) session.getAttribute(WEBKEY);
    if (p == null) {
      p = new SessionParamPool();
      session.setAttribute(WEBKEY, p);
    }
    return p;
  }

  /**
   * finds or creates the SessionParamPool
   */
  public static SessionParamPool instance(PageContext pageContext) {
    SessionParamPool p = (SessionParamPool) pageContext.findAttribute(WEBKEY);
    if (p == null)
      return instance(pageContext.getSession());
    return p;
  }

  public SessionParam getParam(String name) {
    return (SessionParam) this.get(name);
  }
  
  public void setParam(SessionParam p) {
    this.put(p.getName(), p);
  }

  public void removeParam(SessionParam p) {
    this.remove(p.getName());
  }
  
  public void removeParam(String name) {
    this.remove(name);
  }
  
  /**
   * returns a map that accesses the sqlValue of the parameters
   * directly. For example, to access the sqlValue of a parameter
   * named "foo" you will have to write in JSTL EL ${paramPool.foo.sqlValue}
   * if you use the SessionParamPool. If you use the sqlValueMap, you 
   * can write "${paramPool.sqlValueMap.foo}" instead. The map will create
   * all missing entries, i.e. reading a parameter with the expression
   * "${paramPool.sqlValueMap.foo}" will implicitely create a SesssionParam
   * with sqlValue == null.
   * <p />
   * The map is meant as a target for WCF forms that allow the
   * user to enter the sqlValue of SessionParam in a generic
   * way.
   * @see SqlValueMap
   */
  public Map getSqlValueMap() {
    return (Map) get(SQLVALUEMAP);
  }

}