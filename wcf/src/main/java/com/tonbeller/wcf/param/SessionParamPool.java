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

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;

import com.tonbeller.tbutils.res.Resources;

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
 * <p/>
 * A SessionParamPool creates two session variables
 * <ul>
 *   <li><code>paramPool</code> the SessionParamPool instance that contains SessionParam's</li>
 *   <li><code>sqlValueMap</code> a map view to the SessionParamPool that contains the parameters name and their sql values</li>
 * </ul>
 * 
 * @author av
 */

public class SessionParamPool implements Map {
  private static final String SQL_VALUE_MAP = "sqlValueMap";
  private static final String PARAM_POOL = "paramPool";

  private static final Logger logger = Logger.getLogger(SessionParamPool.class);
  private SqlValueMap sqlValueMap;
  private Map map = new HashMap();

  protected SessionParamPool() {
    sqlValueMap = new SqlValueMap(this);
  }

  /**
   * Package local (test only)
   */
  static SessionParamPool instance() {
    return createInstance();
  }

  /**
   * finds or creates the SessionParamPool
   */
  public static SessionParamPool instance(HttpSession session) {
    // test environment?
    if (session == null)
      return createInstance();
    // server!
    SessionParamPool p = (SessionParamPool) session.getAttribute(PARAM_POOL);
    if (p == null) {
      p = createInstance();
      session.setAttribute(PARAM_POOL, p);
      session.setAttribute(SQL_VALUE_MAP, p.getSqlValueMap());
    }
    return p;
  }

  /**
   * creates a new SessionParamPool instance
   */
  protected static SessionParamPool createInstance() {
    String clazz = SessionParamPool.class.getName();
    clazz = Resources.instance().getOptionalString(clazz, clazz);
    try {
      return (SessionParamPool) Class.forName(clazz).newInstance();
    } catch (InstantiationException e) {
      logger.error(null, e);
      throw new IllegalArgumentException(clazz);
    } catch (IllegalAccessException e) {
      logger.error(null, e);
      throw new IllegalArgumentException(clazz);
    } catch (ClassNotFoundException e) {
      logger.error(null, e);
      throw new IllegalArgumentException(clazz);
    }
  }

  /**
   * finds or creates the SessionParamPool
   */
  public static SessionParamPool instance(PageContext pageContext) {
    SessionParamPool p = (SessionParamPool) pageContext.findAttribute(PARAM_POOL);
    if (p == null)
      return instance(pageContext.getSession());
    return p;
  }

  public SessionParam getParam(String name) {
    return (SessionParam) map.get(name);
  }

  public SessionParam setParam(SessionParam p) {
    return (SessionParam) map.put(p.getName(), p);
  }

  /**
   * stores all SessionParam objects of c into the pool. Returns a Map
   * that contains the previous value (or null) for the 
   * modified parameter names.
   * @see #popParams(Map) 
   */
  public Map pushParams(Collection c) {
    Map memento = new HashMap();
    for (Iterator it = c.iterator(); it.hasNext();) {
      SessionParam param = (SessionParam) it.next();
      SessionParam prev = setParam(param);
      String name = param.getName();
      if (!memento.containsKey(name))
        memento.put(name, prev);
    }
    return memento;
  }

  /**
   * restores the state of the pool that was modified
   * by pushParams
   * 
   * @see #pushParams(Collection)
   */
  public void popParams(Map memento) {
    for (Iterator it = memento.entrySet().iterator(); it.hasNext();) {
      Map.Entry e = (Entry) it.next();
      SessionParam p = (SessionParam) e.getValue();
      if (p == null)
        removeParam((String) e.getKey());
      else
        setParam(p);
    }
  }

  public void removeParam(SessionParam p) {
    map.remove(p.getName());
  }

  public SessionParam removeParam(String name) {
    return (SessionParam) map.remove(name);
  }

  /**
   * returns a map that maps parameter names to their sql values.
   */
  public Map getSqlValueMap() {
    return sqlValueMap;
  }

  public int size() {
    return map.size();
  }

  public void clear() {
    map.clear();
  }

  public boolean isEmpty() {
    return map.isEmpty();
  }

  public boolean containsKey(Object key) {
    return map.containsKey(key);
  }

  public boolean containsValue(Object value) {
    return map.containsValue(value);
  }

  public Collection values() {
    return map.values();
  }

  public void putAll(Map t) {
    map.putAll(t);
  }

  public Set entrySet() {
    return map.entrySet();
  }

  public Set keySet() {
    return map.keySet();
  }

  public Object get(Object key) {
    return map.get(key);
  }

  public Object remove(Object key) {
    return map.remove(key);
  }

  public Object put(Object key, Object value) {
    return map.put(key, value);
  }

}